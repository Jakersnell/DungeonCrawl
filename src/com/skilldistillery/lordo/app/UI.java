package com.skilldistillery.lordo.app;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/// ALL UI METHODS HAVE POTENTIAL TO RETURN NULL IF USER SELECTS QUIT
public class UI {
	private static final String onInvalidMessage = "Invalid input.";
	private final Scanner sc = new Scanner(System.in);
	private CLIGame game;

	public UI(CLIGame game) {
		this.game = game;
	}

	private void enterToContinue() {
		printGreen("Please press enter to continue...");
		sc.nextLine();
		clearScreen();
	}

	public void clearScreen() {
		for (int i = 0; i < 1000; i++) {
			System.out.println();
		}
	}

	public void exitMessage() {
		clearScreen();
		printGreen("goodbye!");
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			;
		} finally {
			clearScreen();
		}

	}

	public void printEvent(String message) {
		printGreen(message);
		enterToContinue();
	}

	public void printBadEvent(String message) {
		printErr(message);
		enterToContinue();
	}

	public void printErr(String message) {
		print(message, PrintColor.RED, "");
	}

	public void printGreen(String message) {
		print(message, PrintColor.GREEN, "");
	}

	public void printBlue(String message) {
		print(message, PrintColor.BLUE, "");
	}

	private void print(String message, PrintColor color, String append) {
		System.out.println(color.format(message) + append);
	}

	private boolean checkQuit(String input) {
		boolean isQuit = input.equalsIgnoreCase("quit");
		if (isQuit) {
			game.push(GameEvent.QUIT);
		}
		return isQuit;
	}

	public String getUserChoice(String message, String str1, String str2) {
		return userSelectFrom(message, Arrays.asList(str1, str2));
	}
	
	public String getUserChoice(String str1, String str2) {
		return getUserChoice("", str1, str2);
	}
	
	public Boolean userYesNo() {
		String answer = getUserChoice("yes", "no");
		return answer != null ? answer.equals("yes") : null;
	}

	public <T> T userSelectFrom(String message, List<T> options) {
		StringBuilder builder = new StringBuilder(message + "\nPlease select an option by number.\n");

		for (int i = 0; i < options.size(); i++) {
			builder.append("" + (i + 1) + " -- " + options.get(i) + "\n");
		}

		Integer userSelection = getUserInput(builder.toString(), Integer::parseInt,
				i -> (1 <= i && i <= options.size()));

		return (userSelection == null) ? null : options.get(userSelection - 1);
	}
	

	public <T> T userSelectFrom(List<T> options) {
		return userSelectFrom("", options);
	}

	public Integer getUserInt(String message) {
		return getUserInput(message, Integer::parseInt, a -> true);
	}

	public Integer getUserInt() {
		return getUserInt("");
	}

	public String getUserString(String message) {
		return getUserInput(message, str -> str, str -> true);
	}

	public String getUserString() {
		return getUserString("");
	}
	

	public <T> T getUserInput(String message, Function<String, T> parseMethod, Function<T, Boolean> isValidChecker)
			throws IllegalArgumentException {
		while (true) {
			try {
				printBlue(message + "\nType 'quit' to quit.\n");

				String input = sc.nextLine();
				if (checkQuit(input)) {
					return null;
				}
				
				T parsed = parseMethod.apply(input);
				
				if (isValidChecker.apply(parsed)) {
					System.out.println();
					clearScreen();
					return parsed;
				} else {
					throw new IllegalArgumentException();
				}
			} catch (IllegalArgumentException e) {
				printErr(onInvalidMessage);
			}
			enterToContinue();
		}
	}
}


enum PrintColor {
	GREEN("32"), RED("31"), BLUE("36");

	String code;

	PrintColor(String code) {
		this.code = code;
	}

	String format(String str) {
		return "\033[" + code + "m" + str + "\033[0m";
	}
}
