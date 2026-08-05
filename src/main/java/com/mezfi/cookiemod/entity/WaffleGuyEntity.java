package com.mezfi.cookiemod.entity;

import com.mezfi.cookiemod.registry.ModItems;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

/**
 * The waffle guy — the common hostile enemy and main soldier-killer (MECHANICS_SPEC §9.1).
 *
 * <p>Attacks the player and cookie soldiers on sight. Spawns from waffle-guy spawners in
 * towers/structures (spawner placement comes in a later phase; a spawn egg is provided now).
 */
public class WaffleGuyEntity extends Monster {

    public WaffleGuyEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 5; // XP for the tower waffle-guy/XP farm (MECHANICS_SPEC §10)
    }

    /** Per-piece chance each gummy armour slot is worn — yields 0..4 pieces (MECHANICS_SPEC §9.1). */
    private static final float GUMMY_PIECE_CHANCE = 0.5F;
    /** Chance a spawning waffle guy wields a lollipop (MECHANICS_SPEC §9.1). */
    private static final float LOLLIPOP_CHANCE = 0.60F;
    /** When killed by a player, each worn armour piece has this chance to drop. */
    private static final float ARMOUR_DROP_CHANCE = 0.20F;
    /** When killed by a player, an equipped lollipop has this chance to drop. */
    private static final float LOLLIPOP_DROP_CHANCE = 0.30F;

    /**
     * Waffle guys spawn with random gear (MECHANICS_SPEC §9.1): a lollipop, and any number of
     * gummy armour pieces (0..4, each rolled independently). Whatever they wear has a random
     * chance to drop when a player kills them.
     */
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        if (this.random.nextFloat() < LOLLIPOP_CHANCE) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.LOLLIPOP.get()));
            this.setDropChance(EquipmentSlot.MAINHAND, LOLLIPOP_DROP_CHANCE);
        }
        maybeWear(EquipmentSlot.HEAD, ModItems.GUMMY_HELMET.get());
        maybeWear(EquipmentSlot.CHEST, ModItems.GUMMY_CHESTPLATE.get());
        maybeWear(EquipmentSlot.LEGS, ModItems.GUMMY_LEGGINGS.get());
        maybeWear(EquipmentSlot.FEET, ModItems.GUMMY_BOOTS.get());
        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    /** Independently roll a single armour slot; if worn, give it a chance to drop on death. */
    private void maybeWear(EquipmentSlot slot, net.minecraft.world.item.Item piece) {
        if (this.random.nextFloat() < GUMMY_PIECE_CHANCE) {
            this.setItemSlot(slot, new ItemStack(piece));
            this.setDropChance(slot, ARMOUR_DROP_CHANCE);
        }
    }

    /** [EST] MECHANICS_SPEC §9.1: 24 HP, 6 attack. */
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, CookieSoldierEntity.class, true));
    }
}
