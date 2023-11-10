package com.skilldistillery.lordo.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.skilldistillery.lordo.entities.Challenge;
import com.skilldistillery.lordo.entities.Fight;
import com.skilldistillery.lordo.entities.Fighter;
import com.skilldistillery.lordo.entities.Mage;
import com.skilldistillery.lordo.entities.Player;
import com.skilldistillery.lordo.entities.Puzzle;
import com.skilldistillery.lordo.entities.Riddle;
import com.skilldistillery.lordo.entities.Rogue;
import com.skilldistillery.lordo.entities.Treasure;

public class App extends CLIGame {
	
	/// UI cli is inherited from CLIGame
	/// Stack eventQueue is as-well
	/// ALL UI METHODS HAVE POTENTIAL TO RETURN NULL IF USER SELECTS QUIT

	private final boolean DEV = false;
	private final int REQ_GOLD = 1000;
	private final Random rng = new Random();
	
	private Challenge currChallenge;
	private Player playerCharacter;
	private List<Challenge> challenges;
	private Iterator<Challenge> chalIter;

	public static void main(String[] args) {
		App app = new App();
		app.run();
	}

	// ---------------------------------------------------Event-Loop------------------------------------------------------------------------------

	@Override
	public void run() {
		boolean playLoop = true;

		while (playLoop) {
			switch (nextEvent()) {

			case INIT:
				init();
				break;

			case GAME_START:
				start();
				break;

			case START_CHALLENGE:
				startChallenge();
				break;

			case EXE_CHALLENGE:
				executeChallenge();
				break;

			case CHECK_HP:
				checkHP();
				break;

			case HEAL:
				playerHeal();
				break;

			case GAME_END:
				gameEnd();
				break;

			case QUIT:
				playLoop = false;
				break;
			}
		}

		cli.exitMessage();

	}

	// ---------------------------------------------------Event-methods------------------------------------------------------------------------

	/// if playerCharacter is null, isQuit() has been called and has pushed
	/// 'GameEvent.QUIT' to the stack.
	public void init() {
		playerCharacter = null;
		initCharacter();
		if (playerCharacter == null) {
			return;
		}
		initChallenges();

	}
	
	/// prompts user, initializes character.
	private void initCharacter() {
		cli.clearScreen();
		String name = cli.getUserString("Hello Traveler! What is your name?");
		if (name == null)
			return;
		String playerClass = cli.userSelectFrom("Who are you?", Arrays.asList("mage", "rogue", "fighter"));
		if (playerClass == null) {
			return;
		}
		playerCharacter = createPlayer(name, playerClass);
	}

	private void initChallenges() {
		try {
			challenges = Arrays.asList(new Puzzle(playerCharacter), new Riddle(playerCharacter),
					new Fight(playerCharacter));
			Collections.shuffle(challenges);
			push(GameEvent.GAME_START);
		} catch (Exception e) {
			if (DEV)
				System.out.println("in exception\n" + e);
			push(GameEvent.QUIT);
		}
	}

	public Player createPlayer(String name, String playerClass) {
		Player player = null;

		switch (playerClass) {
		case "mage":
			player = new Mage(name);
			break;

		case "rogue":
			player = new Rogue(name);
			break;

		case "fighter":
			player = new Fighter(name);
			break;

		default:
			throw new IllegalArgumentException("Could not match " + playerClass + " to a class type.");
		}

		return player;
	}

	/// game start behavior here; initialize objects, print behaviors
	public void start() {
		chalIter = challenges.iterator();
		cli.printEvent("Welcome to the dungeon, full of mysteries, terrors, and treasure!\n" + "You must collect "
				+ REQ_GOLD + " to pay the goblin king to let you out!");
		push(GameEvent.START_CHALLENGE);
	}

	/// primes for next challenge, calls Iterator to place challenge in
	/// 'currChallenge'
	public void startChallenge() {
		if (chalIter.hasNext()) {
			currChallenge = chalIter.next();
			push(GameEvent.EXE_CHALLENGE);
		} else {
			push(GameEvent.GAME_END);
		}
	}

	/// executes related methods for challenges pushes the next steps to the stack.
	public void executeChallenge() {
		cli.printGreen("This challenge is a " + currChallenge.getClass().getSimpleName() + "\n");
		if (currChallenge instanceof Fight) {
			fightChallenge();
		} else if (currChallenge instanceof Riddle) {
			riddleChallenge();
		} else if (currChallenge instanceof Puzzle) {
			puzzleChallenge();
		}
		if (nextEventIsQuit()) {
			return;
		}
		push(GameEvent.CHECK_HP);
		push(GameEvent.START_CHALLENGE);
		cli.printBlue("Your current HP: " + playerCharacter.getCurrentHP());
		cli.printBlue("Your current Treasure: " + playerCharacter.getTreasure());
	}

	/// does not push Event to stack, only called as an intermediary. restarts if character died.
	public void checkHP() {
		if (!playerCharacter.isAlive()) {
			cli.printBadEvent("You died!\nTime to respawn!");
			push(GameEvent.INIT);
		}
	}

	/// heals player, is also Intermediary, pushes nothing to stack.
	public void playerHeal() {
		cli.printEvent("You are healing.");
		playerCharacter.heal();
	}

	/// end of game, does not exit.
	public void gameEnd() {
		if (REQ_GOLD <= playerCharacter.getTreasure()) {
			cli.printEvent(playerCharacter.getName() + ", congratulations on completing the dungeon!\n" + "With "
					+ playerCharacter.getTreasure() + " treasure!");
			push(GameEvent.QUIT);
		} else {
			String choice = cli.getUserString("You will now heal and restart.");
			if (choice != null) {
				push(GameEvent.HEAL);
				push(GameEvent.GAME_START);
			} else {
				push(GameEvent.QUIT);
			}
		}
	}
	
	public void challengeResult(boolean won) {
		if (won) {
			Treasure treasure = Treasure.values()[rng.nextInt((Treasure.values().length))];
			cli.printEvent("Congratulations, you have won a " + treasure);
			playerCharacter.setTreasure(treasure.getValue());
		} else {
			cli.printBadEvent("You lost the challenge!");
		}
	}

	// ---------------------------------------------------Challenge-Methods------------------------------------------------------------------------

	public void fightChallenge() {
		Fight fight = (Fight) currChallenge;
		cli.printErr("Welcome to the fighting pits. Two may enter, one may leave.");
		cli.printBadEvent(fight.fightAnnouncement());
		fight.fightLoop();
		challengeResult(fight.fightResult());
	}

	public void puzzleChallenge() {
		Puzzle puzzle = (Puzzle) currChallenge;
		cli.printErr("You have been caught in a trap. Answer correctly or feel the pain.");
		puzzle.generateProblem();
		Integer userInt = cli.getUserInt(puzzle.displayProblem());
		if (userInt == null)
			return;
		puzzle.setGuess(userInt);
		challengeResult(puzzle.puzzleResult());
	}

	public void riddleChallenge() {
		Riddle riddle = (Riddle) currChallenge;
		cli.printErr("Welcome to the Sphinx Riddle room. Answer correctly or feel the pain.");
		riddle.setRiddle();
		cli.printGreen(riddle.getRiddle());
		List<String> answers = riddle.getAnswers();
		Collections.shuffle(answers);
		String answer = cli.userSelectFrom("Please select one of the following", answers);
		if (answer == null) 
			return;
		riddle.setPlayerAnswer(answer);
		challengeResult(riddle.riddleResult());
	}

}
