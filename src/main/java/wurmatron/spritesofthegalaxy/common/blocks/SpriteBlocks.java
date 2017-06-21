package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.client.proxy.ClientProxy;
import wurmatron.spritesofthegalaxy.common.items.ModuleItemBlock;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block habitatCasing;

	public static void registerBlocks () {
		register (habitatController = new CoreBlock ().setRegistryName ("core").setUnlocalizedName ("core"));
		register (habitatCasing = new BlockMutiBlock ().setRegistryName ("casing").setUnlocalizedName ("casing"));
	}

	public static void registerTiles () {
		GameRegistry.registerTileEntity (TileHabitatCore.class,"habitatCore");
		GameRegistry.registerTileEntity (TileMutiBlock.class,"mutiblock");
	}

	private static void register (Block block) {
		if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT)
			ClientProxy.blocks.add (block);
		GameRegistry.registerWithItem (block);
	}
}
