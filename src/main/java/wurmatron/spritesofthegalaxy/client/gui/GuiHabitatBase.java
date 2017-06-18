package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import wurmatron.spritesofthegalaxy.SpritesOfTheGalaxy;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatController;

import java.io.IOException;

// TODO Gui Needs a way to be updated
public class GuiHabitatBase extends GuiScreen {

	// Top Bar
	protected GuiButton overview;
	protected GuiButton population;
	protected GuiButton resourceProduction;
	protected GuiButton storage;
	protected GuiButton research;

	protected TileHabitatController tile;

	public GuiHabitatBase (TileHabitatController tile) {
		this.tile = tile;
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		drawRect (width / 20,height / 20,width - width / 20,height - width / 20,0xB33d3d3d);
		super.drawScreen (mouseX,mouseY,partialTicks);
	}

	@Override
	public void initGui () {
		int startWidth = width / 20 + width / 60;
		int buttonHeight = height / 20;
		buttonList.add (overview = new GuiTexturedButton (0,startWidth,buttonHeight,width / 6,buttonHeight,"Overview"));
		buttonList.add (population = new GuiTexturedButton (1,startWidth + width / 6,buttonHeight,width / 6,buttonHeight,"Population"));
		buttonList.add (resourceProduction = new GuiTexturedButton (2,startWidth + ((width / 6) * 2),buttonHeight,width / 6,buttonHeight,"Manage"));
		buttonList.add (storage = new GuiTexturedButton (3,startWidth + ((width / 6) * 3),buttonHeight,width / 6,buttonHeight,"Storage"));
		buttonList.add (research = new GuiTexturedButton (4,startWidth + ((width / 6) * 4),buttonHeight,width / 6,buttonHeight,"Research"));
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		switch (button.id) {
			case (0):
				Minecraft.getMinecraft ().player.openGui (SpritesOfTheGalaxy.instance,GuiHandler.OVERVIEW,Minecraft.getMinecraft ().player.world,(int) Minecraft.getMinecraft ().player.posX,(int) Minecraft.getMinecraft ().player.posY,(int) Minecraft.getMinecraft ().player.posZ);
		}
	}
}
