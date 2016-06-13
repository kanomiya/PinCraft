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

    default IPinReference getSource()
    {
        return PinCraftAPI.toPinReference(getSourceUUID());
    }

    default void setSource(IPinReference source)
    {
        setSource(source.getPinUUID());
    }

    default IPinReference getDestination()
    {
        return PinCraftAPI.toPinReference(getDestinationUUID());
    }

    default void setDestination(IPinReference dest)
    {
        setDestination(dest.getPinUUID());
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
        IPinReference source = getSource();
        return source != null ? source.getPin().getKnotOffsetVec() : Vec3d.ZERO;
    }

    default Vec3d getEndPos()
    {
        IPinReference source = getSource();
        IPinReference dest = getDestination();

        if (source == null || dest == null) return Vec3d.ZERO;

        Vec3d srcAbs = source.getPinHolder().getAbsolutePos();
        Vec3d destAbs = dest.getPinHolder().getAbsolutePos();

        Vec3d offset = new Vec3d(srcAbs.xCoord -destAbs.xCoord, srcAbs.yCoord -destAbs.yCoord, destAbs.zCoord -srcAbs.zCoord);

        return offset.add(getStartPos());
    }

}
