package me.bluemond.bluemagic.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib.animation.builder.AnimationBuilder;
import software.bernie.geckolib.animation.controller.AnimationController;
import software.bernie.geckolib.animation.controller.EntityAnimationController;
import software.bernie.geckolib.entity.IAnimatedEntity;
import software.bernie.geckolib.event.AnimationTestEvent;
import software.bernie.geckolib.manager.EntityAnimationManager;

import java.util.ArrayList;
import java.util.List;

public class EmpowermentEntity extends Entity implements IAnimatedEntity {

    private EntityAnimationManager animationManager = new EntityAnimationManager();
    private AnimationController animationController = new EntityAnimationController(this, "idleController", 20F, this::animationPredicate);

    private static final DataParameter<ItemStack> KEY_POTION = EntityDataManager.createKey(EmpowermentEntity.class, DataSerializers.ITEMSTACK);
    private static final DataParameter<Float> KEY_MAX_AGE = EntityDataManager.createKey(EmpowermentEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> KEY_RADIUS = EntityDataManager.createKey(EmpowermentEntity.class, DataSerializers.FLOAT);

    private List<EffectInstance> effects;
    private int age;

    public EmpowermentEntity(EntityType<? extends EmpowermentEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        animationManager.addAnimationController(animationController);

        effects = new ArrayList<>();
        age = 0;
    }

    public void tick() {
        super.tick();

        /*
        // verify age
        age++;
        float maxAge = this.dataManager.get(KEY_MAX_AGE);
        if(age >= this.dataManager.get(KEY_MAX_AGE)){
            this.remove();
        }

         */


        // add potion effect to those within radius
        if(!this.world.isRemote() && !effects.isEmpty()){
            BlockPos pos = this.getPosition();
            float radius = this.dataManager.get(KEY_RADIUS);
            List<PlayerEntity> playerEntities = this.world.getEntitiesWithinAABB(
                    PlayerEntity.class, new AxisAlignedBB(pos.getX()- radius, 0, pos.getZ()- radius, pos.getX()+ radius, 256, pos.getZ()+ radius));

            // loop through players and applicable effects
            for(PlayerEntity playerEntity : playerEntities){
                for(EffectInstance effectInstance : effects){
                    if(!effectInstance.getPotion().isInstant()){

                        playerEntity.addPotionEffect(new EffectInstance
                                    (effectInstance.getPotion(), 5*20, effectInstance.getAmplifier()));

                    }
                }
            }

        }
    }

    @Override
    protected void registerData() {
        this.dataManager.register(KEY_POTION, ItemStack.EMPTY);
        this.dataManager.register(KEY_MAX_AGE, (float)(8 *60*20));
        this.dataManager.register(KEY_RADIUS, 16F);
    }


    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(KEY_POTION, ItemStack.read(compound));
        this.dataManager.set(KEY_MAX_AGE, compound.getFloat("max_age"));
        this.dataManager.set(KEY_RADIUS, compound.getFloat("radius"));
        age = compound.getInt("age");
        effects = PotionUtils.getEffectsFromStack(this.dataManager.get(KEY_POTION));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        this.dataManager.get(KEY_POTION).write(compound);
        compound.putFloat("max_age", this.dataManager.get(KEY_MAX_AGE));
        compound.putFloat("radius", this.dataManager.get(KEY_RADIUS));
        compound.putInt("age", age);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }

    public ItemStack getPotionStack() {
        return this.dataManager.get(KEY_POTION);
    }

    // also sets effects from potionStack
    public void setPotionStack(ItemStack potionStack) {
        this.dataManager.set(KEY_POTION, potionStack);
        effects = PotionUtils.getEffectsFromStack(potionStack);
    }

    public List<EffectInstance> getEffects() {
        return effects;
    }


    @Override
    public void remove() {
        super.remove();
    }

    @Override
    public boolean canBeCollidedWith(){
        return false;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn){
        return false;
    }

    @Override
    public boolean canBeAttackedWithItem(){
        return false;
    }



    @Override
    public EntityAnimationManager getAnimationManager() {
        return animationManager;
    }


    private <E extends EmpowermentEntity> boolean animationPredicate(AnimationTestEvent<E> event){

        animationController.setAnimation(new AnimationBuilder().addAnimation("bluemagic.empowermententity.idle", true));

        return true;
    }

    public void setExtendedRangeAndTime(boolean extendedRange, boolean extendedTime) {
        if(extendedRange){
            this.dataManager.set(KEY_RADIUS, 28F);
        }
        if(extendedTime){
            this.dataManager.set(KEY_MAX_AGE, (float)(13 *60*20));
        }
    }
}
