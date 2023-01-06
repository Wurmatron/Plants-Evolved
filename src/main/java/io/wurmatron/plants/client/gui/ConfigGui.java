package io.wurmatron.plants.client.gui;


import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import io.wurmatron.plants.common.config.ConfigHandler;
import io.wurmatron.plants.common.reference.Global;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig {

	public ConfigGui (GuiScreen parent) {
		super (parent,getConfigElements (),Global.MODID,false,false,"Sprites Of The Galaxy Configuration");
	}

	private static List <IConfigElement> getConfigElements () {
		List <IConfigElement> list = new ArrayList <> ();
		list.add (categoryElement (Configuration.CATEGORY_GENERAL,"General","config.category.general"));
		list.add (categoryElement (Global.HABITAT,"Habitat","config.category.habitat"));
		return list;
	}

	private static IConfigElement categoryElement (String category,String name,String tooltip_key) {
		return new DummyConfigElement.DummyCategoryElement (name,tooltip_key,new ConfigElement (ConfigHandler.config.getCategory (category)).getChildElements ());
	}
}