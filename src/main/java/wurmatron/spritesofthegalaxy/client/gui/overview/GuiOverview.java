package wurmatron.spritesofthegalaxy.client.gui.overview;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.reference.NBT;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore2;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;

import java.awt.*;

public class GuiOverview extends GuiHabitatBase {


	public GuiOverview (TileHabitatCore2 tile) {
		super (tile);
	}

	@Override
	public void drawScreen (int mouseX,int mouseY,float partialTicks) {
		super.drawScreen (mouseX,mouseY,partialTicks);
		GlStateManager.pushMatrix ();
		mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/overview.png"));
		drawTexturedModalRect (startWidth,startHeight,0,0,256,256);
		GlStateManager.popMatrix ();
		drawString (fontRenderer,I18n.translateToLocal (Local.LINEAGE) + ":    " + DisplayHelper.formatLineage (tile.getColony ()),startWidth + 140,startHeight + 22,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.SIZE) + ":         " + tile.mutiBlockSize + "x",startWidth + 140,startHeight + 30,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.POPULATION) + ": " + DisplayHelper.formatNum (tile.getColonyValue (NBT.POPULATION)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_POPULATION)),startWidth + 140,startHeight + 38,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.FOOD) + ":        " + DisplayHelper.formatNum (tile.getColonyValue (NBT.FOOD) - tile.getPopulationFoodUsage ()),startWidth + 140,startHeight + 46,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.MINERALS) + ":   " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MINERALS)) + " / " + DisplayHelper.formatNum (tile.getColonyValue (NBT.MAX_MINERALS)),startWidth + 140,startHeight + 54,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.ENERGY) + ":    " + DisplayHelper.formatNum ((tile.getColonyValue (NBT.ENERGY) - tile.getPowerUsage ())),startWidth + 140,startHeight + 62,Color.white.getRGB ());
	}

	@Override
	public void initGui () {
		super.initGui ();
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		buttonList.add (new GuiTexturedButton (10,startWidth + 6,startHeight + 21,60,15,I18n.translateToLocal (Local.INCOME)));
		buttonList.add (new GuiTexturedButton (11,startWidth + 6,startHeight + 37,60,15,I18n.translateToLocal (Local.STATS)));
		buttonList.add (new GuiTexturedButton (10,startWidth + 6,startHeight + 53,60,15,I18n.translateToLocal (Local.INFO)));
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

}
