package wurmatron.spiritsofthegalaxy.common.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import wurmatron.spiritsofthegalaxy.common.tileentity.TileHabitatController;

import javax.annotation.Nullable;

public class ControllerBlock extends BlockContainer {

	public ControllerBlock (Material material) {
		super (material);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World worldIn,int meta) {
		return new TileHabitatController ();
	}

	@Override
	public EnumBlockRenderType getRenderType (IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
