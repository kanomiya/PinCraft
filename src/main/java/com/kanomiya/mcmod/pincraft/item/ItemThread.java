package com.kanomiya.mcmod.pincraft.item;

import java.util.Objects;

import com.kanomiya.mcmod.pincraft.PinCraft;
import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.pin.IPin;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IPinReference;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;
import com.kanomiya.mcmod.pincraft.api.pin.PinReference;
import com.kanomiya.mcmod.pincraft.api.pin.ThreadLine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
        TileEntity tile = worldIn.getTileEntity(pos);
        // System.out.println(hitX +":"+hitY+":"+hitZ);

        if (tile != null && tile.hasCapability(PinCraftAPI.CAP_PINHOLDER, null))
        {
            IPinHolder holder = tile.getCapability(PinCraftAPI.CAP_PINHOLDER, null);

            int index = holder.getPinIndexAt(new Vec3d(hitX, hitY, hitZ));
            if (0 <= index)
            {
                IPinReference pinRef = new PinReference(holder, index);
                IPin pin = pinRef.getPin();
                IThreadLine thread = pin.getThread();

                IPinHolder playerHolder = playerIn.getCapability(PinCraftAPI.CAP_PINHOLDER, null);
                if (playerHolder == null) return EnumActionResult.FAIL;

                IPinReference playerPinRef = new PinReference(playerHolder, 0);
                IPin playerPin = playerPinRef.getPin();

                if (playerPin == null) return EnumActionResult.FAIL;

                if (thread == null)
                {
                    if (playerPin.getThread() == null)
                    {
                        thread = new ThreadLine(pinRef, playerPinRef);

                        pin.setThread(thread);
                        playerPin.setThread(thread);

                    } else
                    {
                        thread = playerPin.getThread();

                        playerPin.stripThread();

                        thread.setDestination(pinRef);

                        pin.setThread(thread);
                    }

                    return EnumActionResult.SUCCESS;

                } else if (Objects.equals(thread.getDestinationUUID(), playerPin.getUUID()))
                {

                    thread.strip();

                    return EnumActionResult.SUCCESS;
                } else return EnumActionResult.FAIL;

            }

            return EnumActionResult.FAIL;
        }

        return EnumActionResult.PASS;
    }

}
