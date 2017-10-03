package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.LogHandler;

import java.awt.*;
import java.io.IOException;

public class GuiHabitatBase extends GuiScreen {

	protected int startWidth;
	protected int startHeight;

	// Top Bar
	protected GuiButton overview;
	protected GuiButton population;
	protected GuiButton manage;
	protected GuiButton storage;
	protected GuiButton research;

	protected TileHabitatCore tile;

	public GuiHabitatBase (TileHabitatCore tile) {
		this.tile = tile;
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		GlStateManager.pushMatrix ();
		mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/colonyBackground.png"));
		drawTexturedModalRect (startWidth,startHeight,0,0,256,256);
		GlStateManager.popMatrix ();
		super.drawScreen (mouseX,mouseY,partialTicks);
	}

	@Override
	public void initGui () {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		buttonList.add (overview = new GuiTexturedButton (0,startWidth + 2,startHeight + 5,63,15,"Overview"));
		buttonList.add (population = new GuiTexturedButton (1,startWidth + 57,startHeight + 5,46,15,"Pop."));
		buttonList.add (manage = new GuiTexturedButton (2,startWidth + 95,startHeight + 5,54,15,"Manage"));
		buttonList.add (storage = new GuiTexturedButton (3,startWidth + 141,startHeight + 5,56,15,"Storage"));
		buttonList.add (storage = new GuiTexturedButton (4,startWidth + 190,startHeight + 5,62,15,"Research"));
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		switch (button.id) {
			case (0):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.OVERVIEW,tile.getPos ()));
				break;
			case (1):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.POPULATION,tile.getPos ()));
				break;
			case (2):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.MANAGE,tile.getPos ()));
				break;
			case (3):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.STORAGE,tile.getPos ()));
				break;
			case (4):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH,tile.getPos ()));
				break;
		}
	}
}
