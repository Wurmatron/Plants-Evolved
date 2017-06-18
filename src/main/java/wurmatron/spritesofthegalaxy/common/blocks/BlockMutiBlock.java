package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

import javax.annotation.Nullable;

public class BlockMutiBlock extends Block implements ITileEntityProvider {

	public BlockMutiBlock () {
		super (Material.IRON);
		setCreativeTab (SpritesOfTheGalaxy.tabSprites);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
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
