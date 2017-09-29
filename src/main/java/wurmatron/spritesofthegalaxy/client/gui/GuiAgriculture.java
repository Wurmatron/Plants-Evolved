package wurmatron.spritesofthegalaxy.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import wurmatron.spritesofthegalaxy.api.research.IResearch;
import wurmatron.spritesofthegalaxy.common.network.NetworkHandler;
import wurmatron.spritesofthegalaxy.common.network.server.ResearchUpdateMessage;
import wurmatron.spritesofthegalaxy.common.research.ResearchHelper;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class GuiAgriculture extends GuiManage {

	private int startWidth = width / 20 + width / 60;

	protected GuiButton landAdd;
	protected GuiButton landSub;
	private ScaledResolution res = new ScaledResolution (Minecraft.getMinecraft ());

	public GuiAgriculture (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		// TODO Scale depending on screen size
		drawCenteredString (Minecraft.getMinecraft ().fontRenderer,I18n.format ("research." + ResearchHelper.land.getName () + ".name") + " lvl " + getResearchLevel (ResearchHelper.land),res.getScaledWidth () / 5 + (res.getScaledWidth () / 30),height / 20 * 3,Color.WHITE.getRGB ());
	}

	@Override
	public void initGui () {
		super.initGui ();
		agriculture.visible = false;
		mines.visible = false;
		energyProduction.visible = false;
		magicProduction.visible = false;
		mobFarm.visible = false;
		liquidFarm.visible = false;
		nursery.visible = false;
		buttonList.add (landAdd = new GuiButton (100,res.getScaledWidth () / 10,height / 20 * 3,width / 40,width / 40,"+"));
		buttonList.add (landSub = new GuiButton (101,res.getScaledWidth () / 10 * 2 + res.getScaledWidth () / 20 * 3,height / 20 * 3,width / 40,width / 40,"-"));
	}

	@Override
	protected void actionPerformed (GuiButton button) throws IOException {
		super.actionPerformed (button);
		switch (button.id) {
			case (100):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.land,getResearchLevel (ResearchHelper.land) + 1,tile.getPos ()));
				break;
			case (101):
				NetworkHandler.sendToServer (new ResearchUpdateMessage (ResearchHelper.land,getResearchLevel (ResearchHelper.land) - 1,tile.getPos ()));
				break;
		}
	}

	private int getResearchLevel (IResearch research) {
		if (research != null && tile != null) {
			HashMap <IResearch, Integer> currentResearch = tile.getResearch ();
			if (currentResearch != null && currentResearch.size () > 0)
				return currentResearch.get (research);
		}
		return 0;
	}
}
