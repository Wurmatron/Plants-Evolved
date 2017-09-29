package wurmatron.spritesofthegalaxy.common.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class CommonProxy {

	public void onSideOnly () {
	}

	public EntityPlayer getPlayer (MessageContext ctx) {
		return ctx.getServerHandler ().player;
	}

	public IThreadListener getThreadFromCTX (MessageContext ctx) {
		return ctx.getServerHandler ().player.getServer ();
	}
}
