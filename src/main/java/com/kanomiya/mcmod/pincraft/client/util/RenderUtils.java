package com.kanomiya.mcmod.pincraft.client.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;

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



    public static float getYaw(EnumFacing facing)
    {
        switch (facing)
        {
        case UP:
            break;
        case DOWN:
            return 180f;
        case NORTH:
            return 270f;
        case SOUTH:
            return 90f;
        case EAST:
            break;
        case WEST:
            return 270f;
        }

        return 0f;
    }

    public static float getPitch(EnumFacing facing)
    {
        switch (facing)
        {
        case UP:
            break;
        case DOWN:
            break;
        case NORTH:
            break;
        case SOUTH:
            break;
        case EAST:
            return -90f;
        case WEST:
            return 90f;
        }

        return 0f;
    }



    public static double uvSize(int tileSize, int textureSize)
    {
        return (double) tileSize /textureSize;
    }



    public static void drawLine(Vec3d from, Vec3d to, int rgba)
    {
        drawLine(from, to, ((rgba >> (16*3)) & 0xff) /0xff, ((rgba >> (16*2)) & 0xff) /0xff, ((rgba >> 16) & 0xff) /0xff, (rgba & 0xff) /0xff);
    }

    public static void drawLine(Vec3d from, Vec3d to, float red, float green, float blue, float alpha)
    {
        VertexBuffer vertexBuffer = vertexBuffer();

        vertexBuffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        vertexBuffer.pos(from.xCoord, from.yCoord, from.zCoord).color(red, green, blue, alpha).endVertex();
        vertexBuffer.pos(to.xCoord, to.yCoord, to.zCoord).color(red, green, blue, alpha).endVertex();

        tesselator().draw();
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
