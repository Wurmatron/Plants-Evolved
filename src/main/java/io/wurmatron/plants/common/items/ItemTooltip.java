package io.wurmatron.plants.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemTooltip extends ItemBlock {

	private ArrayList <String> tips;

	public ItemTooltip (Block block,ArrayList <String> tooltip) {
		super (block);
		this.tips = tooltip;
	}

	@Override
	public void addInformation (ItemStack stack,@Nullable World world,List <String> tooltip,ITooltipFlag flagIn) {
		Collections.addAll (tooltip,tips.toArray (new String[0]));
	}
}
