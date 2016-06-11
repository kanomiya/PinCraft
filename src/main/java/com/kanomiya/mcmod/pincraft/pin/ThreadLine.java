package com.kanomiya.mcmod.pincraft.pin;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;

import net.minecraft.entity.Entity;

public class ThreadLine implements IThreadLine
{
    IPin source;
    IPin dest;

    Entity placer;

    public ThreadLine(IPin source, Entity placer)
    {
        this.source = source;
        this.placer = placer;
    }

    @Override
    public IPin getSource()
    {
        return source;
    }

    @Override
    public void setSource(IPin source)
    {
        this.source = source;
    }

    @Override
    public IPin getDestination()
    {
        return dest;
    }

    @Override
    public void setDestination(IPin dest)
    {
        this.dest = dest;
    }


    @Override
    public Entity getPlacer()
    {
        return placer;
    }


}
