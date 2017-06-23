package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.client.proxy.ClientProxy;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileInput;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block habitatCasing;
	public static Block habitatOutput;
	public static Block habitatInput;

	public static void registerBlocks () {
		register (habitatController = new CoreBlock ().setRegistryName ("core").setUnlocalizedName ("core"));
		register (habitatCasing = new BlockMutiBlock ().setRegistryName ("casing").setUnlocalizedName ("casing"));
		register (habitatOutput = new BlockOutput ().setRegistryName ("output").setUnlocalizedName ("output"));
		register (habitatInput = new BlockInput ().setRegistryName ("input").setUnlocalizedName ("input"));
	}

	public static void registerTiles () {
		GameRegistry.registerTileEntity (TileHabitatCore.class,"habitatCore");
		GameRegistry.registerTileEntity (TileMutiBlock.class,"mutiblock");
		GameRegistry.registerTileEntity (TileInput.class, "input");
	}

	private static void register (Block block) {
		if (FMLCommonHandler.instance ().getEffectiveSide () == Side.CLIENT)
			ClientProxy.blocks.add (block);
		GameRegistry.registerWithItem (block);
	}
}
