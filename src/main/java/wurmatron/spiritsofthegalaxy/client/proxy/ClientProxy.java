package wurmatron.spiritsofthegalaxy.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import wurmatron.spiritsofthegalaxy.common.config.ConfigHandler;
import wurmatron.spiritsofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spiritsofthegalaxy.common.reference.Global;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {

	public static List<Block> blocks = new ArrayList<> ();

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		for (Block block : blocks)
			createModel (block);
	}

	public static void createModel (Block item) {
		ModelLoader.setCustomModelResourceLocation (Item.getItemFromBlock (item),0,new ModelResourceLocation (Global.MODID + ":" + item.getUnlocalizedName ().substring (5),"inventory"));
	}
}
