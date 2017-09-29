package wurmatron.spritesofthegalaxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.common.blocks.SpriteBlocks;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.items.SpriteItems;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Registry;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.structure.StructureHelper;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY, dependencies = Global.DEPENDENCIES)
public class SpritesOfTheGalaxy {

	@Mod.Instance (Global.MODID)
	public static SpritesOfTheGalaxy instance;

	@SidedProxy (serverSide = Global.COMMON_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static CreativeTabs tabSprites = new CreativeTabs ("tabSprites") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (Items.APPLE);
		}
	};

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.preInit (e);
		MinecraftForge.EVENT_BUS.register (new Registry());
		SpriteBlocks.registerBlocks ();
		SpriteItems.registerItems ();
		SpriteBlocks.registerTiles ();
		proxy.onSideOnly ();
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler (SpritesOfTheGalaxy.instance,new GuiHandler ());
		NetworkHandler.registerPackets ();
	}

	@Mod.EventHandler
	public void onPostInit (FMLPostInitializationEvent e) {
		ResearchHelper.registerResearch ();
		StructureHelper.registerStructures ();
	}

}
