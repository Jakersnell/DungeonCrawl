/*
Synopsis:
Author:
Version: 
 */
package com.skilldistillery.lordo.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Riddle extends Challenge {
	private Map<String, List<String>> riddleMap;
	private List<String> answers = new ArrayList<>();
	private String riddle;
	private String playerAnswer;
	private String correctAnswer;

	{
		riddleMap = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader("Riddles.txt"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split("\t");
				String key = splitLine[0];
				List<String> choices = new ArrayList<>();
				choices.add(splitLine[1]);
				choices.add(splitLine[2]);
				choices.add(splitLine[3]);
				choices.add(splitLine[4]);
				riddleMap.put(key, choices);
			}
		}
	}

	public Riddle(Player player) throws Exception {
		super(player);
	}
	
	
	public String getRiddle() {
		return riddle;
	}


	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	public void setPlayerAnswer(String answer) {
		this.playerAnswer = answer;
	}
	
	public void setRiddle() { // error in index
		Random rand = new Random();
		List<String> keys = new ArrayList<String>(riddleMap.keySet());
		riddle = keys.get(rand.nextInt(keys.size()));
		answers = riddleMap.get(riddle);
		correctAnswer = answers.get(0);
	}
	
	public boolean riddleResult() {
		boolean playerPassChallenge = false;
		if (playerAnswer.equals(correctAnswer)) {
			playerPassChallenge = true;
		} 
		return playerPassChallenge;
	}
	
	@Override
	public boolean checkAbility() {
		if (getPlayer().abilityUsed == false && getPlayer() instanceof Mage) {
			return true;
		}
		return false;
	}

}