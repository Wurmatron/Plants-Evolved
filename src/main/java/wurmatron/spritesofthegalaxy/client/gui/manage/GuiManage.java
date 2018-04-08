package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class GuiManage extends GuiHabitatBase {

	protected GuiTexturedButton agriculture;
	protected GuiTexturedButton mines;
	protected GuiTexturedButton energyProduction;
	protected GuiTexturedButton magicProduction;
	protected GuiTexturedButton mobFarm;
	protected GuiTexturedButton liquidFarm;
	protected GuiTexturedButton husbandry;
	protected GuiTexturedButton research;
	protected GuiTexturedButton storage;

	public GuiManage (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + 61,startHeight + 21,60,15,1,I18n.translateToLocal (Local.AGRICULTURE)));
		buttonList.add (mines = new GuiTexturedButton (11,startWidth + 61,startHeight + 37,60,15,1,I18n.translateToLocal (Local.MINES)));
		buttonList.add (energyProduction = new GuiTexturedButton (12,startWidth + 61,startHeight + 53,60,15,1,I18n.translateToLocal (Local.ENERGY_PRODUCTION)));
		buttonList.add (magicProduction = new GuiTexturedButton (13,startWidth + 61,startHeight + 69,60,15,1,I18n.translateToLocal (Local.MAGIC_PRODUCTION)));
		buttonList.add (mobFarm = new GuiTexturedButton (14,startWidth + 61,startHeight + 85,60,15,1,I18n.translateToLocal (Local.MOB_FARM)));
		buttonList.add (liquidFarm = new GuiTexturedButton (15,startWidth + 61,startHeight + 101,60,15,1,I18n.translateToLocal (Local.LIQUID_FARM)));
		buttonList.add (husbandry = new GuiTexturedButton (16,startWidth + 61,startHeight + 117,60,15,1,I18n.translateToLocal (Local.HUSBANDRY)));
		buttonList.add (research = new GuiTexturedButton (17,startWidth + 61,startHeight + 133,60,15,1,I18n.translateToLocal (Local.RESEARCH)));
		buttonList.add (storage = new GuiTexturedButton (18,startWidth + 61,startHeight + 149,60,15,1,I18n.translateToLocal (Local.STORAGE)));
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
