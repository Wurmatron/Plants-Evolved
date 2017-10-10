package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.common.tileentity.TileOutput;

import javax.annotation.Nullable;

public class BlockOutput extends BlockMutiBlock {

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
		return new TileOutput ();
	}
}
