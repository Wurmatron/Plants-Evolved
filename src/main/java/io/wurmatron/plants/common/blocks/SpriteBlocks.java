package io.wurmatron.plants.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.registry.GameRegistry;
import io.wurmatron.plants.api.mutiblock.SpecialType;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.reference.Registry;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.tileentity.TileInput;
import io.wurmatron.plants.common.tileentity.TileMutiBlock;
import io.wurmatron.plants.common.tileentity.TileOutput;

import java.util.ArrayList;

public class SpriteBlocks {

	public static Block habitatController;
	public static Block habitatCasing;
	public static Block habitatOutput;
	public static Block habitatInput;
	public static Block habitatSpecial;

	public static void registerBlocks () {
		ArrayList <String> habitatCore = new ArrayList <> ();
		habitatCore.add (I18n.translateToLocal (Local.MUTIBLOCK_INFO2));
		register (habitatController = new CoreBlock (),habitatCore);
		register (habitatCasing = new BlockCasing ());
		register (habitatOutput = new BlockOutput ());
		register (habitatInput = new BlockInput ());
		register (habitatSpecial = new BlockSpecial (),SpecialType.ACCELERATOR);
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

	private static void register (Block block,SpecialType type) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),type);
	}

	private static void register (Block block,ArrayList <String> tooltip) {
		Registry.registerBlock (block,block.getUnlocalizedName ().substring (5),tooltip);
	}
}
