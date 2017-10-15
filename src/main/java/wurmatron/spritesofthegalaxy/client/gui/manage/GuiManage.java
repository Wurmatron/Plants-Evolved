package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.io.IOException;

public class GuiManage extends GuiHabitatBase {

	protected GuiButton agriculture;
	protected GuiButton mines;
	protected GuiButton energyProduction;
	protected GuiButton magicProduction;
	protected GuiButton mobFarm;
	protected GuiButton liquidFarm;
	protected GuiButton nursery;
	protected GuiButton research;
	protected GuiButton storage;

	public GuiManage (TileHabitatCore2 tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (agriculture = new GuiButton (10,startWidth + 61,startHeight + 21,60,15,I18n.translateToLocal (Local.AGRICULTURE)));
		buttonList.add (mines = new GuiButton (11,startWidth + 61,startHeight + 37,60,15,I18n.translateToLocal (Local.MINES)));
		buttonList.add (energyProduction = new GuiButton (12,startWidth + 61,startHeight + 53,60,15,I18n.translateToLocal (Local.ENERGY_PRODUCTION)));
		buttonList.add (magicProduction = new GuiButton (13,startWidth + 61,startHeight + 69,60,15,I18n.translateToLocal (Local.MAGIC_PRODUCTION)));
		buttonList.add (mobFarm = new GuiButton (14,startWidth + 61,startHeight + 85,60,15,I18n.translateToLocal (Local.MOB_FARM)));
		buttonList.add (liquidFarm = new GuiButton (15,startWidth + 61,startHeight + 101,60,15,I18n.translateToLocal (Local.LIQUID_FARM)));
		buttonList.add (nursery = new GuiButton (16,startWidth + 61,startHeight + 117,60,15,I18n.translateToLocal (Local.NURSERY)));
		buttonList.add (research = new GuiButton (17,startWidth + 61,startHeight + 133,60,15,I18n.translateToLocal (Local.RESEARCH)));
		buttonList.add (storage = new GuiButton (18,startWidth + 61,startHeight + 149,60,15,I18n.translateToLocal (Local.STORAGE)));
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		switch (butt.id) {
			case (10):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.AGRICULTURE,tile.getPos ()));
				break;
			case (11):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.MINES,tile.getPos ()));
				break;
			case (12):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.ENERGY_PRODUCTION,tile.getPos ()));
				break;
			case (13):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.MAGIC_PRODUCTION,tile.getPos ()));
				break;
			case (14):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.MOB_FARM,tile.getPos ()));
				break;
			case (15):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.LIQUID_FARM,tile.getPos ()));
				break;
			case (16):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.NURSERY,tile.getPos ()));
				break;
			case (17):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH_MANAGE,tile.getPos ()));
				break;
			case (18):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.STORAGE,tile.getPos ()));
				break;
		}
	}
}
