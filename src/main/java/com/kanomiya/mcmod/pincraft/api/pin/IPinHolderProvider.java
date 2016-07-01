package com.kanomiya.mcmod.pincraft.api.pin;

import javax.annotation.Nonnull;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface IPinHolderProvider extends ICapabilityProvider
{

    void onPinUpdate(IPin pin, boolean isOn);

    @Nonnull
    IPinModel[] createPinModels();

}
