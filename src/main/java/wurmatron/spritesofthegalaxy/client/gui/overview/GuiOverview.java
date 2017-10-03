package wurmatron.spritesofthegalaxy.client.gui.overview;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import wurmatron.spritesofthegalaxy.client.gui.GuiHabitatBase;
import wurmatron.spritesofthegalaxy.client.gui.utils.GuiTexturedButton;
import wurmatron.spritesofthegalaxy.common.reference.Global;
import wurmatron.spritesofthegalaxy.common.reference.Local;
import wurmatron.spritesofthegalaxy.common.tileentity.TileHabitatCore;
import wurmatron.spritesofthegalaxy.common.utils.DisplayHelper;

import java.awt.*;

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
		super.drawScreen (mouseX,mouseY,partialTicks);
		GlStateManager.pushMatrix ();
		mc.renderEngine.bindTexture (new ResourceLocation (Global.MODID,"textures/gui/overview.png"));
		drawTexturedModalRect (startWidth,startHeight,0,0,256,256);
		GlStateManager.popMatrix ();
		drawString (fontRenderer,I18n.translateToLocal (Local.LINEAGE) + ":    " + DisplayHelper.formatLineage (tile.getColony ()),startWidth + 145,startHeight + 22,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.SIZE) + ":         " + tile.mutiBlockSize + "x",startWidth + 145,startHeight + 30,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.POPULATION) + ": " + DisplayHelper.formatNum (tile.getPopulation ()) + " / " + DisplayHelper.formatNum (tile.getMaxPopulation ()),startWidth + 145,startHeight + 38,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.FOOD) + ":        " + DisplayHelper.formatNum (tile.getFood ()),startWidth + 145,startHeight + 46,Color.white.getRGB ());
		drawString (fontRenderer,I18n.translateToLocal (Local.MINERALS) + ":   " + DisplayHelper.formatNum (tile.getMinerals ()),startWidth + 145,startHeight + 54,Color.white.getRGB ());
	}

	@Override
	public void initGui () {
		super.initGui ();
		startWidth = (width - 256) / 2;
		startHeight = (height - 256) / 2;
		buttonList.add (income = new GuiTexturedButton (10,startWidth + 2,startHeight + 21,60,15,I18n.translateToLocal (Local.INCOME)));
		buttonList.add (stats = new GuiTexturedButton (11,startWidth + 2,startHeight + 37,60,15,I18n.translateToLocal (Local.STATS)));
		buttonList.add (info = new GuiTexturedButton (10,startWidth + 2,startHeight + 53,60,15,I18n.translateToLocal (Local.INFO)));
	}

	@Override
	public boolean doesGuiPauseGame () {
		return false;
	}

}
