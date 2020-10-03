package me.bluemond.bluemagic.ritualeffects;

import com.ma.api.recipes.IRitualRecipe;
import com.ma.api.rituals.RitualEffect;
import me.bluemond.bluemagic.entities.EmpowermentEntity;
import me.bluemond.bluemagic.init.EntityInit;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;


public class RitualEffectEmpowerment extends RitualEffect {

    public RitualEffectEmpowerment(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    protected boolean applyRitualEffect(PlayerEntity playerEntity, ServerWorld serverWorld, BlockPos ritualCenter, IRitualRecipe iRitualRecipe, NonNullList<ItemStack> reagents) {

        EmpowermentEntity empowermentEntity = EntityInit.EMPOWERMENT_ENTITY.get()
                .create(serverWorld, null, null, null, ritualCenter.up(),
                        SpawnReason.TRIGGERED, false, false);
        empowermentEntity.setPotionStack(reagents.get(1));
        serverWorld.addEntity(empowermentEntity);

        return false;
    }

    @Override
    protected int getApplicationTicks(ServerWorld serverWorld, BlockPos blockPos, IRitualRecipe iRitualRecipe, NonNullList<ItemStack> nonNullList) {
        return 0;
    }

}
