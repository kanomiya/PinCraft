package com.kanomiya.mcmod.pincraft.api.pin;

import java.util.Collection;

import com.google.common.collect.Sets;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

public interface IPinHolder extends INBTSerializable<NBTTagCompound>
{
    int getPinCount();

    IPin getPinAt(int index);

    int getPinIndexAt(Vec3d pos);

    Object getOwner();

    Vec3d getAbsolutePos();

    default void onPinUpdate(IPin pin, boolean isOn)
    {
        Object owner = getOwner();
        if (owner instanceof IPinHolderProvider)
        {
            ((IPinHolderProvider) owner).onPinUpdate(pin, isOn);
        }

    }

    default Collection<IPin> pinSet()
    {
        int len = getPinCount();
        IPin[] array = new IPin[len];

        for (int i=0; i<len; i++)
        {
            array[i] = getPinAt(i);
        }

        return Sets.newHashSet(array);
    }


    public static class CapStorage implements Capability.IStorage<IPinHolder>
    {
        @Override
        public NBTBase writeNBT(Capability<IPinHolder> capability, IPinHolder instance, EnumFacing side)
        {
            return instance.serializeNBT();
        }

        @Override
        public void readNBT(Capability<IPinHolder> capability, IPinHolder instance, EnumFacing side, NBTBase nbt)
        {
            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.deserializeNBT(compound);
        }
    }

}
