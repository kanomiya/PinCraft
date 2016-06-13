package com.kanomiya.mcmod.pincraft.api;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.kanomiya.mcmod.pincraft.api.pin.IPinHolder;
import com.kanomiya.mcmod.pincraft.api.pin.IPinReference;
import com.kanomiya.mcmod.pincraft.api.pin.IThreadLine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PinCraftAPI
{
	public static final String MODID = "com.kanomiya.mcmod.pincraft";

	@CapabilityInject(IPinHolder.class)
	public static final Capability<IPinHolder> CAP_PINHOLDER = null;
	public static final ResourceLocation RL_CAP_PINHOLDER = new ResourceLocation(MODID, "pinHolder");

	public static final List<IPinReference> loadedPins = Lists.newArrayList();

	/**
	 *
	 * Pinが読み込まれていない場合、または該当するPinが存在しない場合、nullを返します
	 *
	 * @param uuid
	 * @return
	 */
	@Nullable
	public static IPinReference toPinReference(UUID uuid)
	{
	    if (uuid == null) return null;

	    Iterator<IPinReference> itr = loadedPins.iterator();

	    while (itr.hasNext())
	    {
	        IPinReference pin = itr.next();

	        if (pin.getPin().getUUID() == null) continue;
	        if (pin.getPin().getUUID().equals(uuid))
	        {
	            return pin;
	        }
	    }

	    return null;
	}

	public static enum PinCraftAPIEventHandler
	{
	    INSTANCE;

        protected void loadPinHolder(IPinHolder holder)
        {
            if (holder == null) return;

            holder.pinSet().stream().forEach((pinRef) ->
            {
                // IPin pin = pinRef.getPin();
                // UUID uuid = pin.getUUID();

                if (! loadedPins.contains(pinRef)) loadedPins.add(pinRef);
            });
        }

        protected void unloadPinHolder(IPinHolder holder)
        {
            if (holder == null) return;

            holder.pinSet().stream().forEach((pinRef) ->
            {
                // IPin pin = pinRef.getPin();
                // UUID uuid = pin.getUUID();

                loadedPins.remove(pinRef);
            });
        }

        @SubscribeEvent
        public void onWorldLoad(WorldEvent.Load event)
        {
            World world = event.getWorld();

            world.addEventListener(new IWorldEventListener()
                    {

                        @Override
                        public void onEntityAdded(Entity entityIn)
                        {
                            if (entityIn.hasCapability(CAP_PINHOLDER, null))
                            {
                                loadPinHolder(entityIn.getCapability(CAP_PINHOLDER, null));
                            }
                        }

                        @Override
                        public void onEntityRemoved(Entity entityIn)
                        {
                            if (entityIn.hasCapability(CAP_PINHOLDER, null))
                            {
                                unloadPinHolder(entityIn.getCapability(CAP_PINHOLDER, null));
                            }
                        }

                        @Override
                        public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState,
                                IBlockState newState, int flags)
                        {
                            TileEntity tile = worldIn.getTileEntity(pos);
                            if (tile != null && tile.hasCapability(CAP_PINHOLDER, null))// && ! te.shouldRefresh(worldIn, pos, oldState, newState))
                            {
                                loadPinHolder(tile.getCapability(CAP_PINHOLDER, null));
                            }

                        }

                        @Override
                        public void notifyLightSet(BlockPos pos)
                        {
                        }

                        @Override
                        public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2)
                        {
                        }

                        @Override
                        public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn,
                                SoundCategory category, double x, double y, double z, float volume, float pitch)
                        {
                        }

                        @Override
                        public void playRecord(SoundEvent soundIn, BlockPos pos)
                        {
                        }

                        @Override
                        public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord,
                                double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
                        {
                        }

                        @Override
                        public void broadcastSound(int soundID, BlockPos pos, int data)
                        {
                        }

                        @Override
                        public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data)
                        {
                        }

                        @Override
                        public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress)
                        {
                        }
                    });
        }

        @SubscribeEvent
        public void onWorldUnload(WorldEvent.Unload event)
        {
            loadedPins.clear();
        }

        @SubscribeEvent
        public void onChunkLoad(ChunkEvent.Load event) // PlayerEvent
        {
            Chunk chunk = event.getChunk();

            chunk.getTileEntityMap().values().stream().filter((tile) -> tile.hasCapability(CAP_PINHOLDER, null)).forEach((tile) ->
            {
                loadPinHolder(tile.getCapability(CAP_PINHOLDER, null));
            });

        }

        @SubscribeEvent
        public void onChunkUnload(ChunkEvent.Unload event)
        {
            Chunk chunk = event.getChunk();

            chunk.getTileEntityMap().values().stream().filter((tile) -> tile.hasCapability(CAP_PINHOLDER, null)).forEach((tile) ->
            {
                unloadPinHolder(tile.getCapability(CAP_PINHOLDER, null));
            });

        }

        @SubscribeEvent
        public void onBlockBreak(BlockEvent.BreakEvent event)
        {
            TileEntity tile = event.getWorld().getTileEntity(event.getPos());

            if (tile != null && tile.hasCapability(CAP_PINHOLDER, null))
            {
                IPinHolder holder = tile.getCapability(CAP_PINHOLDER, null);

                holder.pinSet().stream().forEach((pinRef) ->
                {
                    IThreadLine thread = pinRef.getPin().getThread();
                    if (thread != null) thread.strip(); // TODO: 確実に切れない
                    else pinRef.getPin().stripThread(); // 保険
                });

                unloadPinHolder(holder);
            }
        }

	}


	private PinCraftAPI() {  }
}
