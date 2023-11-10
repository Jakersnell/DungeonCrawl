package com.skilldistillery.lordo.entities;

public class Mage extends Player implements AbilityClass {

	public Mage(String name) {
		super(name);
		this.setAttack(this.getAttack() + 10);
		this.setDefense(this.getDefense() - 5);
	}
	

	@Override
	public String getPlayerClass() {
		String intro = "I'm a Mage";
		
		return intro;
	}
	

}