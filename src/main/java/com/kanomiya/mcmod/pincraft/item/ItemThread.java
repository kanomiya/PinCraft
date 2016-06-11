package com.kanomiya.mcmod.pincraft.item;

import com.kanomiya.mcmod.pincraft.PinCraft;
import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;
import com.kanomiya.mcmod.pincraft.entity.EntityThreadKnot;
import com.kanomiya.mcmod.pincraft.pin.ThreadLine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
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
        setMaxStackSize(1);
    }

    @Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // TODO: LINK BETWEEN PINS
        TileEntity tile = worldIn.getTileEntity(pos);
        // System.out.println(hitX +":"+hitY+":"+hitZ);

        if (tile instanceof IPinHolder)
        {
            IPinHolder holder = (IPinHolder) tile;

            int index = holder.getPinIndexAt(hitX, hitY, hitZ);
            if (0 <= index)
            {
                IPin pin = holder.getPinAt(index);
                IThreadLine thread = pin.getThread();

                // TODO: 以下クソ実装

                NBTTagCompound nbt = stack.getSubCompound("sourcePin", false);

                if (thread == null && nbt != null)
                {
                    TileEntity sourceTile = worldIn.getTileEntity(new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z")));
                    if (! (sourceTile instanceof IPinHolder)) return EnumActionResult.FAIL;

                    IPinHolder sourceHolder = (IPinHolder) sourceTile;
                    IPin source = sourceHolder.getPinAt(nbt.getInteger("port"));

                    if (source == null || source.getThread() == null) return EnumActionResult.FAIL;

                    thread = source.getThread();
                    thread.setDestination(pin);
                }
                else thread = new ThreadLine(pin, playerIn);

                pin.attachThread(thread);

                if (nbt == null)
                {
                    nbt = new NBTTagCompound();

                    nbt.setInteger("x", pos.getX());
                    nbt.setInteger("y", pos.getY());
                    nbt.setInteger("z", pos.getZ());
                    nbt.setInteger("port", pin.getHolderPort());

                    stack.setTagInfo("sourcePin", nbt);
                } else
                {
                    stack.getTagCompound().removeTag("sourcePin");
                }

                return EnumActionResult.SUCCESS;
            }

            return EnumActionResult.FAIL;
        }

        return EnumActionResult.PASS;
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
