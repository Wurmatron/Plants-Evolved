package wurmatron.spritesofthegalaxy.client.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import wurmatron.spritesofthegalaxy.common.config.ConfigHandler;
import wurmatron.spritesofthegalaxy.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
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
