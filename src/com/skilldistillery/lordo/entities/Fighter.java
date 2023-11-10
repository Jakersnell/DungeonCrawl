package com.skilldistillery.lordo.entities;

public class Fighter extends Player implements AbilityClass {

	public Fighter(String name) {
		super(name);
		this.setAttack(this.getMaxHP() + 10);
		this.setCurrentHP(this.getMaxHP() + 10);
		this.setAttack(this.getAttack()+ 5);
		this.setDefense(this.getDefense()+5);
	}

	@Override
	public String getPlayerClass() {
		String intro ="I'm a Fighter!";
		return intro;
	}

}