package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;

import java.io.IOException;

public class GuiHabitatBase extends GuiScreen {

	protected int startWidth;
	protected int startHeight;

	// Top Bar
	protected GuiButton overview;
	protected GuiButton manage;
	protected GuiButton production;
	protected GuiButton research;

	protected TileHabitatCore2 tile;

	public GuiHabitatBase (TileHabitatCore2 tile) {
		this.tile = tile;
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		GlStateManager.pushMatrix ();
		GlStateManager.enableBlend ();
		mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/colonyBackground.png"));
		drawTexturedModalRect (startWidth,startHeight,0,0,256,256);
		GlStateManager.popMatrix ();
		super.drawScreen (mouseX,mouseY,partialTicks);
	}

	@Override
	public void initGui () {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		buttonList.add (overview = new GuiTexturedButton (0,startWidth + 4,startHeight + 2,60,15,"Overview"));
		buttonList.add (manage = new GuiTexturedButton (1,startWidth + 55,startHeight + 2,50,15,"Manage"));
		buttonList.add (production = new GuiTexturedButton (2,startWidth + 95,startHeight + 2,68,15,"Production"));
		buttonList.add (research = new GuiTexturedButton (3,startWidth + 153,startHeight + 2,54,15,"Science"));
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
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.MANAGE,tile.getPos ()));
				break;
			case (2):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.PRODUCTION,tile.getPos ()));
				break;
			case (3):
				NetworkHandler.sendToServer (new OpenGuiMessage (GuiHandler.RESEARCH,tile.getPos ()));
				break;
		}
	}

	protected int keyAmount () {
		int amt = 1;
		if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL))
			if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT))
				amt = 10;
			else
				amt = 5;
		return amt;
	}

	protected boolean isWithin (int mouseX,int mouseY,int x,int y,int x2,int y2) {
		return mouseX >= x && mouseX < x2 && mouseY >= y && mouseY < y2;
	}
}
