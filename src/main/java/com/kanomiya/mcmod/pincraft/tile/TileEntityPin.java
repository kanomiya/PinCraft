package com.kanomiya.mcmod.pincraft.tile;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolderProvider;
import com.kanomiya.mcmod.pincraft.api.pin.IPinModel;
import com.kanomiya.mcmod.pincraft.api.pin.PinModel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.Vec3d;

public class TileEntityPin extends TileEntity implements IPinHolderProvider
{

    public TileEntityPin()
    {
        super();
    }



    @Override
    public void onPinUpdate(IPin pin, boolean isOn)
    {

    }

    @Override
    public IPinModel[] createPinModels()
    {
        return new IPinModel[]
                {
                        new PinModel(new Vec3d(0d,0.05d,0d), new Vec3d(0.2d,0.95d,0.2d), new Vec3d(0d,-0.15d,0d))
                };
    }


    @Override public SPacketUpdateTileEntity getUpdatePacket()
    {   NBTTagCompound nbtTagCompound = new NBTTagCompound();
        nbtTagCompound = writeToNBT(nbtTagCompound);
        return new SPacketUpdateTileEntity(pos, 1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }









}
