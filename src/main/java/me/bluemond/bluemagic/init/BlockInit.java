/*
package me.bluemond.bluemagic.init;

import me.bluemond.bluemagic.BlueMagic;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(BlueMagic.MOD_ID)
@Mod.EventBusSubscriber(modid = BlueMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static final Block empowerment_block = null;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event){
        //empowerment_block registry
        event.getRegistry().register(new Block(Block.Properties.create(Material.IRON)
                .notSolid()
                .noDrops()
                .doesNotBlockMovement())
                .setRegistryName("empowerment_block"));
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new BlockItem(empowerment_block, new Item.Properties().).setRegistryName("empowerment_block"));
    }
}

 */
