package com.kanomiya.mcmod.pincraft.pin;

import java.util.Objects;
import java.util.UUID;

import com.google.common.base.Optional;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.ISignal;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class Pin implements IPin
{
    IThreadLine thread;
    ISignal signal;

    Vec3d offset;
    Vec3d size;
    Vec3d knotOffset;

    UUID uuid;

    public Pin(Vec3d offset, Vec3d size, Vec3d knotOffset)
    {
        this.offset = offset;
        this.size = size;
        this.knotOffset = knotOffset;

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
    public Optional<ISignal> getSignal(ResourceLocation type)
    {
        if (type == signal.getType()) return Optional.of(signal);
        return Optional.absent();
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
    public Vec3d getOffsetVec()
    {
        return offset;
    }

    @Override
    public Vec3d getSizeVec()
    {
        return size;
    }

    @Override
    public Vec3d getKnotOffsetVec()
    {
        return knotOffset;
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


}
