package me.bluemond.bluemagic.client.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemRendererWrapper{
    private final ItemRenderer itemRenderer;

    public ItemRendererWrapper(ItemRenderer itemRendererIn){
        itemRenderer = itemRendererIn;
    }

    public void renderItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformTypeIn, boolean leftHand, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn, IBakedModel modelIn) {
        if (!itemStackIn.isEmpty()) {
            matrixStackIn.push();
            boolean flag = transformTypeIn == ItemCameraTransforms.TransformType.GUI || transformTypeIn == ItemCameraTransforms.TransformType.GROUND || transformTypeIn == ItemCameraTransforms.TransformType.FIXED;
            if (itemStackIn.getItem() == Items.TRIDENT && flag) {
                modelIn = this.itemRenderer.getItemModelMesher().getModelManager().getModel(new ModelResourceLocation("minecraft:trident#inventory"));
            }

            modelIn = net.minecraftforge.client.ForgeHooksClient.handleCameraTransforms(matrixStackIn, modelIn, transformTypeIn, leftHand);
            matrixStackIn.translate(-0.5D, -0.5D, -0.5D);
            if (!modelIn.isBuiltInRenderer() && (itemStackIn.getItem() != Items.TRIDENT || flag)) {
                boolean flag1;
                if (transformTypeIn != ItemCameraTransforms.TransformType.GUI && !transformTypeIn.isFirstPerson() && itemStackIn.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem)itemStackIn.getItem()).getBlock();
                    flag1 = !(block instanceof BreakableBlock) && !(block instanceof StainedGlassPaneBlock);
                } else {
                    flag1 = true;
                }
                if (modelIn.isLayered()) { net.minecraftforge.client.ForgeHooksClient.drawItemLayered(this.itemRenderer, modelIn, itemStackIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, flag1); }
                else {
                    RenderType rendertype = RenderType.getEntityTranslucent(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
                    IVertexBuilder ivertexbuilder;
                    if (itemStackIn.getItem() == Items.COMPASS && itemStackIn.hasEffect()) {
                        matrixStackIn.push();
                        MatrixStack.Entry matrixstack$entry = matrixStackIn.getLast();
                        if (transformTypeIn == ItemCameraTransforms.TransformType.GUI) {
                            matrixstack$entry.getMatrix().mul(0.5F);
                        } else if (transformTypeIn.isFirstPerson()) {
                            matrixstack$entry.getMatrix().mul(0.75F);
                        }

                        if (flag1) {
                            ivertexbuilder = itemRenderer.getDirectGlintVertexBuilder(bufferIn, rendertype, matrixstack$entry);
                        } else {
                            ivertexbuilder = itemRenderer.getGlintVertexBuilder(bufferIn, rendertype, matrixstack$entry);
                        }

                        matrixStackIn.pop();
                    } else if (flag1) {
                        ivertexbuilder = itemRenderer.getEntityGlintVertexBuilder(bufferIn, rendertype, true, itemStackIn.hasEffect());
                    } else {
                        ivertexbuilder = itemRenderer.getBuffer(bufferIn, rendertype, true, itemStackIn.hasEffect());
                    }

                    itemRenderer.renderModel(modelIn, itemStackIn, combinedLightIn, combinedOverlayIn, matrixStackIn, ivertexbuilder);
                }
            } else {
                itemStackIn.getItem().getItemStackTileEntityRenderer().func_239207_a_(itemStackIn, transformTypeIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
            }

            matrixStackIn.pop();
        }
    }

}
