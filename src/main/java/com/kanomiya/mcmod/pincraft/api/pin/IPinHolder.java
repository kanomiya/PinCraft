package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraft.util.math.Vec3d;

public interface IPinHolder
{
    int getPinCount();

    IPin getPinAt(int index);

    int getPinIndexAt(double hitX, double hitY, double hitZ);

    Vec3d getHolderPos();

}
