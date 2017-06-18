package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.client.proxy.ClientProxy;
import wurmatron.spritesofthegalaxy.common.items.ModuleItemBlock;
import wurmatron.spritesofthegalaxy.common.module.ModuleHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;
import wurmatron.spritesofthegalaxy.common.tileentity.TileModule;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block tunnel;
	public static Block population;
	public static Block garden;

	public static void registerBlocks () {
		register (habitatController = new ControllerBlock (Material.IRON).setCreativeTab (SpritesOfTheGalaxy.tabSprites).setRegistryName ("controller").setUnlocalizedName ("controller"));
		register (tunnel = new BlockMutiBlock ().setUnlocalizedName ("tunnel").setRegistryName ("tunnel"));
		register (population = new BlockModule (ModuleHelper.population).setUnlocalizedName ("population").setRegistryName ("population"));
		register (garden = new BlockModule (ModuleHelper.garden).setUnlocalizedName ("garden").setRegistryName ("garden"));
	}

	public static void registerTiles () {
		GameRegistry.registerTileEntity (TileHabitatController.class,"habitatController");
		GameRegistry.registerTileEntity (TileMutiBlock.class,"tunnel");
		GameRegistry.registerTileEntity (TileModule.class,"module");
	}

	private static void register (Block block) {
		if (block instanceof BlockModule) {
			BlockModule blockModule = (BlockModule) block;
			GameRegistry.register (blockModule);
			ModuleItemBlock itemBlock = new ModuleItemBlock (blockModule);
			itemBlock.setRegistryName (block.getRegistryName ());
			itemBlock.setUnlocalizedName (block.getUnlocalizedName ());
			GameRegistry.register (itemBlock);
			if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT)
				ClientProxy.moduleBlocks.put (blockModule,itemBlock);
		} else {
			if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT)
				ClientProxy.blocks.add (block);
			GameRegistry.registerWithItem (block);
		}
	}

}
