package wurmatron.spritesofthegalaxy.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.items.ModuleItemBlock;
import wurmatron.spritesofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spritesofthegalaxy.common.reference.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientProxy extends CommonProxy {

	public static List <Block> blocks = new ArrayList <> ();
	public static List <Item> items = new ArrayList <> ();

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		for (Block block : blocks)
			createModel (block);
		for (Item item : items)
			createModel (item);
	}

	private static void createModel (Block item) {
		ModelLoader.setCustomModelResourceLocation (Item.getItemFromBlock (item),0,new ModelResourceLocation (Global.MODID + ":" + item.getUnlocalizedName ().substring (5),"inventory"));
	}

	private static void createModel (Item item) {
		ModelLoader.setCustomModelResourceLocation (item,0,new ModelResourceLocation (Global.MODID + ":" + item.getUnlocalizedName ().substring (5),"inventory"));
	}

	@Override
	public EntityPlayer getPlayer (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft ().player : super.getPlayer (ctx));
	}

	@Override
	public IThreadListener getThreadFromCTX (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft () : super.getThreadFromCTX (ctx));
	}
}
