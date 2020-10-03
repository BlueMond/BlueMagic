package me.bluemond.bluemagic.unused;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;

public class BlueRenderTypes extends RenderType {

    public BlueRenderTypes(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
    }

    public static RenderType getItemEntityTranslucent(ResourceLocation locationIn) {
        RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false)).transparency(TRANSLUCENT_TRANSPARENCY).target(field_241712_U_).diffuseLighting(DIFFUSE_LIGHTING_ENABLED).alpha(DEFAULT_ALPHA).cull(CULL_DISABLED).lightmap(LIGHTMAP_ENABLED).overlay(OVERLAY_ENABLED).writeMask(RenderState.COLOR_DEPTH_WRITE).build(true);
        return makeType("item_entity_translucent", DefaultVertexFormats.ENTITY, 7, 256, true, true, rendertype$state);
    }

    public static RenderType getTranslucentEntity(ResourceLocation locationIn){
        RenderType.State rendertype$state = RenderType.State.getBuilder().texture(new RenderState.TextureState(locationIn, false, false))
                .cull(CULL_DISABLED)
                .alpha(AlphaState.DEFAULT_ALPHA)
                .transparency(TransparencyState.CRUMBLING_TRANSPARENCY)
                .diffuseLighting(DiffuseLightingState.DIFFUSE_LIGHTING_ENABLED)
                .lightmap(LIGHTMAP_ENABLED)
                .overlay(OVERLAY_ENABLED)
                .build(true);
        return makeType("entity_translucent", DefaultVertexFormats.BLOCK, 7, 256, true, true, rendertype$state);
    }
}
