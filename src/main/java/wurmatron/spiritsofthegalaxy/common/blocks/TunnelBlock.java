package wurmatron.spiritsofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wurmatron.spiritsofthegalaxy.common.tileentity.TileMutiBlock;
import wurmatron.spiritsofthegalaxy.common.utils.LogHandler;

import javax.annotation.Nullable;
import java.util.List;

public class TunnelBlock extends Block implements ITileEntityProvider {

	public TunnelBlock (Material material) {
		super (material);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World worldIn,int meta) {
		return new TileMutiBlock ();
	}

	@Override
	public void onNeighborChange (IBlockAccess world,BlockPos pos,BlockPos neighbor) {
		super.onNeighborChange (world,pos,neighbor);
		TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (pos);
		if (tile != null)
			tile.updateController ();
	}

	@Override
	public void breakBlock (World world,BlockPos pos,IBlockState state) {
		TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (pos);
		if (tile != null)
			tile.updateDestroyed ();
		super.breakBlock (world,pos,state);
	}
}
