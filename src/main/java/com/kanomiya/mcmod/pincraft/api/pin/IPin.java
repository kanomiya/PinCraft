package com.kanomiya.mcmod.pincraft.api.pin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public interface IPin
{
    @Nonnull
    IPinHolder getHolder();
    int getHolderPort();

    IThreadLine getThread();

    void attachThread(@Nullable IThreadLine thread);
    void stripThread();

    Vec3d getKnotOffsetVec();
    AxisAlignedBB getRelativeBox();

    default AxisAlignedBB getAbsoluteBox()
    {
        Vec3d holderPos = getHolder().getHolderPos();
        return getRelativeBox().addCoord(holderPos.xCoord, holderPos.yCoord, holderPos.zCoord);
    }

    default Vec3d getRelativeKnotVec()
    {
        AxisAlignedBB relBox = getRelativeBox();
        return new Vec3d((relBox.maxX+relBox.minX)/2, (relBox.maxY+relBox.minY)/2, (relBox.maxZ+relBox.minZ)/2).add(getKnotOffsetVec());
    }

    default Vec3d getAbsoluteKnotVec()
    {
        Vec3d holderPos = getHolder().getHolderPos();
        return getRelativeKnotVec().addVector(holderPos.xCoord, holderPos.yCoord, holderPos.zCoord);
    }


    @Nullable
    Optional<ISignal> getSignal(ResourceLocation type);



}
