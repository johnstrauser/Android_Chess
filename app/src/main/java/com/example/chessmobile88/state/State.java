package com.example.chessmobile88.state;

import java.util.Scanner;

import com.example.chessmobile88.board.Board;
/**
 * <p>Where the management of the game state takes place, (i.e when a player is in check or checkmate occurs)
 * Contains the current turn and determines if game is over or not.</p>
 * @author Joshua Pineda and John Strauser
 *
 */
public class State {
	/**
	 * <p>Status number meaning:
	 * 0 = normal game state
	 * 1 = Player with current turn is in check
	 * 2 = Player with current turn is in checkmate.</p>
	 */
	private int status = 0;

	/**
	 * <p>0 = White's turn 
	 * 1 = Black's turn</p>
	 */
	private int turn = 0;
	//Type is still tentative here
	int InCheck;
	
	/**
	 * <p>The player enters their given move through the terminal</p>
	 * @return String input given from the user
	 */
	public String turn() {
		Scanner s = new Scanner(System.in);
		String move;
		System.out.println();
		if (this.turn == 0) {
			System.out.print("White's move: ");
			move = s.nextLine();
		}
		else {
			System.out.print("Black's move: ");
			move = s.nextLine();
		}
		System.out.println();
		
		return move;
	}
	/**
	 * <p>Changes state of game (0 = normal, 1 = check, 2 = checkmate)</p>
	 * @param state - integer
	 */
	public void changeState(int state) {
		this.status = state;
	}
	/**
	 * <p>Returns the status number of the game (if the game is to continue normally or if some major event has taken place)</p>
	 * @return status of the game
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * <p>Changes the turn in a State object</p>
	 * @param b - The Board object in use for the game
	 */
	public void changeTurn(Board b) {
		if (this.turn == 0) {
			b.setTurn(1);
			turn = 1;

		}
		else {
			b.setTurn(0);
			turn = 0;

		}
	}
	/**
	 * <p>Return current player's turn</p>
	 * @return Current player's turn (0 - White, 1 - Black)
	 */
	public int getTurn() {
		return this.turn;
	}
	/**
	 * <p>Ends the game and prints the opposite player's name as winner.</p>
	 * @param s - State Object
	 */
	public void EndGame(State s) {
		if (s.turn == 0) {
			System.out.println("Black wins");
		}
		else {
			System.out.println("White wins");
		}
	}


}

