package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.UUID;

public class PinReference implements IPinReference
{
    private IPinHolder pinHolder;
    private int port;

    public PinReference(IPinHolder pinHolder, int port)
    {
        this.pinHolder = pinHolder;
        this.port = port;
    }

    @Override
    public IPinHolder getPinHolder()
    {
        return pinHolder;
    }

    @Override
    public int getPort()
    {
        return port;
    }


    @Override
    public IPin getPin()
    {
        return pinHolder.getPinAt(port);
    }

    @Override
    public UUID getPinUUID()
    {
        return getPin().getUUID();
    }




    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pinHolder == null) ? 0 : pinHolder.hashCode());
        result = prime * result + port;
        return result;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PinReference other = (PinReference) obj;
        if (pinHolder == null)
        {
            if (other.pinHolder != null) return false;
        } else if (!pinHolder.equals(other.pinHolder)) return false;
        if (port != other.port) return false;
        return true;
    }



}
