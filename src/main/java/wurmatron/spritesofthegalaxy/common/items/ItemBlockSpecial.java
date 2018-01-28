package wurmatron.spritesofthegalaxy.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import wurmatron.spritesofthegalaxy.api.mutiblock.SpecialType;

public class ItemBlockSpecial extends ItemBlock {

	public ItemBlockSpecial (Block block) {
		super (block);
		setMaxDamage (0);
		setHasSubtypes (true);
	}

	@Override
	public String getUnlocalizedName (ItemStack stack) {
		return super.getUnlocalizedName (stack).substring (5) + "." + (SpecialType.values ().length > stack.getItemDamage () ? SpecialType.values ()[stack.getItemDamage ()].getName () : "invalid");
	}
}
