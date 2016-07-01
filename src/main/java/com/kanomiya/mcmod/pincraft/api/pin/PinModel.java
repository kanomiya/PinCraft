package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraft.util.math.Vec3d;

public class PinModel implements IPinModel
{
    private Vec3d offset, size, knotOffset;

    public PinModel(Vec3d offset, Vec3d size, Vec3d knotOffset)
    {
        this.offset = offset;
        this.size = size;
        this.knotOffset = knotOffset;
    }

    @Override
    public Vec3d getOffset()
    {
        return offset;
    }

    @Override
    public Vec3d getSize()
    {
        return size;
    }

    @Override
    public Vec3d getKnotOffset()
    {
        return knotOffset;
    }



}
