package com.kanomiya.mcmod.pincraft.pin;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.util.IPositionable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.Constants.NBT;

public class PinHolder<T> implements IPinHolder
{
    IPin[] pins;
    T owner;

    public PinHolder()
    {

    }

    public PinHolder(T owner, IPin... pins)
    {
        this.owner = owner;
        this.pins = pins;
    }

    @Override
    public int getPinCount()
    {
        return pins.length;
    }

    @Override
    public IPin getPinAt(int index)
    {
        return 0 <= index && index < pins.length ? pins[index] : null;
    }

    @Override
    public int getPinIndexAt(Vec3d pos)
    {
        for (int i=0,len=getPinCount(); i<len; i++)
        {
            AxisAlignedBB box = getPinAt(i).getRelativeBox();
            // System.out.println(box.isVecInside(pos) + ")) " + box + " => " + pos);
            if (box.isVecInside(pos)) return i;
        }

        return -1;
    }

    @Override
    public T getOwner()
    {
        return owner;
    }

    @Override
    public Vec3d getAbsolutePos()
    {
        T owner = getOwner();

        if (owner instanceof TileEntity) return new Vec3d(((TileEntity) owner).getPos());
        if (owner instanceof Entity) return ((Entity) owner).getPositionVector();
        if (owner instanceof IPositionable) return ((IPositionable) owner).getPosition();

        return Vec3d.ZERO;
    }


    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        int count = getPinCount();

        NBTTagList pinsNbt = new NBTTagList();

        for (int i=0; i<count; i++)
        {
            IPin pin = getPinAt(i);
            pinsNbt.appendTag(pin.serializeNBT());
        }

        nbt.setTag("pins", pinsNbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        int count = getPinCount();

        NBTTagList pinsNbt = nbt.getTagList("pins", NBT.TAG_COMPOUND);

        for (int i=0; i<count; i++)
        {
            IPin pin = getPinAt(i);
            pin.deserializeNBT(pinsNbt.getCompoundTagAt(i));
        }

    }

}
