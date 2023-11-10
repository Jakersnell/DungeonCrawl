package com.skilldistillery.lordo.entities;

public class Player extends GameCharacter {
	int treasure;
	boolean abilityUsed;

	public Player(String name) {
		super(name);
	}
	
	public int getTreasure() {
		return treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure +=  treasure;
	}

	public void usedAbility() {
	abilityUsed = true;
		
	}
	public void resetAbility() {
		abilityUsed = false;
	}
		
	
	
}