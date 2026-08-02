package com.mezfi.cookiemod.entity;

import com.mezfi.cookiemod.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * The cookie soldier — the core recruit mechanic (MECHANICS_SPEC §3).
 *
 * <p>Spawns wild and passive. Feeding it a {@code cookie} recruits it (tames it to the
 * player). Once recruited it follows and protects its owner (targeting hostiles), and
 * right-clicking with an empty hand toggles its Go/Stay order (sit).
 */
public class CookieSoldierEntity extends TamableAnimal {

    public CookieSoldierEntity(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    /** Cookie soldiers spawn wielding a sword (kept, not dropped). */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    /** [EST] stats from MECHANICS_SPEC §3.4: 20 HP, 3 attack, moderate speed. */
    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, 1.1D, 8.0F, 2.0F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        // Protect the owner: hunt hostiles, but only once recruited.
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(
                this, Monster.class, 10, true, false, (living) -> this.isTame()));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Level level = this.level();

        if (!this.isTame()) {
            // Recruit by feeding a cookie (MECHANICS_SPEC §3.1: 1 cookie).
            if (stack.is(ModItems.COOKIE.get())) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                if (!level.isClientSide) {
                    this.tame(player);
                    this.setOrderedToSit(false);
                    this.navigation.stop();
                    this.setTarget(null);
                    level.broadcastEntityEvent(this, (byte) 7); // taming hearts
                    this.playSound(SoundEvents.PLAYER_LEVELUP, 0.6F, 1.4F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            return super.mobInteract(player, hand);
        }

        // Owner toggles Go / Stay with an empty hand.
        if (this.isOwnedBy(player) && stack.isEmpty()) {
            if (!level.isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    /** Cookie soldiers are recruited, not bred. */
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }
}
