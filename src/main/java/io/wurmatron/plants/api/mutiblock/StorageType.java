package io.wurmatron.plants.api.mutiblock;

import io.wurmatron.plants.common.reference.NBT;

public enum StorageType {

	POPULATION (NBT.MAX_POPULATION,100,200),MINERAL (NBT.MAX_MINERALS,500,250),MAGIC (NBT.MAX_MAGIC,50,1000),GEM (NBT.MAX_GEM,100,1000),BUILD_QUEUE (NBT.BUILD_QUEUE,1,1000);

	private final String displayKey;
	private final double scale;
	private final int cost;

	StorageType (String displayKey,double scaling,int mineral) {
		this.displayKey = displayKey;
		this.scale = scaling;
		this.cost = mineral;
	}

	public String getDisplayKey () {
		return displayKey;
	}

	public double getScale () {
		return scale;
	}

	public int getCost () {
		return cost;
	}
}
