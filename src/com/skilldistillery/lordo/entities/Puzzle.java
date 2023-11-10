/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.lordo.entities;

import java.util.Random;

public class Puzzle extends Challenge {
	private int num1;
	private int num2;
	private char operator;
	private int answer;
	private int guess;
	Random rand = new Random();

	public Puzzle(Player player) throws Exception {
		super(player);
	}
	
	public int getAnswer() {
		return answer;
	}

	public int getGuess() {
		return guess;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}

	public void generateProblem() {
		char[] opChoices = { '+', '-', '*' };
		operator = opChoices[rand.nextInt(opChoices.length - 1)];
		num1 = rand.nextInt(100);
		num2 = rand.nextInt(100);
	}

	@Override
	public boolean checkAbility() {
		if (getPlayer().abilityUsed == false && getPlayer() instanceof Rogue) {
			return true;
		}
		return false;
	}
	
	public String displayProblem() {
		return num1 + " " + operator + " " + num2 + " = ?";
	}
	
	public void setAnswer() {
		if (operator == '+') {
			answer = num1 + num2;
		} else if (operator == '-') {
			answer = num1 - num2;
		} else if (operator == '*') {
			answer = num1 * num2;
		}
	}

	public boolean puzzleResult() {
		setAnswer();
		getGuess();
		boolean playerPassChallenge = false;
		if (guess == answer) {
			playerPassChallenge = true;
		} else {
			getPlayer().takeDamage(25);
			playerPassChallenge = false;
		}
		return playerPassChallenge;
	}

}