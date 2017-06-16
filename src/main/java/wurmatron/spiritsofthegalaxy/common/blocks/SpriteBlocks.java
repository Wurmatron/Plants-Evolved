package wurmatron.spiritsofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spiritsofthegalaxy.SpiritsOfTheGalaxy;
import wurmatron.spiritsofthegalaxy.client.proxy.ClientProxy;
import wurmatron.spiritsofthegalaxy.common.tileentity.TileHabitatController;
import wurmatron.spiritsofthegalaxy.common.tileentity.TileMutiBlock;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block tunnel;

	public static void registerBlocks () {
		register (habitatController = new ControllerBlock (Material.IRON).setCreativeTab (SpiritsOfTheGalaxy.tabSpirits).setRegistryName ("controller").setUnlocalizedName ("controller"));
		register (tunnel = new TunnelBlock (Material.IRON).setCreativeTab (SpiritsOfTheGalaxy.tabSpirits).setRegistryName ("tunnel").setUnlocalizedName ("tunnel"));
	}

	public static void registerTiles () {
		GameRegistry.registerTileEntity (TileHabitatController.class,"habitatController");
		GameRegistry.registerTileEntity (TileMutiBlock.class, "tunnel");
	}

	private static void register (Block block) {
		if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT)
			ClientProxy.blocks.add (block);
		GameRegistry.registerWithItem (block);
	}

}
