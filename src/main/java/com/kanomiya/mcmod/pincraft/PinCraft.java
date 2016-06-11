package com.kanomiya.mcmod.pincraft;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.block.BlockPin;
import com.kanomiya.mcmod.pincraft.client.render.RenderThreadKnot;
import com.kanomiya.mcmod.pincraft.client.render.TESRPin;
import com.kanomiya.mcmod.pincraft.entity.EntityThreadKnot;
import com.kanomiya.mcmod.pincraft.item.ItemThread;
import com.kanomiya.mcmod.pincraft.tile.TileEntityPin;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
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

		EntityRegistry.registerModEntity(EntityThreadKnot.class, "entityThreadKnot", 0, instance, 64, 1, false);

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


			RenderingRegistry.registerEntityRenderingHandler(EntityThreadKnot.class, RenderThreadKnot::new);
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPin.class, new TESRPin());

		}

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{

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
