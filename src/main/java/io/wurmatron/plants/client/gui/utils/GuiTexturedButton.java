package io.wurmatron.plants.client.gui.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import io.wurmatron.plants.common.reference.Global;

public class GuiTexturedButton extends GuiButton {

	private static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation (Global.MODID,"textures/gui/button.png");
	private int buttonTexID;

	public GuiTexturedButton (int buttonId,int x,int y,int widthIn,int heightIn,String buttonText) {
		this (buttonId,x,y,widthIn,heightIn,0,buttonText);
	}

	public GuiTexturedButton (int buttonId,int x,int y,int widthIn,int heightIn,int buttonTexID,String buttonText) {
		super (buttonId,x,y,widthIn,heightIn,buttonText);
		this.buttonTexID = buttonTexID;
	}

	@Override
	public void drawButton (Minecraft mc,int mouseX,int mouseY,float particleTicks) {
		if (this.visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			mc.getTextureManager ().bindTexture (BUTTON_TEXTURES);
			GlStateManager.color (1.0F,1.0F,1.0F,1.0F);
			this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState (this.hovered) + (buttonTexID * 60) + (buttonTexID > 0 ? 20 : 0);
			GlStateManager.enableBlend ();
			GlStateManager.tryBlendFuncSeparate (GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,GlStateManager.SourceFactor.ONE,GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc (GlStateManager.SourceFactor.SRC_ALPHA,GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect (this.x,this.y,0,i * 20,this.width / 2,this.height);
			this.drawTexturedModalRect (this.x + this.width / 2,this.y,200 - this.width / 2,i * 20,this.width / 2,this.height);
			this.mouseDragged (mc,mouseX,mouseY);
			int j = 14737632;
			if (packedFGColour != 0) {
				j = packedFGColour;
			} else if (!this.enabled) {
				j = 10526880;
			} else if (this.hovered) {
				j = 16777120;
			}
			drawCenteredString (fontrenderer,this.displayString,this.x + this.width / 2,this.y + (this.height - 8) / 2,j);
		}
	}
}
