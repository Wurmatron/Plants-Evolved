package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockCasing extends BlockMutiBlock {

	private static final PropertyBool CONNECTED_DOWN = PropertyBool.create ("connected_down");
	private static final PropertyBool CONNECTED_UP = PropertyBool.create ("connected_up");
	private static final PropertyBool CONNECTED_NORTH = PropertyBool.create ("connected_north");
	private static final PropertyBool CONNECTED_SOUTH = PropertyBool.create ("connected_south");
	private static final PropertyBool CONNECTED_WEST = PropertyBool.create ("connected_west");
	private static final PropertyBool CONNECTED_EAST = PropertyBool.create ("connected_east");

	public BlockCasing () {
		super ();
		setDefaultState (this.blockState.getBaseState ().withProperty (CONNECTED_DOWN,Boolean.FALSE).withProperty (CONNECTED_EAST,Boolean.FALSE).withProperty (CONNECTED_NORTH,Boolean.FALSE).withProperty (CONNECTED_SOUTH,Boolean.FALSE).withProperty (CONNECTED_UP,Boolean.FALSE).withProperty (CONNECTED_WEST,Boolean.FALSE));
		setUnlocalizedName ("casing");
	}

	@Override
	public IBlockState getActualState (IBlockState state,IBlockAccess world,BlockPos position) {
		return state.withProperty (CONNECTED_DOWN,this.isSideConnectable (world,position,EnumFacing.DOWN)).withProperty (CONNECTED_EAST,this.isSideConnectable (world,position,EnumFacing.EAST)).withProperty (CONNECTED_NORTH,this.isSideConnectable (world,position,EnumFacing.NORTH)).withProperty (CONNECTED_SOUTH,this.isSideConnectable (world,position,EnumFacing.SOUTH)).withProperty (CONNECTED_UP,this.isSideConnectable (world,position,EnumFacing.UP)).withProperty (CONNECTED_WEST,this.isSideConnectable (world,position,EnumFacing.WEST));
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,CONNECTED_DOWN,CONNECTED_UP,CONNECTED_NORTH,CONNECTED_SOUTH,CONNECTED_WEST,CONNECTED_EAST);
	}


	@Override
	public int getMetaFromState (IBlockState state) {
		return 0;
	}

	private boolean isSideConnectable (IBlockAccess world,BlockPos pos,EnumFacing side) {
		return world.getBlockState (pos.offset (side)).getBlock () != null && world.getBlockState (pos.offset (side)).getBlock () instanceof BlockMutiBlock;
	}
}
