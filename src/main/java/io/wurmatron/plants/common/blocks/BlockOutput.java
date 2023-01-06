package io.wurmatron.plants.common.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import io.wurmatron.plants.common.tileentity.TileOutput;

import javax.annotation.Nullable;

public class BlockOutput extends BlockMutiBlock {

	public BlockOutput () {
		super ();
		setUnlocalizedName ("output");
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
		return new TileOutput ();
	}
}
