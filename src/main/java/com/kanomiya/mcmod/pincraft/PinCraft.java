package com.kanomiya.mcmod.pincraft;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.api.PinCraftAPI.PinCraftAPIEventHandler;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolderProvider;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.client.render.TESRPin;
import com.kanomiya.mcmod.pincraft.item.ItemThread;
import com.kanomiya.mcmod.pincraft.pin.Pin;
import com.kanomiya.mcmod.pincraft.pin.PinCapabilityProvider;
import com.kanomiya.mcmod.pincraft.pin.PinHolder;
import com.kanomiya.mcmod.pincraft.tile.TileEntityPin;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid=PinCraftAPI.MODID, name="PinCraft", version="@VERSION@")
public class PinCraft
{
	@Mod.Instance(PinCraftAPI.MODID)
	public static PinCraft instance = null;

	public static final CreativeTabs tab = new CreativeTabs(PinCraftAPI.MODID) {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Item.getItemFromBlock(PinCraft.Blocks.PIN);
		}
	};

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		GameRegistry.register(PinCraft.Blocks.PIN);
		GameRegistry.register(new ItemBlock(PinCraft.Blocks.PIN).setRegistryName(PinCraft.Blocks.PIN.getRegistryName()));
        GameRegistry.register(PinCraft.Items.THREAD);

        GameRegistry.registerTileEntity(TileEntityPin.class, new ResourceLocation(PinCraftAPI.MODID, "tileEntityPin").toString());


		if (event.getSide().isClient())
		{
			Consumer<Item> simpleRegister = (item) -> ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			BiConsumer<Item, Integer> metaRegister = new BiConsumer<Item, Integer>()
			{
				@Override
				public void accept(Item item, Integer maxMeta)
				{
					for (int i=0; i<=maxMeta; ++i)
					{
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(item.getRegistryName().getResourceDomain(), item.getRegistryName().getResourcePath()), "inventory"));
					}
				}
			};
			BiConsumer<Item, String[]> arrayRegister = new BiConsumer<Item, String[]>()
			{
				@Override
				public void accept(Item item, String[] stringAry)
				{
					for (int i=0; i<stringAry.length; ++i)
					{
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(item.getRegistryName().getResourceDomain(), item.getRegistryName().getResourcePath() + (stringAry[i].equals("") ? "" : "_" + stringAry[i].toLowerCase())), "inventory"));
					}
				}
			};
			BiConsumer<Item, Enum[]> enumRegister = new BiConsumer<Item, Enum[]>()
			{
				@Override
				public void accept(Item item, Enum[] enumAry)
				{
					for (int i=0; i<enumAry.length; ++i)
					{
						ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(item.getRegistryName().getResourceDomain(), item.getRegistryName().getResourcePath() + "_" + enumAry[i].name().toLowerCase()), "inventory"));
					}
				}
			};

			simpleRegister.accept(Item.getItemFromBlock(PinCraft.Blocks.PIN));
			simpleRegister.accept(PinCraft.Items.THREAD);


			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPin.class, new TESRPin());

		}

		CapabilityManager.INSTANCE.register(IPinHolder.class, new IPinHolder.CapStorage(), PinHolder::new);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(PinCraftAPIEventHandler.INSTANCE);
	}

    /*

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
        APIの存在判定?

	    if (ModAPIManager.INSTANCE.hasAPI("com.kanomiya.mcmod.pincraft|API"))
	    {
            com.kanomiya.mcmod.pincraft.api.PinCraftAPI...
	    }

	}
    */



    @SubscribeEvent
    public void onAttachCapEntity(AttachCapabilitiesEvent.Entity event)
    {
        Entity entity = event.getEntity();

        if (entity instanceof EntityPlayer)
        {
            IPinHolder holder = new PinHolder<Entity>(entity, new Pin(Vec3d.ZERO, Vec3d.ZERO, Vec3d.ZERO));

            event.addCapability(PinCraftAPI.RL_CAP_PINHOLDER, new PinCapabilityProvider(holder));
        }

    }

    @SubscribeEvent
    public void onAttachCapTileEntity(AttachCapabilitiesEvent.TileEntity event)
    {
        TileEntity tile = event.getTileEntity();


        if (tile instanceof IPinHolderProvider)
        {
            IPinHolderProvider provider = (IPinHolderProvider) tile;

            IPinHolder holder = new PinHolder<TileEntity>(tile, provider.createPins());

            event.addCapability(PinCraftAPI.RL_CAP_PINHOLDER, new PinCapabilityProvider(holder));
        }

    }


	public static class Blocks
	{
		public static final BlockPin PIN = new BlockPin();
	}

	public static class Items
	{
		public static final ItemThread THREAD = new ItemThread();

	}

}
