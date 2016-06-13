package com.kanomiya.mcmod.pincraft.api.pin;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IPinHolderProvider<THIS> extends ICapabilityProvider
{
    IPin[] createPins();

}
