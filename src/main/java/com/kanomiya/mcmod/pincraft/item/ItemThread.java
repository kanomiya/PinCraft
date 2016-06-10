package com.kanomiya.mcmod.pincraft.item;

import com.kanomiya.mcmod.pincraft.PinCraft;
import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.entity.EntityThreadKnot;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemThread extends Item
{

	public ItemThread()
	{
        setRegistryName(new ResourceLocation(PinCraftAPI.MODID, "itemThread"));
        setUnlocalizedName(getRegistryName().toString());

        setCreativeTab(PinCraft.tab);
    }

    @Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // TODO: LINK BETWEEN PINS
        Block block = worldIn.getBlockState(pos).getBlock();

        if (! (block instanceof BlockPin))
        {
            return EnumActionResult.PASS;
        }
        else
        {
            if (! worldIn.isRemote)
            {
                attachToPin(playerIn, worldIn, pos);
            }

            return EnumActionResult.SUCCESS;
        }
    }

    public static boolean attachToPin(EntityPlayer player, World worldIn, BlockPos pin)
    {
        if (EntityThreadKnot.getKnotForPosition(worldIn, pin) == null)
        {
            EntityThreadKnot.createKnot(worldIn, pin);
            return true;
        }

        return false;
    }
}
