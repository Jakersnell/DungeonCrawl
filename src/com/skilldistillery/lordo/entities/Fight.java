/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.lordo.entities;

public class Fight extends Challenge {
	Enemy enemy = new Enemy("Orc");

	public Fight(Player player) throws Exception { 
		super(player);
	}
	
	public String getEnemyName() {
		return enemy.getName();
	}
	
	public String fightAnnouncement() {
		return getPlayer().getName() + " will be fighting an " + enemy.getName();
	}

	public void fightLoop() {
		while (getPlayer().isAlive() && enemy.isAlive()) {
			fightLogic();
		}
		fightResult();
	}

	public void fightLogic() {
		enemy.takeDamage(getPlayer().getAttack() - enemy.getDefense());
		getPlayer().takeDamage(enemy.getAttack() - getPlayer().getDefense());
	}

	public boolean fightResult() {
		boolean playerWins = false;
		if (getPlayer().isAlive()) {
			playerWins = true;
		}
		return playerWins;
	}
	
	@Override
	public boolean checkAbility() {
		if (getPlayer().abilityUsed == false && getPlayer() instanceof Fighter) {
			return true;
		}
		return false;
	}

}