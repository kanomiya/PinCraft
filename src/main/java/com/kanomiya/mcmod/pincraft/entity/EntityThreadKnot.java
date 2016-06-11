package com.kanomiya.mcmod.pincraft.entity;

import javax.annotation.Nullable;

import com.kanomiya.mcmod.pincraft.PinCraft;
import com.kanomiya.mcmod.pincraft.block.BlockPin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Deprecated
public class EntityThreadKnot extends EntityLeashKnot
{
	public EntityThreadKnot(World worldIn)
	{
		super(worldIn);
        ignoreFrustumCheck = true;
	}

	public EntityThreadKnot(World worldIn, BlockPos hangingPositionIn)
	{
		super(worldIn, hangingPositionIn);

		ignoreFrustumCheck = true;
	}

    @Override
	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand)
    {
        return true;
    }

    @Override
    public void onBroken(@Nullable Entity brokenEntity)
    {
        super.onBroken(brokenEntity);

        entityDropItem(new ItemStack(PinCraft.Items.THREAD), 0.1f);
    }

    @Override
    public boolean onValidSurface()
    {
        return this.worldObj.getBlockState(this.hangingPosition).getBlock() instanceof BlockPin;
    }

    public static EntityThreadKnot createKnot(World worldIn, BlockPos pin)
    {
    	EntityThreadKnot entityThreadKnot = new EntityThreadKnot(worldIn, pin);
        entityThreadKnot.forceSpawn = true;

        EnumFacing facing = worldIn.getBlockState(pin).getValue(BlockPin.FACING);


        switch (facing)
        {
        case UP:
            break;
        case DOWN:
            entityThreadKnot.rotationYaw = 180f;
            break;
        case NORTH:
            entityThreadKnot.rotationYaw = 270f;
            break;
        case SOUTH:
            entityThreadKnot.rotationYaw = 90f;
            break;
        case EAST:
            entityThreadKnot.rotationPitch = -90f;
            break;
        case WEST:
            entityThreadKnot.rotationYaw = 270f;
            entityThreadKnot.rotationPitch = 90f;
            break;
        }


        worldIn.spawnEntityInWorld(entityThreadKnot);
        entityThreadKnot.playPlaceSound();
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
