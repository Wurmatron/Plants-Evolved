package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.client.GuiBackupFailed;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class GuiManage extends GuiHabitatBase {

	protected GuiButton agriculture;
	protected GuiButton mines;
	protected GuiButton energyProduction;
	protected GuiButton magicProduction;
	protected GuiButton mobFarm;
	protected GuiButton liquidFarm;
	protected GuiButton nursery;

	public GuiManage (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + 95,startHeight + 21,60,15,I18n.translateToLocal (Local.AGRICULTURE)));
		buttonList.add (mines = new GuiTexturedButton (11,startWidth + 95,startHeight + 37,60,15,I18n.translateToLocal (Local.MINES)));
		buttonList.add (energyProduction = new GuiTexturedButton (12,startWidth + 95,startHeight + 53,60,15,I18n.translateToLocal (Local.ENERGY_PRODUCTION)));
		buttonList.add (magicProduction = new GuiTexturedButton (13,startWidth + 95,startHeight + 69,60,15,I18n.translateToLocal (Local.MAGIC_PRODUCTION)));
		buttonList.add (mobFarm = new GuiTexturedButton (14,startWidth + 95,startHeight + 85,60,15,I18n.translateToLocal (Local.MOB_FARM)));
		buttonList.add (liquidFarm = new GuiTexturedButton (15,startWidth + 95,startHeight + 101,60,15,I18n.translateToLocal (Local.LIQUID_FARM)));
		buttonList.add (nursery = new GuiTexturedButton (16,startWidth + 95,startHeight + 117,60,15,I18n.translateToLocal (Local.NURSERY)));
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		switch (butt.id) {
			case (10):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.AGRICULTURE,tile.getPos ()));
				break;
		}
	}
}
