package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;
/**
 *  <p>Approves the moves that King pieces are allowed to make on the chess board, also involved in approving castling</p>
 * @author Joshua Pineda
 *
 */
public class King extends Piece implements java.io.Serializable {
	public int firstMove = 1;
	public King(int color, char type) {
		super(color, type);
	}
	@Override
	public boolean move(Space from, Space to, Board board) {
		//king can only move one space in any direction
		//calc difference in rows
		int rowDiff = Math.abs(from.getRow() - to.getRow());
		//calc difference in cols
		int colDiff = Math.abs(from.getCol() - to.getCol());
		if(rowDiff > 1 || colDiff > 1 ){
			return false;
		}
		
		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		
		firstMove = 0;
		return true;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {

		//king can only move one space in any direction
		//calc difference in rows
		int rowDiff = Math.abs(from.getRow() - to.getRow());
		//calc difference in cols
		int colDiff = Math.abs(from.getCol() - to.getCol());
		if (rowDiff == 0 && colDiff == 2) {
			if (firstMove == 1){
				int realdiff = from.getCol() - to.getCol();
				/*Right-side*/
				if (realdiff == -2) {
					if(board.turn == 0) {
						if (board.WRookR != 1){
							return false;
						}
					}
					else {
						if (board.BRookR != 1){
							return false;
						}
					}
				}
				/*Left-side*/
				else {
					if(board.turn == 0) {
						if (board.WRookL != 1){
							return false;
						}
					}
					else {
						if (board.BRookL != 1){
							return false;
						}
					}
				}
			}
		}else if(rowDiff > 1 || colDiff > 1 ){
			return false;
		}

		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		return true;
	}
	
}
