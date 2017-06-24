package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

public class GuiManage extends GuiHabitatBase {

	// "DropDown"
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
		int startWidth = width / 20 + width / 60;
		int buttonHeight = height / 20;
		buttonList.add (agriculture = new GuiTexturedButton (10,startWidth + ((width / 6) * 2),(buttonHeight * 2) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.AGRICULTURE)));
		buttonList.add (mines = new GuiTexturedButton (11,startWidth + ((width / 6) * 2),(buttonHeight * 3) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.MINES)));
		buttonList.add (energyProduction = new GuiTexturedButton (12,startWidth + ((width / 6) * 2),(buttonHeight * 4) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.ENERGY_PRODUCTION)));
		buttonList.add (magicProduction = new GuiTexturedButton (13,startWidth + ((width / 6) * 2),(buttonHeight * 5) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.MAGIC_PRODUCTION)));
		buttonList.add (mobFarm = new GuiTexturedButton (14,startWidth + ((width / 6) * 2),(buttonHeight * 6) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.MOB_FARM)));
		buttonList.add (liquidFarm = new GuiTexturedButton (15,startWidth + ((width / 6) * 2),(buttonHeight * 7) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.LIQUID_FARM)));
		buttonList.add (nursery = new GuiTexturedButton (16,startWidth + ((width / 6) * 2),(buttonHeight * 8) + buttonHeight / 4,width / 6,buttonHeight,I18n.format (Local.NURSERY)));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
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
		}
	}
}
