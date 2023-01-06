package io.wurmatron.plants.common.blocks;


import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import io.wurmatron.plants.PlantsEvolved;
import io.wurmatron.plants.api.mutiblock.SpecialType;

public class BlockSpecial extends BlockMutiBlock {

	public static final PropertyEnum TYPE = PropertyEnum.create ("type",SpecialType.class);

	public BlockSpecial () {
		setUnlocalizedName ("special");
		setDefaultState (blockState.getBaseState ().withProperty (TYPE,SpecialType.ACCELERATOR));
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		SpecialType type = (SpecialType) state.getValue (TYPE);
		return type.getID ();
	}

	@Override
	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState ().withProperty (TYPE,SpecialType.values ()[meta]);
	}

	@Override
	public int damageDropped (IBlockState state) {
		return getMetaFromState (state);
	}

	@Override
	public void getSubBlocks (CreativeTabs tab,NonNullList <ItemStack> items) {
		if (tab == PlantsEvolved.tabSprites)
			for (int meta = 0; meta < SpecialType.values ().length; meta++)
				items.add (new ItemStack (this,1,meta));
	}

	@Override
	public ItemStack getPickBlock (IBlockState state,RayTraceResult target,World world,BlockPos pos,EntityPlayer player) {
		return new ItemStack (Item.getItemFromBlock (this),1,this.getMetaFromState (world.getBlockState (pos)));
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,TYPE);
	}
}
