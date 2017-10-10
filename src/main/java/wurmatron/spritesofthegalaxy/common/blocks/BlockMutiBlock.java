package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.common.items.SpriteItems;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import javax.annotation.Nullable;

public class BlockMutiBlock extends Block implements ITileEntityProvider {

	public BlockMutiBlock () {
		super (Material.IRON);
		setCreativeTab (SpritesOfTheGalaxy.tabSprites);
		setHardness (5);
		setResistance (10);
		setHarvestLevel ("pickaxe",3);
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
		if (tile != null) {
			tile.checkIfValid ();
		}
	}

	@Override
	public void breakBlock (World world,BlockPos pos,IBlockState state) {
		TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (pos);
		if (tile != null)
			tile.checkIfValid ();
		super.breakBlock (world,pos,state);
	}

	@Override
	public boolean onBlockActivated (World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		TileMutiBlock tile = (TileMutiBlock) world.getTileEntity (pos);
		boolean added = false;
		if (tile != null && tile.getCore () != null) {
			if (world.getTileEntity (tile.getCore ()) != null && world.getTileEntity (tile.getCore ()) instanceof TileHabitatCore2) {
				TileHabitatCore2 core = (TileHabitatCore2) world.getTileEntity (tile.getCore ());
				if (player.getHeldItemMainhand () != ItemStack.EMPTY && player.getHeldItemMainhand ().getItem () == SpriteItems.spriteColony) {
					core.setColony (player.getHeldItemMainhand ());
					player.inventory.deleteStack (player.getHeldItemMainhand ());
					added = true;
				} else if (player.getHeldItemMainhand () == ItemStack.EMPTY && core != null && player.isSneaking () && core.getColony () != ItemStack.EMPTY) {
					player.inventory.addItemStackToInventory (core.getColony ());
					core.setColony (ItemStack.EMPTY);
				}
			}
			if (!player.isSneaking () && !added) {
				BlockPos loc = tile.getCore () != null ? tile.getCore () : pos;
				player.openGui (SpritesOfTheGalaxy.instance,GuiHandler.OVERVIEW,world,loc.getX (),loc.getY (),loc.getZ ());
			}
			return true;
		}
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType (IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
