package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.INBTSerializable;

public interface IThreadLine extends INBTSerializable<NBTTagCompound>
{

    UUID getSourceUUID();
    UUID getDestinationUUID();

    void setSource(UUID source);
    void setDestination(UUID dest);

    default void strip()
    {
        stripSource();
        stripDestination();
    }

    void stripSource();
    void stripDestination();

    default IPin getSource()
    {
        return PinCraftAPI.toPin(getSourceUUID());
    }

    default void setSource(IPin source)
    {
        setSource(source.getUUID());
    }

    default IPin getDestination()
    {
        return PinCraftAPI.toPin(getDestinationUUID());
    }

    default void setDestination(IPin dest)
    {
        setDestination(dest.getUUID());
    }


    @Override
    default NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        if (getSource() != null)
        {
            nbt.setUniqueId("sourceUUID", getSourceUUID());
        }

        if (getDestination() != null)
        {
            nbt.setUniqueId("destUUID", getDestinationUUID());
        }

        return nbt;
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt)
    {
        if (nbt.hasUniqueId("sourceUUID"))
        {
            setSource(nbt.getUniqueId("sourceUUID"));
        }

        if (nbt.hasUniqueId("destUUID"))
        {
            setDestination(nbt.getUniqueId("destUUID"));
        }

    }


    default Vec3d getStartPos()
    {
        IPin source = getSource();
        return source != null ? source.getModel().getKnotOffset() : Vec3d.ZERO;
    }

    default Vec3d getEndPos()
    {
        IPin source = getSource();
        IPin dest = getDestination();

        if (source == null || dest == null) return Vec3d.ZERO;

        Vec3d srcAbs = source.getPinHolder().getAbsolutePos();
        Vec3d destAbs = dest.getPinHolder().getAbsolutePos();

        Vec3d offset = new Vec3d(srcAbs.xCoord -destAbs.xCoord, srcAbs.yCoord -destAbs.yCoord, destAbs.zCoord -srcAbs.zCoord);

        return offset.add(getStartPos());
    }

}
