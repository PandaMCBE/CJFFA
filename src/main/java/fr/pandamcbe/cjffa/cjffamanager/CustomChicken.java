package fr.pandamcbe.cjffa.cjffamanager;

import net.minecraft.server.v1_8_R3.*;


public class CustomChicken extends EntityChicken {

    public CustomChicken(World world) {
        super(world);

        // Clear pathfinders
        this.goalSelector = new PathfinderGoalSelector(world.methodProfiler);
        this.targetSelector = new PathfinderGoalSelector(world.methodProfiler);

        // Pathfinders
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.4D, true)); // Attaque
        this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityHuman.class, true));

        // Stats custom
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(40.0D); // 20 cœurs
        this.setHealth(40.0F);

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.45D); // Plus rapide
    }

    @Override
    public boolean a(EntityHuman entityhuman) {
        entityhuman.damageEntity(DamageSource.mobAttack(this), 6.0F);
        return super.a(entityhuman);
    }

}