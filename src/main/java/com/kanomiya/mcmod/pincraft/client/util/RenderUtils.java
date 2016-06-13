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
            return 90f;
        case SOUTH:
            return 270f;
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

    public static void translate(Vec3d vec)
    {
        GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);
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

    public static void drawLineBox(Vec3d sizeVec, int rgba)
    {
        drawLineBox(sizeVec, ((rgba >> (16*3)) & 0xff) /0xff, ((rgba >> (16*2)) & 0xff) /0xff, ((rgba >> 16) & 0xff) /0xff, (rgba & 0xff) /0xff);
    }

    public static void drawLineBox(Vec3d sizeVec, float red, float green, float blue, float alpha)
    {
        VertexBuffer vertexBuffer = vertexBuffer();

        vertexBuffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

        Vec3d vec = sizeVec.scale(0.5d);

        for (int i=-1; ; i=1)
        {
            vertexBuffer.pos(-vec.xCoord, i*vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(vec.xCoord, i*vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();

            vertexBuffer.pos(vec.xCoord, i*vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(vec.xCoord, i*vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();

            vertexBuffer.pos(vec.xCoord, i*vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(-vec.xCoord, i*vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();

            vertexBuffer.pos(-vec.xCoord, i*vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(-vec.xCoord, i*vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();

            if (i == 1) break;
        }

        for (int i=-1; ; i=1)
        {
            vertexBuffer.pos(i*vec.xCoord, -vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(i*vec.xCoord, vec.yCoord, -vec.zCoord).color(red, green, blue, alpha).endVertex();

            if (i == 1) break;
        }

        for (int i=-1; ; i=1)
        {
            vertexBuffer.pos(i*vec.xCoord, -vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();
            vertexBuffer.pos(i*vec.xCoord, vec.yCoord, vec.zCoord).color(red, green, blue, alpha).endVertex();

            if (i == 1) break;
        }

        tesselator().draw();
    }

    public static VertexBuffer pos(Vec3d vec)
    {
        vertexBuffer().pos(vec.xCoord, vec.yCoord, vec.zCoord);
        return vertexBuffer();
    }



    public static void quadPolygonWithUV(Vec3d sizeVec, EnumFacing facing, double u, double v)
    {
        quadPolygonWithScaledUV(sizeVec, facing, u, v, 1d, 1d);
    }

    public static void quadPolygonWithScaledUV(Vec3d sizeVec, EnumFacing facing, double u, double v, double w, double h)
    {
        VertexBuffer vertexBuffer = vertexBuffer();

        Vec3d vec = sizeVec.scale(0.5d);


        switch (facing)
        {
        case DOWN:
            // bottom
            vec = new Vec3d(vec.xCoord, -vec.yCoord, vec.zCoord);
        case UP:
            // top
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u, v).endVertex();
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u +w, v).endVertex();
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, vec.zCoord).tex(u +w, v +h).endVertex();
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, vec.zCoord).tex(u, v +h).endVertex();
            break;

        case NORTH:
            // north
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u, v).endVertex();
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u +w, v).endVertex();
            vertexBuffer.pos(vec.xCoord, vec.yCoord, -vec.zCoord).tex(u +w, v +h).endVertex();
            vertexBuffer.pos(-vec.xCoord, vec.yCoord, -vec.zCoord).tex(u, v +h).endVertex();
            break;

        case SOUTH:
            // south
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, vec.zCoord).tex(u, v).endVertex();
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, vec.zCoord).tex(u +w, v).endVertex();
            vertexBuffer.pos(vec.xCoord, vec.yCoord, vec.zCoord).tex(u +w, v +h).endVertex();
            vertexBuffer.pos(-vec.xCoord, vec.yCoord, vec.zCoord).tex(u +w, v +h).endVertex();
            break;
        case EAST:
            // east
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u, v).endVertex();
            vertexBuffer.pos(-vec.xCoord, -vec.yCoord, vec.zCoord).tex(u +w, v).endVertex();
            vertexBuffer.pos(-vec.xCoord, vec.yCoord, vec.zCoord).tex(u +w, v +h).endVertex();
            vertexBuffer.pos(-vec.xCoord, vec.yCoord, -vec.zCoord).tex(u, v +h).endVertex();
            break;

        case WEST:
            // west
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, -vec.zCoord).tex(u, v).endVertex();
            vertexBuffer.pos(vec.xCoord, -vec.yCoord, vec.zCoord).tex(u +w, v).endVertex();
            vertexBuffer.pos(vec.xCoord, vec.yCoord, vec.zCoord).tex(u +w, v +h).endVertex();
            vertexBuffer.pos(vec.xCoord, vec.yCoord, -vec.zCoord).tex(u, v +h).endVertex();
            break;
        }

    }



    public static class Box
    {
        protected Vec3d sizeVec;

        public Box(double size)
        {
           sizeVec = new Vec3d(size, size, size);
        }

        public Box(Vec3d sizeVec)
        {
            this.sizeVec = sizeVec;
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
            quadPolygonWithScaledUV(sizeVec, facing, u, v, w, h);
            return this;
        }

    }

}
