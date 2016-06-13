package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPin extends INBTSerializable<NBTTagCompound>
{
    IThreadLine getThread();

    void setThread(@Nullable IThreadLine thread);
    void stripThread();

    Vec3d getOffsetVec();
    Vec3d getSizeVec();
    Vec3d getKnotOffsetVec();

    default AxisAlignedBB getRelativeBox()
    {
        Vec3d offset = getOffsetVec();
        Vec3d size = getSizeVec();

        return new AxisAlignedBB(offset.xCoord -size.xCoord/2, offset.yCoord -size.yCoord/2, offset.zCoord -size.zCoord/2, offset.xCoord +size.xCoord/2, offset.yCoord +size.yCoord/2, offset.zCoord +size.zCoord/2).offset(0.5d, 0.5d, 0.5d);
    }

    default Vec3d getRelativeKnotVec()
    {
        AxisAlignedBB relBox = getRelativeBox();
        return new Vec3d((relBox.maxX+relBox.minX)/2, (relBox.maxY+relBox.minY)/2, (relBox.maxZ+relBox.minZ)/2).add(getKnotOffsetVec());
    }


    @Nullable
    Optional<ISignal> getSignal(ResourceLocation type);


    void setUUID(UUID uuid);

    /**
     *
     * @return PinのUUID
     */
    @Nonnull
    UUID getUUID();


    @Override
    default NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setUniqueId("UUID", getUUID());

        IThreadLine thread = getThread();
        if (thread != null)
        {
            nbt.setTag("thread", thread.serializeNBT());
        }

        return nbt;
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt)
    {
        setUUID(nbt.getUniqueId("UUID"));

        if (nbt.hasKey("thread", NBT.TAG_COMPOUND))
        {
            IThreadLine thread = ThreadLine.createFromNBT(nbt.getCompoundTag("thread"));
            setThread(thread);
        }

    }


}
