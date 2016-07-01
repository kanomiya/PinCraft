package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPin extends INBTSerializable<NBTTagCompound>
{
    IThreadLine getThread();

    void setThread(@Nullable IThreadLine thread);
    void stripThread();

    IPinHolder getPinHolder();
    int getPort();

    IPinModel getModel();

    boolean isOn();
    void on();
    void off();


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
