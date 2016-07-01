package com.kanomiya.mcmod.pincraft.client.render;

import java.util.Objects;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IPinModel;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.client.util.RenderUtils;
import com.kanomiya.mcmod.pincraft.tile.TileEntityPin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
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
        IPinHolder pinHolder = tile.getCapability(PinCraftAPI.CAP_PINHOLDER, null);

        IBlockState state = getWorld().getBlockState(tile.getPos());
        EnumFacing facing = state.getValue(BlockPin.FACING);


        GlStateManager.pushMatrix();

        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);


        bindTexture(THREAD_KNOT_TEXTURES);

        GlStateManager.translate(x +0.5d, y +0.5d, z +0.5d);

        float yaw = RenderUtils.getYaw(facing);
        float pitch = RenderUtils.getPitch(facing);


        GlStateManager.disableCull();
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        GlStateManager.disableLighting();

        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4d(1.0d, 0.5d, 0.65d, 1d);

        double knotSize = 0.2d;

        int uWidth = 32;
        int vHeight = 16;

        for (int i=0; i<pinHolder.getPinCount(); i++)
        {
            IPin pin = pinHolder.getPinAt(i);
            IPinModel model = pin.getModel();
            IThreadLine thread = pin.getThread();

            GlStateManager.enableTexture2D();

            GL11.glPushMatrix();

            GL11.glRotated(yaw, 1d, 0d, 0d);
            GL11.glRotated(pitch, 0d, 0d, 1d);

            RenderUtils.translate(model.getOffset());

            boolean hasSource = thread != null && thread.getSourceUUID() != null;
            boolean hasDest = thread != null && thread.getDestinationUUID() != null;

            if (thread != null)
            {
                GL11.glPushMatrix();
                RenderUtils.translate(model.getKnotOffset());

                double uvSize16 = RenderUtils.uvSize(16, uWidth);

                new RenderUtils.Box(knotSize).begin()
                    .polygon(EnumFacing.UP, uvSize16, 0, uvSize16, 1d)
                    .polygon(EnumFacing.DOWN, uvSize16, 0, uvSize16, 1d)
                    .polygon(EnumFacing.NORTH, 0, 0, uvSize16, 1d)
                    .polygon(EnumFacing.SOUTH, 0, 0, uvSize16, 1d)
                    .polygon(EnumFacing.EAST, 0, 0, uvSize16, 1d)
                    .polygon(EnumFacing.WEST, 0, 0, uvSize16, 1d)
                    .draw();

                GL11.glPopMatrix();
            }

            GlStateManager.disableTexture2D();


            if (Minecraft.getMinecraft().thePlayer.isCreative())
            {
                RenderUtils.drawLineBox(model.getSize(), 0xff00ffff);
            }

            GL11.glPopMatrix();

            if (hasDest && ! Objects.equals(thread.getDestinationUUID(), pin.getUUID()))
            {
                Vec3d startPos = thread.getStartPos();
                Vec3d endPos = thread.getEndPos();

                RenderUtils.drawLine(startPos, endPos, 0xffffffff);
            }
        }

        GL11.glPopMatrix();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        GL11.glPopAttrib();
    }

}
