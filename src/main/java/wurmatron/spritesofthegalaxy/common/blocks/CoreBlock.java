package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

import javax.annotation.Nullable;

public class CoreBlock extends BlockMutiBlock {

	public CoreBlock () {
		super ();
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World worldIn,int meta) {
		return new TileHabitatCore ();
	}

	@Override
	public EnumBlockRenderType getRenderType (IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated (World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		if (!world.isRemote) {
			player.openGui (SpritesOfTheGalaxy.instance,GuiHandler.OVERVIEW,world,pos.getX (),pos.getY (),pos.getZ ());
			return true;
		}
		return false;
	}
}