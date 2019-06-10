package com.example.chessmobile88.piece;
import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;
/**
 *  <p>Approves the moves that knight pieces are allowed to make on the chess board</p>
 * @author John Strauser
 *
 */
public class Knight extends Piece implements java.io.Serializable {

	public Knight(int color, char type) {
		super(color, type);
	}
	@Override
	public boolean move(Space from, Space to, Board board) {
		//knight can only move in an L shape (2 X and 1 Y, or 2 Y and 1 X)
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		//if statements for L shape
		if(!((Math.abs(colDiff)==2&&Math.abs(rowDiff)==1)||(Math.abs(colDiff)==1&&Math.abs(rowDiff)==2))){
			return false;
		}
		//cannot attack piece of same color
		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		//Knight can jump pieces so no need to check here
		return true;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {
		//knight can only move in an L shape (2 X and 1 Y, or 2 Y and 1 X)
		int rowDiff = from.getRow() - to.getRow();
		int colDiff = from.getCol() - to.getCol();
		//if statements for L shape
		if(!((Math.abs(colDiff)==2&&Math.abs(rowDiff)==1)||(Math.abs(colDiff)==1&&Math.abs(rowDiff)==2))){
			return false;
		}
		//cannot attack piece of same color
		if(to.getPiece() != null){
			if(this.color == to.getPiece().getColor()){
				return false;
			}
		}
		//Knight can jump pieces so no need to check here
		return true;
	}
}
