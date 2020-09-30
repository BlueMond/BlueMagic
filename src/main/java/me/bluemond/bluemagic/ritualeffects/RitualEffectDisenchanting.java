package me.bluemond.bluemagic.ritualeffects;

import com.ma.api.recipes.IRitualRecipe;
import com.ma.api.rituals.RitualEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;


public class RitualEffectDisenchanting extends RitualEffect {

    public RitualEffectDisenchanting(ResourceLocation ritualName) {
        super(ritualName);
    }

    @Override
    protected boolean applyRitualEffect(PlayerEntity playerEntity, ServerWorld serverWorld, BlockPos ritualCenter, IRitualRecipe iRitualRecipe, NonNullList<ItemStack> reagents) {
        ItemStack enItemStack = reagents.get(7);
        ItemStack bookItemStack = new ItemStack(Items.ENCHANTED_BOOK);

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enItemStack);
        for(Map.Entry<Enchantment, Integer> enEntry : enchantments.entrySet()){
            bookItemStack.addEnchantment(enEntry.getKey(), enEntry.getValue());
        }

        ItemEntity bookEntity =
                new ItemEntity(serverWorld, ritualCenter.getX(), ritualCenter.getY(), ritualCenter.getZ(), bookItemStack);

        serverWorld.addEntity(bookEntity);

        return false;
    }

    @Override
    protected int getApplicationTicks(ServerWorld serverWorld, BlockPos blockPos, IRitualRecipe iRitualRecipe, NonNullList<ItemStack> nonNullList) {
        return 0;
    }

}
