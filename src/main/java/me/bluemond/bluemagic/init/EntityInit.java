package me.bluemond.bluemagic.init;

import me.bluemond.bluemagic.BlueMagic;
import me.bluemond.bluemagic.entities.EmpowermentEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, BlueMagic.MOD_ID);

    public static final RegistryObject<EntityType<EmpowermentEntity>> EMPOWERMENT_ENTITY =
            ENTITY_TYPES
                    .register("empowerment_entity",
                            () -> EntityType.Builder.create(EmpowermentEntity::new, EntityClassification.MISC)
                                    .setShouldReceiveVelocityUpdates(false)
                                    //.disableSummoning()
                                    .build(new ResourceLocation(BlueMagic.MOD_ID, "empowerment_entity").toString()));

}
