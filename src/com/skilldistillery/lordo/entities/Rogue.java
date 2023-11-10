package com.skilldistillery.lordo.entities;


public class Rogue extends Player implements AbilityClass{

	public Rogue(String name) {
		super(name);
	}

	@Override
	public String getPlayerClass() {
		String intro = "I'm a Rogue";
		return intro;
	}

}