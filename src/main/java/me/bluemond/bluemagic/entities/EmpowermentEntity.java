package me.bluemond.bluemagic.entities;

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

    private static final String KEY_AGE = "age";
    private static final DataParameter<ItemStack> KEY_POTION = EntityDataManager.createKey(EmpowermentEntity.class, DataSerializers.ITEMSTACK);

    private List<EffectInstance> effects;
    private int age;
    private static final int MAX_AGE = 1 *60*20; // first value is minutes
    private static final int RADIUS = 5;

    public EmpowermentEntity(EntityType<? extends EmpowermentEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        animationManager.addAnimationController(animationController);
        effects = new ArrayList<>();
        age = 0;
    }

    public void tick() {
        super.tick();

        // verify age
        age++;
        /*
        if(age >= MAX_AGE){
            this.remove();
        }
         */

        // add potion effect to those within radius
        if(!this.world.isRemote() && !effects.isEmpty()){
            BlockPos pos = this.getPosition();
            List<PlayerEntity> playerEntities = this.world.getEntitiesWithinAABB(
                    PlayerEntity.class, new AxisAlignedBB(pos.getX()-RADIUS, 0, pos.getZ()-RADIUS, pos.getX()+RADIUS, 256, pos.getZ()+RADIUS));

            // loop through players and applicable effects
            for(PlayerEntity playerEntity : playerEntities){
                for(EffectInstance effectInstance : effects){
                    if(!effectInstance.getPotion().isInstant()){

                        EffectInstance currentEffect = playerEntity.getActivePotionEffect(effectInstance.getPotion());
                        boolean shouldApply = false;

                        // apply potion effect if current application is expiring or not already applied
                        if(currentEffect != null){
                            if(currentEffect.getDuration() <= 1*20){
                                shouldApply = true;
                            }
                        }else{
                            shouldApply = true;
                        }

                        if(shouldApply){
                            playerEntity.addPotionEffect(new EffectInstance
                                    (effectInstance.getPotion(), 5*20, effectInstance.getAmplifier()));
                        }

                    }
                }

            }
        }
    }

    @Override
    protected void registerData() {
        this.dataManager.register(KEY_POTION, null);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        this.dataManager.set(KEY_POTION, ItemStack.read(compound));
        age = compound.getInt(KEY_AGE);
        effects = PotionUtils.getEffectsFromStack(this.dataManager.get(KEY_POTION));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        this.dataManager.get(KEY_POTION).write(compound);
        compound.putInt(KEY_AGE, age);
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
}
