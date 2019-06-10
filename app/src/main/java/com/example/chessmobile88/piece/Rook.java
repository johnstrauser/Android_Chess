package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;

/**
 * <p> Creates a Rook Piece </p>
 * @author John Strauser
 *
 */
public class Rook extends Piece implements java.io.Serializable{
	public int firstMove = 1;
	public Rook(int color, char type) {
		super(color, type);
	}

	/**
	 * Returns validity of a move made by a Rook Piece
	 * @param from - Space that the Piece is attempting to leave from
	 * @param to - Space that the Piece is attempting to occupy
	 * @param board - Board

	 */
	@Override
	public boolean move(Space from, Space to, Board board) {
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		/**check if attempting jump*/
		if(rowDiff != 0 && colDiff != 0) {
			/**
			 * failsafe incase dir isn't set and the above if statements don't return false
			 */
			return false;
		}
		
		else if(rowDiff > 0 && colDiff == 0 ) {
			for(int i = from.getRow()-1; i>to.getRow(); i--) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff < 0) {
			for(int i = from.getCol()+1; i<to.getCol(); i++) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff < 0 && colDiff == 0) {
			for(int i = from.getRow()+1; i<to.getRow(); i++) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff > 0) {
			for(int i = from.getCol()-1; i>to.getCol(); i--) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}
		/**
		 * check that target space is not piece of same color
		 */
		if(to.getPiece() != null) {
			if(this.color == to.getPiece().getColor()) {
				return false;
			}
		}
		firstMove = 0;
		return true;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		/**check if attempting jump*/
		if(rowDiff != 0 && colDiff != 0) {
			/**
			 * failsafe incase dir isn't set and the above if statements don't return false
			 */
			return false;
		}

		else if(rowDiff > 0 && colDiff == 0 ) {
			for(int i = from.getRow()-1; i>to.getRow(); i--) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff < 0) {
			for(int i = from.getCol()+1; i<to.getCol(); i++) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff < 0 && colDiff == 0) {
			for(int i = from.getRow()+1; i<to.getRow(); i++) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff > 0) {
			for(int i = from.getCol()-1; i>to.getCol(); i--) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}
		/**
		 * check that target space is not piece of same color
		 */
		if(to.getPiece() != null) {
			if(this.color == to.getPiece().getColor()) {
				return false;
			}
		}
		return true;
	}
}
