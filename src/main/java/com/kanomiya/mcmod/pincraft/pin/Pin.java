package com.kanomiya.mcmod.pincraft.pin;

import java.util.Objects;
import java.util.UUID;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IPinModel;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;

public class Pin implements IPin
{
    IPinHolder holder;
    int port;

    IPinModel model;

    IThreadLine thread;
    boolean isOn;

    UUID uuid;

    protected Pin(IPinHolder holder, int port, IPinModel model)
    {
        this.holder = holder;
        this.port = port;

        this.model = model;

        uuid = UUID.randomUUID();
    }

    @Override
    public IThreadLine getThread()
    {
        return thread;
    }

    @Override
    public void setThread(IThreadLine thread)
    {
        this.thread = thread;
    }

    @Override
    public void stripThread()
    {
        if (thread == null) return;

        if (Objects.equals(thread.getSourceUUID(), getUUID())) thread.stripSource();
        if (Objects.equals(thread.getDestinationUUID(), getUUID())) thread.stripDestination();

        thread = null;
    }

    @Override
    public IPinModel getModel()
    {
        return model;
    }

    @Override
    public void setUUID(UUID uuid)
    {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID()
    {
        return uuid;
    }

    @Override
    public boolean isOn()
    {
        return isOn;
    }

    @Override
    public void on()
    {
        isOn = true;
        holder.onPinUpdate(this, isOn);
    }

    @Override
    public void off()
    {
        isOn = false;
        holder.onPinUpdate(this, isOn);
    }


    @Override
    public IPinHolder getPinHolder()
    {
        return holder;
    }

    @Override
    public int getPort()
    {
        return port;
    }




}
