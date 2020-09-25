package me.bluemond.bluemagic;

import me.bluemond.bluemagic.init.EntityInit;
import me.bluemond.bluemagic.ritualeffects.RitualEffectDisenchanting;
import me.bluemond.bluemagic.ritualeffects.RitualEffectEmpowerment;
import me.bluemond.bluemagic.ritualeffects.RitualEffectFirework;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ma.api.guidebook.RegisterGuidebooksEvent;
import com.ma.api.rituals.RitualEffect;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("bluemagic")
public class BlueMagic
{
    public static final String MOD_ID = "bluemagic";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    private static RitualEffectFirework ritualEffectFirework;

    public BlueMagic() {
        // Register ourselves for server and other game events we are interested in
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        IEventBus fmlEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityInit.ENTITY_TYPES.register(fmlEventBus);

        eventBus.register(this);
    }

    Block block;

    @SubscribeEvent
    public void onRegisterGuidebooks(RegisterGuidebooksEvent event) {
    	event.getRegistry().AddGuidebook(new ResourceLocation("bluemagic", "guide/guidebook_en_us.json"));
    }

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent event){
        if(event.side.equals(LogicalSide.SERVER)){
            if(ritualEffectFirework != null){
                ritualEffectFirework.performWorldTick(event.world);
            }
        }
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        
        @SubscribeEvent
        public static void onRitualsRegistry(final RegistryEvent.Register<RitualEffect> event) {
            ritualEffectFirework = new RitualEffectFirework(new ResourceLocation("bluemagic", "rituals/firework"));
            event.getRegistry().register(ritualEffectFirework.setRegistryName(new ResourceLocation("bluemagic", "firework-ritual-effect")));

            RitualEffectDisenchanting ritualEffectDisenchanting = new RitualEffectDisenchanting(new ResourceLocation("bluemagic", "rituals/disenchanting"));
            event.getRegistry().register(ritualEffectDisenchanting.setRegistryName(new ResourceLocation("bluemagic", "disenchanting-ritual-effect")));

            RitualEffectEmpowerment ritualEffectEmpowerment = new RitualEffectEmpowerment(new ResourceLocation("bluemagic", "rituals/empowerment"));
            event.getRegistry().register(ritualEffectEmpowerment.setRegistryName(new ResourceLocation("bluemagic", "empowerment-ritual-effect")));
        }

        /*
        @SubscribeEvent
        public static void registerTE(RegistryEvent.Register<TileEntityType<?>> evt) {
            // gotta change factory and validBlocks
            TileEntityType<?> type = TileEntityType.Builder.create(factory, validBlocks).build(null);
            type.setRegistryName("mymod", "myte");
            evt.getRegistry().register(type);
        }

         */
    }    
}
