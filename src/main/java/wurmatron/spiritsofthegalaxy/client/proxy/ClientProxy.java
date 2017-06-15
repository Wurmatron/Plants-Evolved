package wurmatron.spiritsofthegalaxy.client.proxy;

import net.minecraftforge.common.MinecraftForge;
import wurmatron.spiritsofthegalaxy.common.config.ConfigHandler;
import wurmatron.spiritsofthegalaxy.common.proxy.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public void onSideOnly () {
		MinecraftForge.EVENT_BUS.register (new ConfigHandler ());
	}
}
