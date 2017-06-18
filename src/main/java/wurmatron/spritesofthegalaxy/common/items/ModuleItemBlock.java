package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModuleItemBlock extends ItemBlock {

	public ModuleItemBlock (Block block) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
	}

	@Override
	public int getMetadata (int damage) {
		return damage;
	}

	@Override
	public String getUnlocalizedName (ItemStack stack) {
		return super.getUnlocalizedName (stack) + "_" + stack.getItemDamage ();
	}
}
