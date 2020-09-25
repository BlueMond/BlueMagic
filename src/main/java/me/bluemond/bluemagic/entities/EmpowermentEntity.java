package me.bluemond.bluemagic.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public class EmpowermentEntity extends Entity {

    public static final String KEY_AGE = "age";

    private ItemStack potionStack;
    private List<EffectInstance> effects;
    private int age;
    private static final int MAX_AGE = 1 *60*20; // first value is minutes
    private static final int RADIUS = 5;

    public EmpowermentEntity(EntityType<? extends EmpowermentEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        potionStack = null;
        effects = new ArrayList<>();
        age = 0;
    }

    public void tick() {
        super.tick();

        // verify age
        age++;
        if(age >= MAX_AGE){
            this.remove();
        }

        // add potion effect to those within radius
        if(!this.world.isRemote() && !effects.isEmpty()){
            BlockPos pos = this.getPosition();
            List<PlayerEntity> playerEntities = this.world.getEntitiesWithinAABB(
                    PlayerEntity.class, new AxisAlignedBB(pos.getX()-RADIUS, 0, pos.getZ()-RADIUS, pos.getX()+RADIUS, 256, pos.getZ()+RADIUS));

            for(PlayerEntity playerEntity : playerEntities){
                for(EffectInstance effectInstance : effects){
                    if(!effectInstance.getPotion().isInstant()){
                        playerEntity.addPotionEffect(new EffectInstance(effectInstance.getPotion(), 5*20+3, effectInstance.getAmplifier()));
                    }
                }

            }
        }
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        potionStack = ItemStack.read(compound);
        age = compound.getInt(KEY_AGE);
        effects = PotionUtils.getEffectsFromStack(potionStack);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        if(potionStack != null){
            potionStack.write(compound);
            compound.putInt(KEY_AGE, age);
        }
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
        return potionStack;
    }

    // also sets effects from potionStack
    public void setPotionStack(ItemStack potionStack) {
        this.potionStack = potionStack;
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

}
