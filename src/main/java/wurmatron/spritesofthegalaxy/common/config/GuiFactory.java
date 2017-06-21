package wurmatron.spritesofthegalaxy.common.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import wurmatron.spritesofthegalaxy.client.gui.ConfigGui;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {

	@Override
	public void initialize (Minecraft i) {
	}

	@Override
	public Class <? extends GuiScreen> mainConfigGuiClass () {
		return ConfigGui.class;
	}

	@Override
	public Set <RuntimeOptionCategoryElement> runtimeGuiCategories () {
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor (RuntimeOptionCategoryElement element) {
		return null;
	}

	@Override
	public boolean hasConfigGui () {
		return true;
	}

	@Override
	public GuiScreen createConfigGui (GuiScreen parentScreen) {
		return new ConfigGui (parentScreen);
	}
}