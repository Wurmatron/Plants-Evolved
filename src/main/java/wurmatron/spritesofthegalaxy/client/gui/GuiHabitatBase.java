package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import wurmatron.spritesofthegalaxy.api.mutiblock.IOutput;
import wurmatron.spritesofthegalaxy.api.mutiblock.StorageType;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.client.GuiHandler;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.OpenGuiMessage;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;
import wurmatron.spritesofthegalaxy.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiHabitatBase extends GuiScreen {

	protected int startWidth;
	protected int startHeight;

	// Top Bar
	protected GuiButton overview;
	protected GuiButton manage;
	protected GuiButton production;
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

	protected void displayString (IOutput output,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (output.getName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getOutputLevel (tile,output));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			for (StorageType st : output.getCost ().keySet ())
				displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (((MutiBlockHelper.getOutputLevel (tile,output) + keyAmount ()) * tile.getColonyValue (MutiBlockHelper.getType (st))))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			for (StorageType st : output.getCost ().keySet ())
				displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum ((MutiBlockHelper.getOutputLevel (tile,output) * tile.getColonyValue (MutiBlockHelper.getType (st))))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}

	protected void displayString (IResearch research,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal (research.getName ()) + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getResearchLevel (tile,research));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 13,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.COST_RESEARCH).replace ("'POINTS'",DisplayHelper.formatNum (MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research),MutiBlockHelper.getResearchLevel (tile,research) + keyAmount ()))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 13,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.GIVE_RESEARCH).replace ("'POINTS'",DisplayHelper.formatNum (MutiBlockHelper.calcPointsForResearch (research,MutiBlockHelper.getResearchLevel (tile,research) - keyAmount (),MutiBlockHelper.getResearchLevel (tile,research)))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}


	protected void displayString (StorageType type,int mouseX,int mouseY,int startX,int startH,int buttX,int buttY,int buttX2,int buttY2) {
		String str = I18n.translateToLocal ("gui." + type.getDisplayKey () + ".name") + " lvl " + DisplayHelper.formatNum (MutiBlockHelper.getStorageLevel (tile,type));
		drawString (fontRenderer,str,startWidth + startX - fontRenderer.getStringWidth (str) / 2,startHeight + startH,Color.white.getRGB ());
		if (isWithin (mouseX,mouseY,startWidth + buttX,startHeight + buttY,startWidth + buttX + 12,startHeight + buttY + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.GIVE_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calculateSellBack (MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type),MutiBlockHelper.getStorageLevel (tile,type) + keyAmount (),0)))));
			drawHoveringText (displayInfo,startWidth + buttX,startHeight + buttY);
		}
		if (isWithin (mouseX,mouseY,startWidth + buttX2,startHeight + buttY2,startWidth + buttX2 + 12,startHeight + buttY2 + 12)) {
			List <String> displayInfo = new ArrayList ();
			displayInfo.add (I18n.translateToLocal (Local.COST_MINERAL).replace ("'Minerals'",DisplayHelper.formatNum (MutiBlockHelper.calculateSellBack (MutiBlockHelper.calcMineralsForStorage (type,MutiBlockHelper.getStorageLevel (tile,type) - keyAmount (),MutiBlockHelper.getStorageLevel (tile,type),0)))));
			drawHoveringText (displayInfo,startWidth + buttX2,startHeight + buttY2);
		}
	}
}
