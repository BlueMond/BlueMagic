package me.bluemond.bluemagic.client.util;

import me.bluemond.bluemagic.BlueMagic;
import me.bluemond.bluemagic.client.entity.renderer.EmpowermentEntityRenderer;
import me.bluemond.bluemagic.init.EntityInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BlueMagic.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event){
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.EMPOWERMENT_ENTITY.get(), EmpowermentEntityRenderer::new);

    }
}
