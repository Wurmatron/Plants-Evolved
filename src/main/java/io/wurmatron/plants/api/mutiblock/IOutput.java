package io.wurmatron.plants.api.mutiblock;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

public interface IOutput {

	String getName ();

	HashMap <StorageType, Integer> getCost ();

	ItemStack getItem ();

	HashMap <IStructure, Integer> getRequiredStructures ();
}
