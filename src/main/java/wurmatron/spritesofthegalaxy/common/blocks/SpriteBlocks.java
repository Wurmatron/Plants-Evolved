package wurmatron.spritesofthegalaxy.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.Registry;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.tileentity.TileInput;
import wurmatron.spritesofthegalaxy.common.tileentity.TileMutiBlock;
import wurmatron.spritesofthegalaxy.common.tileentity.TileOutput;

import java.util.ArrayList;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block habitatCasing;
	public static Block habitatOutput;
	public static Block habitatInput;

	public static void registerBlocks () {
		ArrayList <String> habitatCore = new ArrayList <> ();
		habitatCore.add (I18n.translateToLocal (Local.MUTIBLOCK_INFO2));
		register (habitatController = new CoreBlock (),habitatCore);
		register (habitatCasing = new BlockCasing ());
		register (habitatOutput = new BlockOutput ());
		register (habitatInput = new BlockInput ());
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

	private static void register (Block block,ArrayList <String> tooltip) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),tooltip);
	}
}
