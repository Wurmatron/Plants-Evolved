package wurmatron.spritesofthegalaxy;

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
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.common.blocks.SpriteBlocks;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.config.Settings;
import wurmatron.spritesofthegalaxy.common.items.SpriteItems;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Registry;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.structure.StructureHelper;
import wurmatron.spritesofthegalaxy.common.utils.output.OutputJson;
import wurmatron.spritesofthegalaxy.common.utils.JsonLoader;
import wurmatron.spritesofthegalaxy.common.utils.StackHelper;

import java.util.HashMap;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY, dependencies = Global.DEPENDENCIES)
public class SpritesOfTheGalaxy {

	@Mod.Instance (Global.MODID)
	public static SpritesOfTheGalaxy instance;

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
		NetworkRegistry.INSTANCE.registerGuiHandler (SpritesOfTheGalaxy.instance,new GuiHandler ());
		NetworkHandler.registerPackets ();
		if (Settings.createDefaultOutput) {
			JsonLoader.saveOutput (new OutputJson ("ironIngot",100,StackHelper.convert (new ItemStack (Items.IRON_INGOT,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("goldIngot",1000,StackHelper.convert (new ItemStack (Items.GOLD_INGOT,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("coal",10,StackHelper.convert (new ItemStack (Items.COAL,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("lapis",20,StackHelper.convert (new ItemStack (Items.DYE,1,4),false)));
			JsonLoader.saveOutput (new OutputJson ("redstone",5,StackHelper.convert (new ItemStack (Items.REDSTONE,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("diamond",5000,StackHelper.convert (new ItemStack (Items.DIAMOND,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("emerald",50,StackHelper.convert (new ItemStack (Items.EMERALD,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("leather",50,StackHelper.convert (new ItemStack (Items.LEATHER,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("string",5000,StackHelper.convert (new ItemStack (Items.STRING,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("blaze",5000,StackHelper.convert (new ItemStack (Items.BLAZE_ROD,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("wood",5000,StackHelper.convert (new ItemStack (Blocks.WOOL,1,0),false)));
			JsonLoader.saveOutput (new OutputJson ("glowstone",5000,StackHelper.convert (new ItemStack (Items.GLOWSTONE_DUST,1,0),false)));
			HashMap <String, Integer> zombieStructure = new HashMap <> ();
			zombieStructure.put (StructureHelper.zombieStructure.getName (),1);
			JsonLoader.saveOutput (new OutputJson ("witherHead",5000,StackHelper.convert (new ItemStack (Items.SKULL,1,1),false),zombieStructure));
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
