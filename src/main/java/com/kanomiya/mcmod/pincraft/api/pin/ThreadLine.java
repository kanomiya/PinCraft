package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

public class ThreadLine implements IThreadLine
{
    UUID sourceUUID;
    UUID destUUID;

    public static ThreadLine createFromNBT(NBTTagCompound nbt)
    {
        ThreadLine thread = new ThreadLine();

        thread.deserializeNBT(nbt);

        return thread;
    }

    protected ThreadLine()
    {

    }

    public ThreadLine(UUID sourceUUID, UUID destUUID)
    {
        this.sourceUUID = sourceUUID;
        this.destUUID = destUUID;
    }

    public ThreadLine(IPin source, IPin dest)
    {
        sourceUUID = source.getUUID();
        destUUID = dest.getUUID();
    }

    @Override
    public UUID getSourceUUID()
    {
        return sourceUUID;
    }

    @Override
    public void setSource(UUID source)
    {
        this.sourceUUID = source;
    }

    @Override
    public UUID getDestinationUUID()
    {
        return destUUID;
    }

    @Override
    public void setDestination(UUID dest)
    {
        this.destUUID = dest;
    }

    @Override
    public void stripSource()
    {
        if (sourceUUID == null) return ;

        IPin pin = getSource();
        if (pin != null)
        {
            pin.setThread(null);
        }

        sourceUUID = null;
    }

    @Override
    public void stripDestination()
    {
        if (destUUID == null) return ;

        IPin pin = getDestination();
        if (pin != null)
        {
            pin.setThread(null);
        }

        destUUID = null;
    }


}
