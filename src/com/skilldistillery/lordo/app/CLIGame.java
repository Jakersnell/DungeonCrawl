package com.skilldistillery.lordo.app;

import java.util.Deque;
import java.util.LinkedList;

public abstract class CLIGame {
	private Deque<GameEvent> eventQueue;
	protected UI cli;

	public abstract void run();

	public CLIGame() {
		eventQueue = new LinkedList<>();
		cli = new UI(this);
		push(GameEvent.INIT);
	}

	public void push(GameEvent event) {
		eventQueue.addFirst(event);
	}

	public GameEvent nextEvent() {
		if (eventQueue.isEmpty())
			return GameEvent.QUIT;
		return eventQueue.removeLast();
	}

	public void clearStack() {
		eventQueue.clear();
	}
	
	public boolean nextEventIsQuit() {
		return (eventQueue.size() != 0 && eventQueue.peek() == GameEvent.QUIT);
	}

}
