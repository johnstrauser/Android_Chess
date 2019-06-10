package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;

/**
 * Piece is the blueprint for the chess pieces in game.
 * @author Joshua Pineda and John Strauser
 *
 */
public abstract class Piece implements java.io.Serializable{
	protected int color;
	private char type;
	int alive;
	public int firstMove = 1;
	
	/**
	 * Piece() constructor
	 */
	public Piece() {
		
	}
	/**
	 * <p>Initializes a Piece object with a color and a type</p>
	 * @param color
	 * @param type
	 */
	public Piece(int color, char type) {
		this.color = color;
		this.type = type;
		alive = 1;
	}
	/**
	 * 
	 * @return Color of piece
	 */
	public int getColor() {
		return color;
	}
	/**
	 * Sets color of piece
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
	}
	/**
	 * Return type of Piece
	 * @return
	 */
	public char getType() {
		return type;
	}
	/**
	 * Set type of a Piece
	 * @param type
	 */
	public void setType(char type) {
		this.type = type;
	}
	/**
	 * Return validity of a chess move based on the Piece's type
	 * @param from - Space that the Piece is attempting to leave from
	 * @param to - Space that the Piece is attempting to occupy
	 * @param board - Board
	 * @return- validity of a chess move based on the Piece's type
	 */
	public abstract boolean move(Space from, Space to, Board board);
	public abstract boolean checkMove(Space from, Space to, Board board);
	/**
	 * Used to print String representation of a Piece object.
	 */
	public String toString() {
		if(color == 0) {
			return "w"+type+" ";
		}
		return "b"+type+" ";
	}
}
