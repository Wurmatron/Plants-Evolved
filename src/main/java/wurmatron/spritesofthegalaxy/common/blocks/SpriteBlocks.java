package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wurmatron.spritesofthegalaxy.common.reference.Registry;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileInput;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;
import wurmatron.spritesofthegalaxy.common.tileentity.TileOutput;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block habitatCasing;
	public static Block habitatOutput;
	public static Block habitatInput;

	public static void registerBlocks () {
		register (habitatController = new CoreBlock ().setUnlocalizedName ("core"));
		register (habitatCasing = new BlockMutiBlock ().setUnlocalizedName ("casing"));
		register (habitatOutput = new BlockOutput ().setUnlocalizedName ("output"));
		register (habitatInput = new BlockInput ().setUnlocalizedName ("input"));
	}

	public static void registerTiles () {
		GameRegistry.registerTileEntity (TileHabitatCore.class,"habitatCore");
		GameRegistry.registerTileEntity (TileMutiBlock.class,"mutiblock");
		GameRegistry.registerTileEntity (TileInput.class,"input");
		GameRegistry.registerTileEntity (TileOutput.class,"output");
	}

	private static void register (Block block) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5));
	}
}
