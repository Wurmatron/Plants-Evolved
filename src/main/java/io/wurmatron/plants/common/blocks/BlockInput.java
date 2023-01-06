package io.wurmatron.plants.common.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.wurmatron.plants.common.tileentity.TileInput;

import javax.annotation.Nullable;

public class BlockInput extends
		io.wurmatron.plants.common.blocks.BlockMutiBlock {

	public BlockInput () {
		super ();
		setUnlocalizedName ("input");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
		return new TileInput ();
	}
}
