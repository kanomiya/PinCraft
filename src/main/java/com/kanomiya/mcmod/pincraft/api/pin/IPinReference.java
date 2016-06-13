package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

public interface IPinReference
{
    IPinHolder getPinHolder();

    int getPort();

    IPin getPin();

    UUID getPinUUID();

}
