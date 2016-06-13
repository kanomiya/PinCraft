package com.kanomiya.mcmod.pincraft.block;

import javax.annotation.Nullable;

import com.kanomiya.mcmod.pincraft.PinCraft;
import com.kanomiya.mcmod.pincraft.api.PinCraftAPI;
import com.kanomiya.mcmod.pincraft.tile.TileEntityPin;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPin extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockPin()
    {
        super(Material.IRON);

        setRegistryName(new ResourceLocation(PinCraftAPI.MODID, "blockPin"));
        setUnlocalizedName(getRegistryName().toString());

        setHardness(1f);
        setCreativeTab(PinCraft.tab);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    public static AxisAlignedBB[] PIN_AABB = new AxisAlignedBB[]
            {
                    new AxisAlignedBB(0.43d, 0.1d, 0.43d, 0.57d, 1d, 0.57d), // DOWN
                    new AxisAlignedBB(0.43d, 0d, 0.43d, 0.57d, 0.9d, 0.57d), // UP
                    new AxisAlignedBB(0.43d, 0.43d, 0.1d, 0.57d, 0.57d, 1d), // NORTH
                    new AxisAlignedBB(0.43d, 0.43d, 0d, 0.57d, 0.57d, 0.9d), // SOUTH
                    new AxisAlignedBB(0.1d, 0.43d, 0.43d, 1d, 0.57d, 0.57d), // WEST
                    new AxisAlignedBB(0d, 0.43d, 0.43d, 0.9d, 0.57d, 0.57d), // EAST

            };

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);

        if (facing.getIndex() < PIN_AABB.length)
        {
            return PIN_AABB[facing.getIndex()];
        }

        return PIN_AABB[0];
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, facing);
    }


    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();

        iblockstate = iblockstate.withProperty(FACING, EnumFacing.getFront(meta & 5));

        return iblockstate;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        EnumFacing facing = state.getValue(FACING);

        i = i | facing.getIndex();

        return i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityPin();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityPin tile;

        super.breakBlock(worldIn, pos, state);
    }

}
