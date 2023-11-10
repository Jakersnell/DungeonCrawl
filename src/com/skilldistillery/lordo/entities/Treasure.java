/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.lordo.entities;

public enum Treasure {
	CHALICE("Golden Chalice", 15), CHEST("Small Chest", 60), ARTWORK("Hotel Art", 20),
	JEWELS("Sapphire and Pearls", 40), BAG("Leather pouch", 25);

	final private String name;
	final private int value;

	Treasure(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return name + " worth " + value;
	}
}
