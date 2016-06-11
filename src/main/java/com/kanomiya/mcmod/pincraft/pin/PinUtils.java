package com.kanomiya.mcmod.pincraft.pin;

import com.google.common.base.Optional;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.entity.EntityThreadKnot;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PinUtils
{

    public static Optional<IPinHolder> getPinHolderAt(World worldIn, BlockPos pin)
    {
        TileEntity tile = worldIn.getTileEntity(pin);
        if (tile instanceof IPinHolder)
        {
            return Optional.of((IPinHolder) tile);
        }

        return Optional.absent();
    }

    public static EntityThreadKnot createKnot(World worldIn, BlockPos pin)
    {
        EntityThreadKnot entityThreadKnot = EntityThreadKnot.createKnot(worldIn, pin);


        return entityThreadKnot;
    }

    public static EntityThreadKnot getKnotForPosition(World worldIn, BlockPos pos)
    {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();

        for (EntityThreadKnot entityThreadKnot : worldIn.getEntitiesWithinAABB(EntityThreadKnot.class, new AxisAlignedBB(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D)))
        {
            if (entityThreadKnot.getHangingPosition().equals(pos))
            {
                return entityThreadKnot;
            }
        }

        return null;
    }


}
