package io.wurmatron.plants.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import io.wurmatron.plants.common.config.ConfigHandler;
import io.wurmatron.plants.common.items.ItemResource;
import io.wurmatron.plants.common.items.SpriteItems;
import io.wurmatron.plants.common.proxy.CommonProxy;
import io.wurmatron.plants.common.reference.Global;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
		MinecraftForge.EVENT_BUS.register (this);
	}

	@Override
	public EntityPlayer getPlayer (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft ().player : super.getPlayer (ctx));
	}

	@Override
	public IThreadListener getThreadFromCTX (MessageContext ctx) {
		return (ctx.side.isClient () ? Minecraft.getMinecraft () : super.getThreadFromCTX (ctx));
	}

	@SubscribeEvent
	public void loadModel (ModelRegistryEvent e) {
		for (int index = 0; index < ItemResource.minerals.length; index++)
			createModel (SpriteItems.mineral,index,ItemResource.minerals[index]);
	}

	private static void createModel (Item item,int meta,String name) {
		ModelLoader.setCustomModelResourceLocation (item,meta,new ModelResourceLocation (Global.MODID + ":" + name,"inventory"));
	}
}
