package com.kanomiya.mcmod.pincraft.tile;

import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolderProvider;
import com.kanomiya.mcmod.pincraft.pin.Pin;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3d;

public class TileEntityPin extends TileEntity implements ITickable, IPinHolderProvider<TileEntityPin>
{
    public TileEntityPin()
    {
        super();
    }

    @Override
    public void update()
    {

    }

    @Override
    public IPin[] createPins()
    {
        return new IPin[]
                {
                        new Pin(new Vec3d(0d,0.05d,0d), new Vec3d(0.2d,0.95d,0.2d), new Vec3d(0d,-0.15d,0d))
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
