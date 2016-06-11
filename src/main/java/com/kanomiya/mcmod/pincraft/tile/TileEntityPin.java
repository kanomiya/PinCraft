package com.kanomiya.mcmod.pincraft.tile;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.pin.Pin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;

public class TileEntityPin extends TileEntity implements ITickable, IPinHolder
{
    IPin pin;

    public TileEntityPin(int meta)
    {
        pin = new Pin(this, 0, BlockPin.PIN_AABB[meta], new Vec3d(0,0,0));
    }

    @Override
    public void update()
    {

    }

    @Override
    public int getPinCount()
    {
        return 1;
    }

    @Override
    public IPin getPinAt(int index)
    {
        return pin;
    }

    @Override
    public int getPinIndexAt(double hitX, double hitY, double hitZ)
    {
        return 0;
    }


    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);



    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound = super.writeToNBT(compound);


        return compound;
    }

    @Override
    public Vec3d getHolderPos()
    {
        return new Vec3d(getPos());
    }



}
