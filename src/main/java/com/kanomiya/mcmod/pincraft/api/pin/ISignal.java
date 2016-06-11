package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraft.util.ResourceLocation;

public interface ISignal
{
    ResourceLocation getType();

    int getLevel();

}
