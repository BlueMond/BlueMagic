package me.bluemond.bluemagic.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.bluemond.bluemagic.entities.EmpowermentEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class EmpowermentEntityModel<T extends EmpowermentEntity> extends EntityModel<T> {
    private final ModelRenderer Body;
    private final ModelRenderer Torso;
    private final ModelRenderer Legs;
    private final ModelRenderer Leg1;
    private final ModelRenderer Leg2;
    private final ModelRenderer Leg3;
    private final ModelRenderer Leg4;

    public EmpowermentEntityModel() {
        textureWidth = 32;
        textureHeight = 32;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 24.0F, 0.0F);


        Torso = new ModelRenderer(this);
        Torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(Torso);
        Torso.setTextureOffset(0, 0).addBox(-3.0F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

        Legs = new ModelRenderer(this);
        Legs.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(Legs);


        Leg1 = new ModelRenderer(this);
        Leg1.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(Leg1);


        Leg2 = new ModelRenderer(this);
        Leg2.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(Leg2);


        Leg3 = new ModelRenderer(this);
        Leg3.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(Leg3);


        Leg4 = new ModelRenderer(this);
        Leg4.setRotationPoint(0.0F, 0.0F, 0.0F);
        Legs.addChild(Leg4);

    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
