package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;

public class Queen extends Piece implements java.io.Serializable{

	/**
	 * Create a Queen Piece with a color and type
	 * @param color
	 * @param type
	 */
	public Queen(int color, char type) {
		super(color, type);
	}
	/**
	 * <p>Returns validity of a move made by a Queen type piece</p>
	 * @param from - Space that the Piece is attempting to leave from
	 * @param to - Space that the Piece is attempting to occupy
	 * @param board - Board
	 * @return validity of a move made by a Queen type piece
	 */
	@Override
	public boolean move(Space from, Space to, Board board) {
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		/**
		 * Queen must be moving diagonal or straight line
		 */
		if((Math.abs(colDiff) != Math.abs(rowDiff))&&(!(colDiff != 0 && rowDiff == 0) && !(colDiff == 0 && rowDiff != 0))) {
			return false;
		}
		/**
		 * Ensure no jumping is occurring
		 */

		if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff < 0 && rowDiff > 0) {
			/**
			 * diagonal up right
			 */
			int i = from.getRow()-1;
			int j = from.getCol()+1;
			while(i > to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j++;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff > 0 && rowDiff > 0) {
			/**
			 * diagonal up left
			 */
			int i = from.getRow()-1;
			int j = from.getCol()-1;
			while(i > to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j--;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff > 0 && rowDiff < 0) {
			/**
			 * diagonal down left
			 */
			int i = from.getRow()+1;
			int j = from.getCol()-1;
			while(i < to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j--;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff < 0 && rowDiff < 0) {
			/**
			 * diagonal down right
			 */
			int i = from.getRow()+1;
			int j = from.getCol()+1;
			while(i < to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j++;
			}
		}else if(rowDiff > 0 && colDiff == 0) {
			/**up*/
			for(int i = from.getRow()-1; i>to.getRow(); i--) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff > 0) {
			/**left*/
			for(int i = from.getCol()-1; i>to.getCol(); i--) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff < 0 && colDiff == 0) {
			/**down*/
			for(int i = from.getRow()+1; i<to.getRow(); i++) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff < 0) {
			/**right*/
			for(int i = from.getCol()+1; i<to.getCol(); i++) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else{
			return false;
		}
		
		/**
		 * Ensure target piece is not of same color
		 */
		if(to.getPiece() != null) {
			if(this.color == to.getPiece().getColor()) {
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		/**
		 * Queen must be moving diagonal or straight line
		 */
		if((Math.abs(colDiff) != Math.abs(rowDiff))&&(!(colDiff != 0 && rowDiff == 0) && !(colDiff == 0 && rowDiff != 0))) {
			return false;
		}
		/**
		 * Ensure no jumping is occurring
		 */

		if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff < 0 && rowDiff > 0) {
			/**
			 * diagonal up right
			 */
			int i = from.getRow()-1;
			int j = from.getCol()+1;
			while(i > to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j++;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff > 0 && rowDiff > 0) {
			/**
			 * diagonal up left
			 */
			int i = from.getRow()-1;
			int j = from.getCol()-1;
			while(i > to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j--;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff > 0 && rowDiff < 0) {
			/**
			 * diagonal down left
			 */
			int i = from.getRow()+1;
			int j = from.getCol()-1;
			while(i < to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j--;
			}
		}else if((Math.abs(colDiff)==Math.abs(rowDiff)) && colDiff < 0 && rowDiff < 0) {
			/**
			 * diagonal down right
			 */
			int i = from.getRow()+1;
			int j = from.getCol()+1;
			while(i < to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j++;
			}
		}else if(rowDiff > 0 && colDiff == 0) {
			/**up*/
			for(int i = from.getRow()-1; i>to.getRow(); i--) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff > 0) {
			/**left*/
			for(int i = from.getCol()-1; i>to.getCol(); i--) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff < 0 && colDiff == 0) {
			/**down*/
			for(int i = from.getRow()+1; i<to.getRow(); i++) {
				if(board.getSpaceFromVals(i, from.getCol()).getPiece() != null) {
					return false;
				}
			}
		}else if(rowDiff == 0 && colDiff < 0) {
			/**right*/
			for(int i = from.getCol()+1; i<to.getCol(); i++) {
				if(board.getSpaceFromVals(from.getRow(), i).getPiece() != null) {
					return false;
				}
			}
		}else{
			return false;
		}

		/**
		 * Ensure target piece is not of same color
		 */
		if(to.getPiece() != null) {
			if(this.color == to.getPiece().getColor()) {
				return false;
			}
		}
		return true;
	}
}
