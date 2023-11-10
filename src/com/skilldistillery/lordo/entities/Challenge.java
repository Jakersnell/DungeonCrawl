/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.lordo.entities;

public abstract class Challenge {
	private Player player;

	public Challenge(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public abstract boolean checkAbility();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Challenge accepted by ");
		builder.append(player.getName());
		return builder.toString();
	}

}
