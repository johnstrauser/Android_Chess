package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;
/**
 * <p>Approves the moves that bishop pieces are allowed to make on the chess board</p>
 * @author John Strauser
 *
 */
public class Bishop extends Piece implements java.io.Serializable {

	public Bishop(int color, char type) {
		super(color, type);
	}
	@Override
	public boolean move(Space from, Space to, Board board) {
		//Bishop can only move diagonally
		//This means absolute value of difference between columns and rows must be equivalent
		int rowDiff = from.getRow()-to.getRow();
		int colDiff = from.getCol()-to.getCol();
		if(Math.abs(colDiff) != Math.abs(rowDiff)){
			return false;
		}
		
		//ensure jumping is not occurring
		if(rowDiff > 0 && colDiff > 0){
			int i = from.getRow()-1;
			int j = from.getCol()-1;
			while(i > to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j--;
			}
		}else if(rowDiff > 0 && colDiff < 0){
			int i = from.getRow()-1;
			int j = from.getCol()+1;
			while(i > to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j++;
			}
		}else if(rowDiff < 0 && colDiff > 0){
			int i = from.getRow()+1;
			int j = from.getCol()-1;
			while(i < to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j--;
			}
		}else if(rowDiff < 0 && colDiff < 0){
			int i = from.getRow()+1;
			int j = from.getCol()+1;
			while(i < to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j++;
			}
		}else{
			return false;
		}
		
		//make sure piece to be attacked is not the same color
		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {
		//Bishop can only move diagonally
		//This means absolute value of difference between columns and rows must be equivalent
		int rowDiff = from.getRow()-to.getRow();
		int colDiff = from.getCol()-to.getCol();
		if(Math.abs(colDiff) != Math.abs(rowDiff)){
			return false;
		}

		//ensure jumping is not occurring
		if(rowDiff > 0 && colDiff > 0){
			int i = from.getRow()-1;
			int j = from.getCol()-1;
			while(i > to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j--;
			}
		}else if(rowDiff > 0 && colDiff < 0){
			int i = from.getRow()-1;
			int j = from.getCol()+1;
			while(i > to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i--;
				j++;
			}
		}else if(rowDiff < 0 && colDiff > 0){
			int i = from.getRow()+1;
			int j = from.getCol()-1;
			while(i < to.getRow() && j > to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j--;
			}
		}else if(rowDiff < 0 && colDiff < 0){
			int i = from.getRow()+1;
			int j = from.getCol()+1;
			while(i < to.getRow() && j < to.getCol()){
				if(board.getSpaceFromVals(i, j).getPiece()!=null){
					return false;
				}
				i++;
				j++;
			}
		}else{
			return false;
		}

		//make sure piece to be attacked is not the same color
		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		return true;
	}
}
