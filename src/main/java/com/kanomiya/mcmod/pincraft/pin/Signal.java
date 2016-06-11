package com.kanomiya.mcmod.pincraft.pin;

import com.kanomiya.mcmod.pincraft.api.pin.ISignal;

import net.minecraft.util.ResourceLocation;

public class Signal implements ISignal
{
    protected ResourceLocation type;
    protected int level;

    public Signal(ResourceLocation type, int level)
    {
        this.type = type;
        this.level = level;
    }

    public ResourceLocation getType()
    {
        return type;
    }

    public int getLevel()
    {
        return level;
    }

}
