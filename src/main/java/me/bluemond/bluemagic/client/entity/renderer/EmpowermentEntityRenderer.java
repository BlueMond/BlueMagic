package me.bluemond.bluemagic.client.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.bluemond.bluemagic.BlueMagic;
import me.bluemond.bluemagic.client.entity.model.EmpowermentEntityModel;
import me.bluemond.bluemagic.entities.EmpowermentEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib.render.IModelRenderer;

public class EmpowermentEntityRenderer extends EntityRenderer<EmpowermentEntity> implements IModelRenderer
{

    protected static final ResourceLocation TEXTURE = new ResourceLocation(BlueMagic.MOD_ID, "textures/entity/empowerment_entity.png");
    protected final EmpowermentEntityModel empowermentModel = new EmpowermentEntityModel();


    public EmpowermentEntityRenderer(EntityRendererManager renderManager) {

        super(renderManager);
        this.shadowSize = 0.4f;

    }

    @Override
    public void render(EmpowermentEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        this.empowermentModel.setRotationAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        this.empowermentModel.setLivingAnimations(entityIn, 0, 0, partialTicks);
        RenderType renderType = RenderType.getEntityTranslucent(this.getEntityTexture(entityIn));

        if(!entityIn.getPotionStack().isEmpty()){
            matrixStackIn.push();
            matrixStackIn.translate(.015, 1.01, 0);
            matrixStackIn.scale(.5F, .5F, .5F);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

            IBakedModel iBakedModel = itemRenderer.getItemModelWithOverrides(entityIn.getPotionStack(), entityIn.getEntityWorld(), null);
            new ItemRendererWrapper(itemRenderer).renderItem(entityIn.getPotionStack(), ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, iBakedModel);
            matrixStackIn.pop();
        }


        IVertexBuilder iVertexBuilder = bufferIn.getBuffer(renderType);
        this.empowermentModel.render(matrixStackIn, iVertexBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EmpowermentEntity entity){
        return TEXTURE;
    }

    @Override
    public EntityModel getEntityModel()
    {
        return this.empowermentModel;
    }
}
