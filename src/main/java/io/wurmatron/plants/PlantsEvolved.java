package io.wurmatron.plants;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import io.wurmatron.plants.client.GuiHandler;
import io.wurmatron.plants.common.blocks.SpriteBlocks;
import io.wurmatron.plants.common.config.ConfigHandler;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.items.SpriteItems;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.proxy.CommonProxy;
import io.wurmatron.plants.common.reference.Global;
import io.wurmatron.plants.common.reference.Registry;
import io.wurmatron.plants.common.research.ResearchHelper;
import io.wurmatron.plants.common.structure.StructureHelper;
import io.wurmatron.plants.common.utils.output.OutputJson;
import io.wurmatron.plants.common.utils.JsonLoader;
import io.wurmatron.plants.common.utils.StackHelper;

import java.util.HashMap;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY, dependencies = Global.DEPENDENCIES)
public class PlantsEvolved {

	@Mod.Instance (Global.MODID)
	public static PlantsEvolved instance;

	@SidedProxy (serverSide = Global.COMMON_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static CreativeTabs tabSprites = new CreativeTabs ("tabSprites") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (SpriteItems.spriteColony);
		}
	};

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		StructureHelper.registerStructures ();
		ConfigHandler.preInit (e);
		MinecraftForge.EVENT_BUS.register (new Registry ());
		SpriteBlocks.registerBlocks ();
		SpriteItems.registerItems ();
		SpriteBlocks.registerTiles ();
		proxy.onSideOnly ();
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler (PlantsEvolved.instance,new GuiHandler ());
		NetworkHandler.registerPackets ();
		if (Settings.createDefaultOutput) {
			JsonLoader.save(new OutputJson ("ironIngot",100,StackHelper.convert (new ItemStack (Items.IRON_INGOT,1,0),false)));
			JsonLoader.save(new OutputJson ("goldIngot",1000,StackHelper.convert (new ItemStack (Items.GOLD_INGOT,1,0),false)));
			JsonLoader.save(new OutputJson ("coal",10,StackHelper.convert (new ItemStack (Items.COAL,1,0),false)));
			JsonLoader.save(new OutputJson ("lapis",20,StackHelper.convert (new ItemStack (Items.DYE,1,4),false)));
			JsonLoader.save(new OutputJson ("redstone",5,StackHelper.convert (new ItemStack (Items.REDSTONE,1,0),false)));
			JsonLoader.save(new OutputJson ("diamond",5000,StackHelper.convert (new ItemStack (Items.DIAMOND,1,0),false)));
			JsonLoader.save(new OutputJson ("emerald",50,StackHelper.convert (new ItemStack (Items.EMERALD,1,0),false)));
			JsonLoader.save(new OutputJson ("leather",50,StackHelper.convert (new ItemStack (Items.LEATHER,1,0),false)));
			JsonLoader.save(new OutputJson ("string",5000,StackHelper.convert (new ItemStack (Items.STRING,1,0),false)));
			JsonLoader.save(new OutputJson ("blaze",5000,StackHelper.convert (new ItemStack (Items.BLAZE_ROD,1,0),false)));
			JsonLoader.save(new OutputJson ("wood",5000,StackHelper.convert (new ItemStack (Blocks.WOOL,1,0),false)));
			JsonLoader.save(new OutputJson ("glowstone",5000,StackHelper.convert (new ItemStack (Items.GLOWSTONE_DUST,1,0),false)));
			HashMap <String, Integer> zombieStructure = new HashMap <> ();
			zombieStructure.put (StructureHelper.zombieStructure.getName (),1);
			JsonLoader.save(new OutputJson ("witherHead",5000,StackHelper.convert (new ItemStack (Items.SKULL,1,1),false),zombieStructure));
		}
	}

	@Mod.EventHandler
	public void onPostInit (FMLPostInitializationEvent e) {
		ResearchHelper.registerResearch ();
	}

	@Mod.EventHandler
	public void onServerStarting (FMLServerStartingEvent e) {
		JsonLoader.loadJsonOutputs ();
	}

}
