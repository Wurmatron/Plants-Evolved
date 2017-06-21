package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Global;

import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.io.IOException;

// TODO Gui Needs a way to be updated
public class GuiOverview extends GuiHabitatBase {

	// "DropDown"
	protected GuiButton income;
	protected GuiButton stats;
	protected GuiButton info;

	public GuiOverview (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		Minecraft.getMinecraft ().renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/overview.png"));
		Gui.drawModalRectWithCustomSizedTexture (width / 20,height / 20,0,0,width - (width / 20 * 2),height - (height / 20 * 2),width - (width / 20 * 2),height - (height / 20 * 2));
		super.drawScreen (mouseX,mouseY,partialTicks);
		//		if (tile != null) {
		//			drawCenteredString (Minecraft.getMinecraft ().fontRendererObj,I18n.format (Local.POPULATION) + ": " + tile.getPopulation () + " / " + tile.getMaxPopulation (),width / 6 * 4 - width / 20,height / 20 * 2 + height / 60,Color.WHITE.getRGB ());
		//			drawCenteredString (Minecraft.getMinecraft ().fontRendererObj,I18n.format (Local.FOOD) + ": " + tile.getFood (),(width / 6 * 4 - width / 20) - fontRendererObj.FONT_HEIGHT * 3 + fontRendererObj.FONT_HEIGHT / 2,height / 20 * 2 + height / 60 + (Minecraft.getMinecraft ().fontRendererObj.FONT_HEIGHT * 2),Color.WHITE.getRGB ());
		//		}
	}

	@Override
	public void initGui () {
		super.initGui ();
		int startWidth = width / 20 + width / 60;
		int buttonHeight = height / 20;
		buttonList.add (income = new GuiTexturedButton (10,startWidth,(buttonHeight * 2) + buttonHeight / 4,width / 6,buttonHeight,"Income"));
		buttonList.add (stats = new GuiTexturedButton (11,startWidth,(buttonHeight * 3) + buttonHeight / 4,width / 6,buttonHeight,"Stats"));
		buttonList.add (info = new GuiTexturedButton (12,startWidth,buttonHeight * 4 + buttonHeight / 4,width / 6,buttonHeight,"Info"));
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (10):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.INCOME));
				break;
			case (11):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.STATS));
				break;
			case (12):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.INFO));
				break;
		}
	}
}
