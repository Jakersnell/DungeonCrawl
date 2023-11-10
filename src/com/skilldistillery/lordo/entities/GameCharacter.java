package com.skilldistillery.lordo.entities;

import java.util.Objects;

public abstract class GameCharacter {
	protected String name;
	private int currentHP;
	private int maxHP;
	private int attack;
	private int defense;

	public GameCharacter(String name) {
		this.name = name;
		this.maxHP = this.currentHP = 100;
		this.attack = 20;
		this.defense = 10;

	}

	public boolean isAlive() {
		if (currentHP <= 0 ) {
			return false;
		} else {
			return true;
		}
	}

	public int takeDamage(int damage) {
		
		currentHP = currentHP - damage;

		return currentHP;

	}
	
	public void heal() {
		this.currentHP = maxHP;
	}

	public int recoverHP(int recoveredHP) {
		currentHP = currentHP + recoveredHP;
		
		return currentHP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	@Override
	public String toString() {
		return "Character Name: " + name + ", CurrentHP: " + currentHP + ", MaxHP: " + maxHP + ", Attack: "
				+ attack + ", Defense: " + defense + " ";
	}

	@Override
	public int hashCode() {
		return Objects.hash(attack, currentHP, defense, maxHP, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameCharacter other = (GameCharacter) obj;
		return attack == other.attack && currentHP == other.currentHP && defense == other.defense
				&& maxHP == other.maxHP && Objects.equals(name, other.name);
	}
	
	

}