package wurmatron.spritesofthegalaxy.client.gui.manage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import org.lwjgl.input.Keyboard;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.ResearchUpdateMessage;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class GuiAgriculture extends GuiHabitatBase {

	protected GuiButton landAdd;
	protected GuiButton landSub;
	protected GuiButton greenAdd;
	protected GuiButton greenSub;
	protected GuiButton underAdd;
	protected GuiButton underSub;
	protected GuiButton hydroAdd;
	protected GuiButton hydroSub;
	protected GuiButton syncAdd;
	protected GuiButton syncSub;
	protected GuiButton augAdd;
	protected GuiButton augSub;

	public GuiAgriculture (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		super.drawScreen (mouseX,mouseY,partialTicks);
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.land.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.land)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.land.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.land))) / 2,startHeight + 24,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.greenHouse.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.greenHouse)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.greenHouse.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.greenHouse))) / 2,startHeight + 40,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.underGround.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.underGround)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.underGround.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.underGround))) / 2,startHeight + 56,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.hydroPonics.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.hydroPonics)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.hydroPonics.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.hydroPonics))) / 2,startHeight + 72,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.synthesis.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.synthesis)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.synthesis.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.synthesis))) / 2,startHeight + 88,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (ResearchHelper.augmentation.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.augmentation)),startWidth + 57 - fontRenderer.getStringWidth (I18n.translateToLocal (ResearchHelper.augmentation.getName ()) + " " + DisplayHelper.formatNum (getResearchLevel (ResearchHelper.augmentation))) / 2,startHeight + 104,Color.white.getRGB ());
	}

	@Override
	public void initGui () {
		super.initGui ();
		buttonList.add (landAdd = new GuiButton (100,startWidth + 4,startHeight + 23,12,12,"+"));
		buttonList.add (landSub = new GuiButton (101,startWidth + 96,startHeight + 23,12,12,"-"));
		buttonList.add (greenAdd = new GuiButton (102,startWidth + 4,startHeight + 39,12,12,"+"));
		buttonList.add (greenSub = new GuiButton (103,startWidth + 96,startHeight + 39,12,12,"-"));
		buttonList.add (underAdd = new GuiButton (104,startWidth + 4,startHeight + 55,12,12,"+"));
		buttonList.add (underSub = new GuiButton (105,startWidth + 96,startHeight + 55,12,12,"-"));
		buttonList.add (hydroAdd = new GuiButton (106,startWidth + 4,startHeight + 71,12,12,"+"));
		buttonList.add (hydroSub = new GuiButton (107,startWidth + 96,startHeight + 71,12,12,"-"));
		buttonList.add (syncAdd = new GuiButton (108,startWidth + 4,startHeight + 87,12,12,"+"));
		buttonList.add (syncSub = new GuiButton (109,startWidth + 96,startHeight + 87,12,12,"-"));
		buttonList.add (augAdd = new GuiButton (110,startWidth + 4,startHeight + 103,12,12,"+"));
		buttonList.add (augSub = new GuiButton (111,startWidth + 96,startHeight + 103,12,12,"-"));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (100):
				proccessButton (ResearchHelper.land);
				break;
			case (101):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.land,getResearchLevel (ResearchHelper.land) - keyAmount (),tile.getPos ()));
				break;
			case (102):
				proccessButton (ResearchHelper.greenHouse);
				break;
			case (103):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.greenHouse,getResearchLevel (ResearchHelper.greenHouse) - keyAmount (),tile.getPos ()));
				break;
			case (104):
				proccessButton (ResearchHelper.underGround);
				break;
			case (105):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.underGround,getResearchLevel (ResearchHelper.underGround) - keyAmount (),tile.getPos ()));
				break;
			case (106):
				proccessButton (ResearchHelper.hydroPonics);
				break;
			case (107):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.hydroPonics,getResearchLevel (ResearchHelper.hydroPonics) - keyAmount (),tile.getPos ()));
				break;
			case (108):
				proccessButton (ResearchHelper.synthesis);
				break;
			case (109):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.synthesis,getResearchLevel (ResearchHelper.synthesis) - keyAmount (),tile.getPos ()));
				break;
			case (110):
				proccessButton (ResearchHelper.augmentation);
				break;
			case (111):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.augmentation,getResearchLevel (ResearchHelper.augmentation) - keyAmount (),tile.getPos ()));
				break;
		}
	}

	private int getResearchLevel (IResearch research) {
		if (research != null && tile != null) {
			HashMap <IResearch, Integer> currentResearch = tile.getResearch ();
			if (currentResearch != null && currentResearch.size () > 0 && currentResearch.containsKey (research))
				return currentResearch.get (research);
		}
		return 0;
	}

	private int keyAmount () {
		int amt = 1;
		if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL))
			if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT))
				amt = 10;
			else
				amt = 5;
		return amt;
	}

	private void proccessButton (IResearch research) {
		if (ResearchHelper.isValidMove (tile.getResearch (),research))
			NetworkHandler.sendToServer (new ResearchUpdateMessage (research,getResearchLevel (ResearchHelper.augmentation) + keyAmount (),tile.getPos ()));
		else
			mc.ingameGUI.getChatGUI ().printChatMessage (new TextComponentString (I18n.translateToLocal (Local.MISSING_RESEARCH).replace ("$Research$",DisplayHelper.formatNeededResearch (ResearchHelper.getNeededResearch (tile.getResearch (),research)))));
	}
}
