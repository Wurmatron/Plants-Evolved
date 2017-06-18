package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.api.mutiblock.IModule;
import wurmatron.spritesofthegalaxy.common.tileentity.TileModule;

import javax.annotation.Nullable;

public class BlockModule extends BlockMutiBlock {

	public static final PropertyEnum TIER = PropertyEnum.create ("tier",EnumTier.class);

	public IModule module;

	public BlockModule (IModule module) {
		super ();
		this.module = module;
		setDefaultState (blockState.getBaseState ().withProperty (TIER,EnumTier.TIER_1));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity (World world,int meta) {
		return new TileModule (module,1 + meta);
	}

	@Override
	protected BlockStateContainer createBlockState () {
		return new BlockStateContainer (this,new IProperty[] {TIER});
	}

	@Override
	public IBlockState getStateFromMeta (int meta) {
		return getDefaultState ().withProperty (TIER,getTierFromMeta (meta));
	}

	@Override
	public int getMetaFromState (IBlockState state) {
		EnumTier tier = (EnumTier) state.getValue (TIER);
		return tier.getMeta ();
	}

	@Override
	public int damageDropped (IBlockState state) {
		return getMetaFromState (state);
	}

	@Override
	public void getSubBlocks (Item item,CreativeTabs tab,NonNullList <ItemStack> list) {
		for (int meta = 0; meta < module.getMaxTier (); meta++)
			list.add (new ItemStack (item,1,meta));
	}

	@Override
	public ItemStack getPickBlock (IBlockState state,RayTraceResult target,World world,BlockPos pos,EntityPlayer player) {
		return new ItemStack (Item.getItemFromBlock (this),1,getMetaFromState (state));
	}

	private EnumTier getTierFromMeta (int meta) {
		for (EnumTier tier : EnumTier.values ())
			if (tier.getMeta () == meta)
				return tier;
		return EnumTier.TIER_1;
	}

	public enum EnumTier implements IStringSerializable {
		TIER_1 (0,"1"),TIER_2 (1,"2"),TIER_3 (2,"3"),TIER_4 (3,"4"),TIER_5 (4,"5"),TIER_6 (5,"6"),TIER_7 (6,"7"),TIER_8 (7,"8"),TIER_9 (8,"9"),TIER_10 (9,"10"),TIER_11 (10,"11"),TIER_12 (11,"12"),TIER_13 (12,"13"),TIER_14 (13,"14"),TIER_15 (14,"15"),TIER_16 (15,"16");

		private int meta;
		private String name;

		EnumTier (int meta,String name) {
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName () {
			return name;
		}

		@Override
		public String toString () {
			return getName ();
		}

		public int getMeta () {
			return meta;
		}
	}
}
