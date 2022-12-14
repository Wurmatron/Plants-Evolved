package io.wurmatron.plants.client.gui.overview;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import io.wurmatron.plants.api.mutiblock.IStructure;
import io.wurmatron.plants.api.mutiblock.StorageType;
import io.wurmatron.plants.api.research.IResearch;
import io.wurmatron.plants.client.gui.GuiHabitatBase;
import io.wurmatron.plants.client.gui.utils.GuiTexturedButton;
import io.wurmatron.plants.common.config.Settings;
import io.wurmatron.plants.common.network.NetworkHandler;
import io.wurmatron.plants.common.network.client.CancelQueueRequest;
import io.wurmatron.plants.common.network.client.ClientBuildQueueRequest;
import io.wurmatron.plants.common.reference.Global;
import io.wurmatron.plants.common.reference.Local;
import io.wurmatron.plants.common.reference.NBT;
import io.wurmatron.plants.common.tileentity.TileHabitatCore;
import io.wurmatron.plants.common.utils.DisplayHelper;
import io.wurmatron.plants.common.utils.MutiBlockHelper;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuiOverview extends GuiHabitatBase {

	private HashMap <Object, Integer> cancelButtons = new HashMap <> ();

	public GuiOverview (TileHabitatCore tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		GlStateManager.pushMatrix ();
		mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/overview.png"));
		drawTexturedModalRect (startWidth,startHeight,0,0,256,256);
		GlStateManager.popMatrix ();
		drawString (fontRenderer,I18n.translateToLocal (Local.LINEAGE) + ": ",startWidth + 140,startHeight + 22,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatLineage (tile.getColony ()),startWidth + 200,startHeight + 22,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.SIZE) + ":",startWidth + 140,startHeight + 30,Color.white.getRGB ());
		drawString (fontRenderer,tile.mutiBlockSize + "x",startWidth + 200,startHeight + 30,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.POPULATION) + ":",startWidth + 140,startHeight + 38,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum ((int) tile.getColonyValue (NBT.POPULATION,null)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_POPULATION)),startWidth + 200,startHeight + 38,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.FOOD) + ":",startWidth + 140,startHeight + 46,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum (tile.getColonyValue (NBT.FOOD) - tile.getPopulationFoodUsage ()),startWidth + 200,startHeight + 46,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.MINERALS) + ":",startWidth + 140,startHeight + 54,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum (tile.getColonyValue (NBT.MINERALS)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_MINERALS)),startWidth + 200,startHeight + 54,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.ENERGY) + ":",startWidth + 140,startHeight + 62,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum ((tile.getColonyValue (NBT.ENERGY) - tile.getPowerUsage ())),startWidth + 200,startHeight + 62,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.GEM) + ":",startWidth + 140,startHeight + 70,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum (tile.getColonyValue (NBT.GEM)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_GEM)),startWidth + 200,startHeight + 70,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.MAGIC) + ":",startWidth + 140,startHeight + 78,Color.white.getRGB ());
		drawString (fontRenderer,DisplayHelper.formatNum (tile.getColonyValue (NBT.MAGIC)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_MAGIC)),startWidth + 200,startHeight + 78,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.QUEUE) + " " + DisplayHelper.formatNum (tile.getBuildQueue ().size ()) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.BUILD_QUEUE)),startWidth + 15,startHeight + 71,Color.white.getRGB ());
		if (buttonList.size () > 3) {
			List <GuiButton> temp = new ArrayList <> ();
			for (GuiButton button : buttonList)
				if (button.id >= 100)
					temp.add (button);
			buttonList.removeAll (temp);
		}
		if (tile.getBuildQueue ().size () > 0)
			for (int index = 0; index < tile.getBuildQueue ().size (); index++)
				if (index <= 9) {
					GlStateManager.pushMatrix ();
					mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/parts.png"));
					drawTexturedModalRect (startWidth + 11,startHeight + 89 + (index * 17),0,0,110,15);
					drawString (fontRenderer,getDisplayName (tile.getBuildQueue ().get (index)[0]) + " -> lvl " + tile.getBuildQueue ().get (index)[1] + " | " + tile.getBuildQueue ().get (index)[2],startWidth + 15,startHeight + 92 + (index * 17),Color.white.getRGB ());
					GlStateManager.popMatrix ();
					int id = cancelButtons.size () + 100;
					buttonList.add (new GuiTexturedButton (id,startWidth + 121,startHeight + 90 + (index * 17),15,15,3,"X"));
					cancelButtons.put (tile.getBuildQueue ().get (index)[0],id);
				} else if (index <= 17) {
					GlStateManager.pushMatrix ();
					mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/parts.png"));
					drawTexturedModalRect (startWidth + 135,startHeight + 106 + (index * 17),0,0,110,15);
					drawString (fontRenderer,getDisplayName (tile.getBuildQueue ().get (index)[0]) + " -> lvl " + tile.getBuildQueue ().get (index)[1] + " | " + DisplayHelper.formatNum ((int) tile.getBuildQueue ().get (index)[2]),startWidth + 15,startHeight + 139 + (index * 17),Color.white.getRGB ());
					GlStateManager.popMatrix ();
				}
		if (isWithin (mouseX,mouseY,startWidth + 140,startHeight + 38,startWidth + 180,startHeight + 44)) {
			List <String> text = new ArrayList <> ();
			text.add ("Workers: " + tile.getRequiredWorkers () + " / " + tile.getAmountOfWorkers ());
			int growth = (int) (tile.getColonyValue (NBT.POPULATION,null) - (tile.getColonyValue (NBT.POPULATION,null) * Settings.populationGrowth));
			if (tile.getColonyValue (NBT.FOOD) == 0)
				growth = 0;
			text.add ("Growth: " + growth);
			drawHoveringText (text,startWidth + 200,startHeight + 46);
		}
		if (isWithin (mouseX,mouseY,startWidth + 140,startHeight + 46,startWidth + 180,startHeight + 50))
			drawHoveringText ("Usage: " + tile.getPopulationFoodUsage (),startWidth + 200,startHeight + 52);
		if (isWithin (mouseX,mouseY,startWidth + 140,startHeight + 54,startWidth + 180,startHeight + 60))
			drawHoveringText ("+" + DisplayHelper.formatNum (MutiBlockHelper.getMineralIncome (tile)),startWidth + 200,startHeight + 62);
		if (isWithin (mouseX,mouseY,startWidth + 140,startHeight + 62,startWidth + 180,startHeight + 70))
			drawHoveringText ("Produces: " + DisplayHelper.formatNum (tile.getColonyValue (NBT.ENERGY)),startWidth + 200,startHeight + 72);
	}

	@Override
	public void initGui () {
		super.initGui ();
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		GuiTexturedButton income = new GuiTexturedButton (10,startWidth + 6,startHeight + 21,60,15,I18n.translateToLocal (Local.INCOME));
		GuiTexturedButton stats = new GuiTexturedButton (11,startWidth + 6,startHeight + 37,60,15,I18n.translateToLocal (Local.STATS));
		GuiTexturedButton info = new GuiTexturedButton (10,startWidth + 6,startHeight + 53,60,15,I18n.translateToLocal (Local.INFO));
		// WIP
		income.enabled = false;
		stats.enabled = false;
		info.enabled = false;
		buttonList.add (income);
		buttonList.add (stats);
		buttonList.add (info);
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

	private String getDisplayName (Object obj) {
		if (obj instanceof IStructure)
			return ((IStructure) obj).getName ().substring (0,1).toUpperCase () + ((IStructure) obj).getName ().substring (1,((IStructure) obj).getName ().length ());
		else if (obj instanceof IResearch)
			return ((IResearch) obj).getName ();
		else if (obj instanceof StorageType)
			return I18n.translateToLocal (((StorageType) obj).getDisplayKey ());
		return "Unknown";
	}

	@Override
	protected void actionPerformed (GuiButton butt) throws IOException {
		super.actionPerformed (butt);
		if (butt.id >= 100) {
			for (Object structure : cancelButtons.keySet ())
				if (cancelButtons.get (structure) == butt.id) {
					NetworkHandler.sendToServer (new CancelQueueRequest (tile.getPos (),structure));
					NetworkHandler.sendToServer (new ClientBuildQueueRequest (tile.getPos ()));
				}
		}
	}
}
