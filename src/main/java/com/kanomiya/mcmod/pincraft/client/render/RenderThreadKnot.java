package com.kanomiya.mcmod.pincraft.client.render;

import org.lwjgl.opengl.GL11;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.client.util.RenderUtils;
import com.kanomiya.mcmod.pincraft.entity.EntityThreadKnot;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Deprecated
public class RenderThreadKnot extends Render<EntityThreadKnot>
{
    private static final ResourceLocation THREAD_KNOT_TEXTURES = new ResourceLocation(PinCraftAPI.MODID, "textures/entity/entityThreadKnot.png");

    public RenderThreadKnot(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    @Override
	public void doRender(EntityThreadKnot entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);

        GL11.glRotated(entity.rotationYaw, 1d, 0d, 0d);
        GL11.glRotated(entity.rotationPitch, 0d, 0d, 1d);

        GlStateManager.translate(0d, 0.15d, 0d);


        GlStateManager.disableCull();
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        bindEntityTexture(entity);

        if (renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entity));
        }

        GlStateManager.disableLighting();

        GL11.glEnable(GL11.GL_BLEND);
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

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        GL11.glPopAttrib();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
	protected ResourceLocation getEntityTexture(EntityThreadKnot entity)
    {
        return THREAD_KNOT_TEXTURES;
    }
}
