package wurmatron.spritesofthegalaxy.common.network.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.relauncher.Side;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.common.network.CustomMessage;

import java.io.IOException;

public class OpenGuiMessage extends CustomMessage.CustomtServerMessage <OpenGuiMessage> {

	private int id;

	public OpenGuiMessage () {
	}

	public OpenGuiMessage (int id) {
		this.id = id;
	}

	@Override
	protected void read (PacketBuffer buff) throws IOException {
		id = buff.readInt ();
	}

	@Override
	protected void write (PacketBuffer buff) throws IOException {
		buff.writeInt (id);
	}

	@Override
	public void process (EntityPlayer player,Side side) {
		player.openGui (SpritesOfTheGalaxy.instance,id,player.world,(int) player.posX,(int) player.posY,(int) player.posZ);
	}
}
