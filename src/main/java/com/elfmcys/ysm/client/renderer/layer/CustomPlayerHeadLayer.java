package com.elfmcys.ysm.client.renderer.layer;

import com.elfmcys.ysm.client.entity.CustomPlayerEntity;
import com.elfmcys.ysm.geckolib3.geo.GeoLayerRenderer;
import com.elfmcys.ysm.geckolib3.geo.GeoRenderData;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CustomPlayerHeadLayer extends GeoLayerRenderer<CustomPlayerEntity> {
    public CustomPlayerHeadLayer(EntityRendererProvider.Context context) {
        // Head-item locator rendering is still TODO; no renderer dependency is required here.
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector collector, CustomPlayerEntity animatable, GeoRenderData renderData, int packedLight, int overlay) {
        // TODO: 头部物品骨骼定位渲染尚未在新版实现
    }
}
