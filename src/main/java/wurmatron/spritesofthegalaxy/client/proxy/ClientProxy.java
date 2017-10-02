package wurmatron.spritesofthegalaxy.client.proxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.proxy.CommonProxy;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Registry;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		for (Block block : Registry.blocks)
			createModel (block);
		for (Item item : Registry.items)
			createModel (item);
		for (Item item : Registry.blockItems.values ())
			createModel (item);
	}

	private static void createModel (Block item) {
		ModelLoader.setCustomModelResourceLocation (Registry.blockItems.get (item),0,new ModelResourceLocation (item.getRegistryName (),"inventory"));
	}

	private static void createModel (Item item) {
		ModelLoader.setCustomModelResourceLocation (item,0,new ModelResourceLocation (item.getRegistryName ().toString (),"inventory"));
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
