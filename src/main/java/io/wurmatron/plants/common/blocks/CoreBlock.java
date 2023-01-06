package io.wurmatron.plants.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import io.wurmatron.plants.PlantsEvolved;
import io.wurmatron.plants.client.GuiHandler;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;

import javax.annotation.Nullable;

public class CoreBlock extends BlockMutiBlock {

	public CoreBlock () {
		super ();
		setUnlocalizedName ("habitat");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World worldIn,int meta) {
		return new TileHabitatCore ();
	}

	@Override
	public boolean onBlockActivated (World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		if (!world.isRemote && world.getTileEntity (pos) != null && world.getTileEntity (pos) instanceof TileHabitatCore) {
			TileHabitatCore tile = (TileHabitatCore) world.getTileEntity (pos);
			if (tile.mutiBlockSize > 0)
				player.openGui (PlantsEvolved.instance,GuiHandler.OVERVIEW,world,pos.getX (),pos.getY (),pos.getZ ());
			return true;
		} else if (world.isRemote)
			player.sendMessage (new TextComponentString (I18n.translateToLocal (Local.MUTIBLOCK_INFO1)));
		return false;
	}
}
