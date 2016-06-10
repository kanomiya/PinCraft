package com.kanomiya.mcmod.pincraft.util.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;

public class RenderUtils
{

    public static Tessellator tesselator()
    {
        return Tessellator.getInstance();
    }

    public static VertexBuffer vertexBuffer()
    {
        return tesselator().getBuffer();
    }

    public static double uvSize(int tileSize, int textureSize)
    {
        return (double) tileSize /textureSize;
    }

    public static class Box
    {
        protected double size;

        public Box(double size)
        {
            this.size = size;
        }

        public Box begin()
        {
            vertexBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
            return this;
        }

        public void draw()
        {
            tesselator().draw();
        }


        public Box polygon(EnumFacing facing, double u, double v)
        {
            return polygon(facing, u, v, 1d, 1d);
        }

        public Box polygon(EnumFacing facing, double u, double v, double w, double h)
        {
            VertexBuffer vertexBufffer = vertexBuffer();

            double ds = size/2;

            switch (facing)
            {
            case UP:
                // top
                vertexBufffer.pos(-ds, -ds, -ds).tex(u, v).endVertex();
                vertexBufffer.pos(ds, -ds, -ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(ds, -ds, ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(-ds, -ds, ds).tex(u, v +h).endVertex();
                break;

            case DOWN:
                // bottom
                vertexBufffer.pos(-ds, ds, -ds).tex(u, v).endVertex();
                vertexBufffer.pos(ds, ds, -ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(ds, ds, ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(-ds, ds, ds).tex(u, v +h).endVertex();
                break;

            case NORTH:
                // north
                vertexBufffer.pos(-ds, -ds, -ds).tex(u, v).endVertex();
                vertexBufffer.pos(ds, -ds, -ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(ds, ds, -ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(-ds, ds, -ds).tex(u, v +h).endVertex();
                break;

            case SOUTH:
                // south
                vertexBufffer.pos(-ds, -ds, ds).tex(u, v).endVertex();
                vertexBufffer.pos(ds, -ds, ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(ds, ds, ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(-ds, ds, ds).tex(u +w, v +h).endVertex();
                break;
            case EAST:
                // east
                vertexBufffer.pos(-ds, -ds, -ds).tex(u, v).endVertex();
                vertexBufffer.pos(-ds, -ds, ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(-ds, ds, ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(-ds, ds, -ds).tex(u, v +h).endVertex();
                break;

            case WEST:
                // west
                vertexBufffer.pos(ds, -ds, -ds).tex(u, v).endVertex();
                vertexBufffer.pos(ds, -ds, ds).tex(u +w, v).endVertex();
                vertexBufffer.pos(ds, ds, ds).tex(u +w, v +h).endVertex();
                vertexBufffer.pos(ds, ds, -ds).tex(u, v +h).endVertex();
                break;
            }

            return this;
        }

    }

}
