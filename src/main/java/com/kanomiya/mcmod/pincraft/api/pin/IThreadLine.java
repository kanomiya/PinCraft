package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public interface IThreadLine
{
    IPin getSource();
    void setSource(IPin source);

    IPin getDestination();
    void setDestination(IPin dest);

    Entity getPlacer();

    default Vec3d getStartPos()
    {
        return getSource() != null ? getSource().getAbsoluteKnotVec() : Vec3d.ZERO;
    }

    default Vec3d getEndPos()
    {
        return getDestination() != null ? getDestination().getAbsoluteKnotVec() : getPlacer() != null ? getPlacer().getPositionVector() : getStartPos();
    }

}
