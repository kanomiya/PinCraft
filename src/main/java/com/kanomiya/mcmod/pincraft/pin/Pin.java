package com.kanomiya.mcmod.pincraft.pin;

import com.google.common.base.Optional;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.ISignal;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class Pin implements IPin
{
    IPinHolder holder;
    int port;

    IThreadLine thread;
    ISignal signal;

    AxisAlignedBB boundingBox;
    Vec3d knotOffset;

    public Pin(IPinHolder holder, int port, AxisAlignedBB boundingBox, Vec3d knotOffset)
    {
        this.holder = holder;
        this.port = port;
        this.boundingBox = boundingBox;
        this.knotOffset = knotOffset;
    }

    @Override
    public IPinHolder getHolder()
    {
        return holder;
    }

    @Override
    public int getHolderPort()
    {
        return port;
    }

    @Override
    public IThreadLine getThread()
    {
        return thread;
    }

    @Override
    public void attachThread(IThreadLine thread)
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

        if (thread.getSource() == this) thread.setSource(null);
        if (thread.getDestination() == this) thread.setDestination(null);
    }

    @Override
    public Vec3d getKnotOffsetVec()
    {
        return knotOffset;
    }

    @Override
    public AxisAlignedBB getRelativeBox()
    {
        return boundingBox;
    }







}
