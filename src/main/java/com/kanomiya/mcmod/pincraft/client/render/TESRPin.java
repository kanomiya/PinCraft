package com.kanomiya.mcmod.pincraft.client.render;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.client.util.RenderUtils;
import com.kanomiya.mcmod.pincraft.tile.TileEntityPin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class TESRPin extends TileEntitySpecialRenderer<TileEntityPin>
{
    private static final ResourceLocation THREAD_KNOT_TEXTURES = new ResourceLocation(PinCraftAPI.MODID, "textures/tileentity/tileEntityThreadKnot.png");

    public TESRPin()
    {

    }


    @Override
    public void renderTileEntityAt(TileEntityPin tile, double x, double y, double z, float partialTicks, int destroyStage)
    {
        IPin pin = tile.getPinAt(0);
        IThreadLine thread = pin.getThread();
        if (thread == null) return ;

        boolean hasSource = thread.getSource() != null;
        boolean hasDest = thread.getDestination() != null;
        boolean hasPlacer = thread.getPlacer() != null;



        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
        GlStateManager.pushMatrix();

        GlStateManager.translate(x +0.5d, y +0.5d, z +0.5d);

        IBlockState state = getWorld().getBlockState(tile.getPos());
        EnumFacing facing = state.getValue(BlockPin.FACING);

        GL11.glRotated(RenderUtils.getYaw(facing), 1d, 0d, 0d);
        GL11.glRotated(RenderUtils.getPitch(facing), 0d, 0d, 1d);

        GlStateManager.translate(0d, 0.15d, 0d);


        GlStateManager.disableCull();
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        bindTexture(THREAD_KNOT_TEXTURES);

        GlStateManager.disableLighting();

        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4d(1.0d, 0.5d, 0.65d, 1d);


        int uWidth = 32;
        int vHeight = 16;

        double uvSize16 = RenderUtils.uvSize(16, uWidth);

        new RenderUtils.Box(0.2d).begin()
            .polygon(EnumFacing.UP, uvSize16, 0, uvSize16, 1d)
            .polygon(EnumFacing.DOWN, uvSize16, 0, uvSize16, 1d)
            .polygon(EnumFacing.NORTH, 0, 0, uvSize16, 1d)
            .polygon(EnumFacing.SOUTH, 0, 0, uvSize16, 1d)
            .polygon(EnumFacing.EAST, 0, 0, uvSize16, 1d)
            .polygon(EnumFacing.WEST, 0, 0, uvSize16, 1d)
            .draw();

        GL11.glPopMatrix();

        GlStateManager.disableTexture2D();

        GL11.glPushMatrix();

        GlStateManager.translate(-TileEntityRendererDispatcher.staticPlayerX, -TileEntityRendererDispatcher.staticPlayerY, -TileEntityRendererDispatcher.staticPlayerZ);

        if (! hasDest && hasPlacer)
        {
            Vec3d startPos = thread.getStartPos();
            Vec3d endPos = thread.getEndPos();

            RenderUtils.drawLine(startPos, endPos, 0xffffffff);
        }

        if (hasDest && thread.getDestination() != pin)
        {
            Vec3d startPos = pin.getAbsoluteKnotVec();
            Vec3d endPos = thread.getDestination().getAbsoluteKnotVec();

            RenderUtils.drawLine(startPos, endPos, 0xffffffff);
        }

        GL11.glPopMatrix();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        GL11.glPopAttrib();
    }

}
