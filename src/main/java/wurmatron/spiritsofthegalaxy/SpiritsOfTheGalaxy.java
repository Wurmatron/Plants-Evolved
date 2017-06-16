package wurmatron.spiritsofthegalaxy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import wurmatron.spiritsofthegalaxy.common.items.SpriteItems;
import wurmatron.spiritsofthegalaxy.common.config.ConfigHandler;
import wurmatron.spiritsofthegalaxy.common.blocks.SpriteBlocks;
import wurmatron.spiritsofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spiritsofthegalaxy.common.reference.Global;

@Mod (modid = Global.MODID, name = Global.NAME, version = Global.VERSION, guiFactory = Global.GUIFACTORY, dependencies = Global.DEPENDENCIES)
public class SpiritsOfTheGalaxy {

	@Mod.Instance (Global.MODID)
	public static SpiritsOfTheGalaxy instance;

	@SidedProxy (serverSide = Global.COMMON_PROXY, clientSide = Global.CLIENT_PROXY)
	public static CommonProxy proxy;

	public static CreativeTabs tabSpirits = new CreativeTabs ("tabSpirits") {
		@Override
		public ItemStack getTabIconItem () {
			return new ItemStack (Items.APPLE);
		}
	};

	@Mod.EventHandler
	public void onPreInit (FMLPreInitializationEvent e) {
		ConfigHandler.preInit (e);
		SpriteItems.registerItems ();
		SpriteBlocks.registerBlocks ();
		SpriteBlocks.registerTiles ();
		proxy.onSideOnly ();
	}

	@Mod.EventHandler
	public void onInit (FMLInitializationEvent e) {
	}

	@Mod.EventHandler
	public void onPostInit (FMLPostInitializationEvent e) {
	}

}
