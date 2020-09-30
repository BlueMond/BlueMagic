package me.bluemond.bluemagic.client.entity.model;

import me.bluemond.bluemagic.entities.EmpowermentEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.animation.model.AnimatedEntityModel;
import software.bernie.geckolib.animation.render.AnimatedModelRenderer;

public class EmpowermentEntityModel extends AnimatedEntityModel<EmpowermentEntity> {

    private final AnimatedModelRenderer Body;
    private final AnimatedModelRenderer Torso;
    private final AnimatedModelRenderer Legs;
    private final AnimatedModelRenderer Leg1;
    private final AnimatedModelRenderer Leg2;

    public EmpowermentEntityModel()
    {
        textureWidth = 32;
        textureHeight = 32;
        Body = new AnimatedModelRenderer(this);
        Body.setRotationPoint(0.0F, 24.0F, 0.0F);

        Body.setModelRendererName("Body");
        this.registerModelRenderer(Body);

        Torso = new AnimatedModelRenderer(this);
        Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(Torso);
        Torso.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        Torso.setModelRendererName("Torso");
        this.registerModelRenderer(Torso);

        Legs = new AnimatedModelRenderer(this);
        Legs.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(Legs);

        Legs.setModelRendererName("Legs");
        this.registerModelRenderer(Legs);

        Leg1 = new AnimatedModelRenderer(this);
        Leg1.setRotationPoint(0.0F, -7.0F, 0.0F);
        Legs.addChild(Leg1);
        Leg1.setTextureOffset(0, 18).addBox(-4.0F, 8.0F, -1.0F, 8.0F, 1.0F, 2.0F, 0.0F, false);
        Leg1.setTextureOffset(0, 18).addBox(2.0F, 7.0F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        Leg1.setTextureOffset(0, 18).addBox(-6.0F, 7.0F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        Leg1.setModelRendererName("Leg1");
        this.registerModelRenderer(Leg1);

        Leg2 = new AnimatedModelRenderer(this);
        Leg2.setRotationPoint(1.0F, -8.0F, 0.0F);
        Legs.addChild(Leg2);
        Leg2.setTextureOffset(14, 16).addBox(8.0F, -1.0F, -4.0F, 1.0F, 2.0F, 8.0F, 0.0F, true);
        Leg2.setTextureOffset(14, 16).addBox(7.0F, -1.0F, -6.0F, 1.0F, 2.0F, 4.0F, 0.0F, true);
        Leg2.setTextureOffset(14, 16).addBox(7.0F, -1.0F, 2.0F, 1.0F, 2.0F, 4.0F, 0.0F, true);
        Leg2.setModelRendererName("Leg2");
        this.registerModelRenderer(Leg2);

        this.rootBones.add(Body);
    }


    @Override
    public ResourceLocation getAnimationFileLocation()
    {
        return new ResourceLocation("bluemagic", "animations/empowerment_entity.json");
    }
}