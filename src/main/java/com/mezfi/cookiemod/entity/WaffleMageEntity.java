package com.mezfi.cookiemod.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * The Waffle Mage — the second boss (MECHANICS_SPEC §8.3).
 *
 * <p>A floating, wither-like creature "made of cakes": it hovers above the arena, circles
 * the player, and spits novas of candy shards. Low melee threat but hard to reach. Shows a
 * boss bar, never despawns. Base HP 4,000 (§8 table). Beam/tongue attacks and crit-weakness
 * are a later phase.
 */
public class WaffleMageEntity extends Monster {

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable("entity.cookiemod.waffle_mage"),
            BossEvent.BossBarColor.YELLOW,
            BossEvent.BossBarOverlay.PROGRESS);

    private static final double HOVER_HEIGHT = 4.5;   // blocks above the target
    private static final double RING = 7.0;           // preferred horizontal distance
    private static final int NOVA_SHARDS = 12;        // shards per ring
    private static final float SHARD_SPEED = 0.9F;

    // Attack rotation: it picks nova / beam / tongue by range on a shared cooldown.
    private static final int ATTACK_INTERVAL = 55;    // ticks between attack choices
    private static final int BEAM_DURATION = 30;      // ticks the beam channels
    private static final int TONGUE_DURATION = 12;    // ticks the tongue lashes
    private static final float CRIT_BONUS = 1.5F;     // §8.3: crit/jump hits land best

    private int attackCooldown = 40;
    private int beamTicks = 0;
    private int tongueTicks = 0;
    private boolean tongueHit = false;

    public WaffleMageEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
        this.setNoGravity(true);
        this.xpReward = 100;
        this.moveControl = new net.minecraft.world.entity.ai.control.FlyingMoveControl(this, 20, true);
    }

    /** [EST] MECHANICS_SPEC §8 table: 4000 HP, low melee, floaty and knockback-immune. */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 4000.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, CookieSoldierEntity.class, true));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) {
            // idle: drift to a gentle stop but keep floating a few blocks off the ground
            int ground = this.level().getHeight(
                    net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    this.getBlockX(), this.getBlockZ());
            double desiredY = ground + 3.5;
            double vy = Math.max(-0.06, Math.min(0.06, (desiredY - this.getY()) * 0.05));
            Vec3 dm = this.getDeltaMovement().scale(0.8);
            this.setDeltaMovement(dm.x, vy, dm.z);
            return;
        }

        hoverAround(target);
        faceTarget(target);

        // Channelled attacks run to completion; otherwise pick a new attack by range.
        if (this.beamTicks > 0) {
            tickBeam(target);
            this.beamTicks--;
        } else if (this.tongueTicks > 0) {
            tickTongue(target);
            this.tongueTicks--;
        } else if (--this.attackCooldown <= 0) {
            chooseAttack(target);
            this.attackCooldown = ATTACK_INTERVAL;
        }
    }

    /** Pick nova (far), beam (mid), or tongue (close) for the next attack. */
    private void chooseAttack(LivingEntity target) {
        double dist = Math.sqrt(this.distanceToSqr(target));
        if (dist < 8.0) {
            this.tongueTicks = TONGUE_DURATION;
            this.tongueHit = false;
            this.playSound(SoundEvents.FROG_TONGUE, 1.2F, 0.8F);
        } else if (dist < 22.0 && this.random.nextBoolean()) {
            this.beamTicks = BEAM_DURATION;
            this.playSound(SoundEvents.EVOKER_CAST_SPELL, 1.1F, 1.4F);
        } else if (dist < 40.0) {
            fireNova(target);
        }
    }

    /**
     * A sustained beam: a line of particles from the Mage's eye to the target, dealing steady
     * magic damage while it channels. Low per-tick so it rewards breaking line of sight.
     */
    private void tickBeam(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        Vec3 from = new Vec3(this.getX(), this.getEyeY() - 0.2, this.getZ());
        Vec3 to = new Vec3(target.getX(), target.getY(0.6), target.getZ());
        Vec3 dir = to.subtract(from);
        double len = dir.length();
        Vec3 step = dir.normalize().scale(0.5);
        Vec3 p = from;
        for (double d = 0; d < len; d += 0.5) {
            level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
            p = p.add(step);
        }
        if (this.beamTicks % 4 == 0) {
            target.hurt(this.damageSources().indirectMagic(this, this), 2.5F);
        }
    }

    /**
     * A short "tongue" lash: a slime-particle strand that extends toward the target over the
     * lash, striking once at mid-reach for melee damage and yanking the target inward.
     */
    private void tickTongue(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel level)) {
            return;
        }
        Vec3 from = new Vec3(this.getX(), this.getEyeY() - 0.4, this.getZ());
        Vec3 to = new Vec3(target.getX(), target.getY(0.5), target.getZ());
        Vec3 dir = to.subtract(from);
        double len = dir.length();
        double reach = len * (1.0 - this.tongueTicks / (double) TONGUE_DURATION); // 0 → full
        Vec3 nd = dir.normalize();
        for (double d = 0; d < reach; d += 0.4) {
            Vec3 pt = from.add(nd.scale(d));
            level.sendParticles(ParticleTypes.ITEM_SLIME, pt.x, pt.y, pt.z, 1, 0.02, 0.02, 0.02, 0.0);
        }
        if (!this.tongueHit && this.tongueTicks <= TONGUE_DURATION / 2 && this.distanceToSqr(target) < 100.0) {
            this.tongueHit = true;
            target.hurt(this.damageSources().mobAttack(this), 5.0F);
            Vec3 pull = new Vec3(this.getX() - target.getX(), 0.0, this.getZ() - target.getZ())
                    .normalize().scale(0.6);
            target.push(pull.x, 0.15, pull.z);
            this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, 0.9F);
        }
    }

    /** §8.3: hard to melee, but crit / jump hits land best — amplify critical melee hits. */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof Player p && isCritHit(p)) {
            amount *= CRIT_BONUS;
        }
        return super.hurt(source, amount);
    }

    /** Vanilla critical-hit conditions, checked on the Mage's side to give crits their bonus. */
    private static boolean isCritHit(Player p) {
        return p.fallDistance > 0.0F && !p.onGround() && !p.onClimbable()
                && !p.isInWater() && !p.isPassenger() && !p.isSprinting()
                && !p.hasEffect(MobEffects.BLINDNESS);
    }

    /**
     * Turn the whole body to face the target, turning around if needed. Circling means the
     * Mage often travels sideways, so its facing is driven by the target, not by movement.
     */
    private void faceTarget(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        float wanted = (float) (Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
        float step = Mth.clamp(Mth.wrapDegrees(wanted - this.getYRot()), -12.0F, 12.0F); // ≤12°/tick
        this.setYRot(this.getYRot() + step);
        this.yBodyRot = this.getYRot();
        this.yHeadRot = this.getYRot();
        this.getLookControl().setLookAt(target, 30.0F, 30.0F);
    }

    /** Circle-strafe the target at RING distance and HOVER_HEIGHT above it. */
    private void hoverAround(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dz = target.getZ() - this.getZ();
        double horiz = Math.sqrt(dx * dx + dz * dz);
        Vec3 toward = horiz > 0.1 ? new Vec3(dx / horiz, 0, dz / horiz) : new Vec3(0, 0, 1);

        Vec3 push;
        if (horiz > RING + 1.0) {
            push = toward.scale(0.07);                       // close in
        } else if (horiz < RING - 1.0) {
            push = toward.scale(-0.06);                      // back off
        } else {
            push = new Vec3(-toward.z, 0, toward.x).scale(0.06); // strafe around
        }

        double desiredY = target.getY() + HOVER_HEIGHT;
        double vy = Math.max(-0.08, Math.min(0.08, (desiredY - this.getY()) * 0.05));

        Vec3 dm = this.getDeltaMovement().scale(0.82).add(push.x, 0, push.z);
        this.setDeltaMovement(dm.x, this.getDeltaMovement().y * 0.7 + vy, dm.z);
    }

    /** Spit a horizontal ring of candy shards plus one aimed straight at the target. */
    private void fireNova(LivingEntity target) {
        if (!(this.level() instanceof ServerLevel)) {
            return;
        }
        double sy = this.getEyeY();
        for (int i = 0; i < NOVA_SHARDS; i++) {
            double a = (Math.PI * 2.0 / NOVA_SHARDS) * i;
            spawnShard(Math.cos(a), 0.08, Math.sin(a), sy);
        }
        // aimed shot
        Vec3 aim = new Vec3(target.getX() - this.getX(),
                target.getEyeY() - sy, target.getZ() - this.getZ());
        spawnShard(aim.x, aim.y, aim.z, sy);
        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.2F, 0.7F);
    }

    private void spawnShard(double dx, double dy, double dz, double sy) {
        WaffleShardEntity shard = new WaffleShardEntity(this.level(), this);
        shard.setPos(this.getX(), sy, this.getZ());
        shard.shoot(dx, dy, dz, SHARD_SPEED, 1.0F);
        this.level().addFreshEntity(shard);
    }

    @Override
    public boolean causeFallDamage(float distance, float multiplier, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean isPersistenceRequired() {
        return true;
    }
}
