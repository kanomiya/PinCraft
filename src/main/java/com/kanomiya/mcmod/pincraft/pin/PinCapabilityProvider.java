package com.kanomiya.mcmod.pincraft.pin;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PinCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
{
    private IPinHolder holder;

    public PinCapabilityProvider(IPinHolder holder)
    {
        this.holder = holder;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        if (capability == PinCraftAPI.CAP_PINHOLDER) return true;
        return false;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        if (capability == PinCraftAPI.CAP_PINHOLDER) return PinCraftAPI.CAP_PINHOLDER.cast(holder);
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return holder.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        holder.deserializeNBT(nbt);
    }

}
