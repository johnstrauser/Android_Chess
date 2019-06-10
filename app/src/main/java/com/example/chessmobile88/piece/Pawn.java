package com.example.chessmobile88.piece;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;

public class Pawn extends Piece implements java.io.Serializable{
	private int firstMove = 1;
	public Pawn(int color, char type) {
		super(color, type);
	}
	
	/**
	 * @author John Strauser
	 * @param board - board used to check if pieces are in path of pawn double-jump
	 * @param from - the space the pawn is coming from
	 * @param color - Needed for checking direction of the jump
	 * @return Validity of the jump: allowed (true) or not allowed (false)
	 */
	boolean Jump(Board board,Space from, int color) {
		Space jump;
		if (color == 0) {
			/**
			 * if moving two spaces, must ensure no jumping is occurring
			 */
			jump = board.getSpaceFromVals(from.getRow()-1,from.getCol());
			if(jump.getPiece() != null){
				/**
				 * there exists a piece in the space that would be jumped
				 */
				return false;
			}else{
				/**
				 * the space being jumped is empty (good to go)
				 */
				firstMove = 0;
				return true;
			}
		}
		else {
			/**
			 * if moving two spaces, must ensure no jumping is occurring
			 */
			jump = board.getSpaceFromVals(from.getRow()+1,from.getCol());
			if(jump.getPiece() != null){
				/**
				 * there exists a piece in the space that would be jumped
				 */
				return false;
			}else{
				/**
				 * the space being jumped is empty (good to go)
				 */
				firstMove = 0;
				return true;
			}
			
		}
		
	}
	boolean CheckJump(Board board,Space from, int color) {
		Space jump;
		if (color == 0) {
			/**
			 * if moving two spaces, must ensure no jumping is occurring
			 */
			jump = board.getSpaceFromVals(from.getRow()-1,from.getCol());
			if(jump.getPiece() != null){
				/**
				 * there exists a piece in the space that would be jumped
				 */
				return false;
			}else{
				/**
				 * the space being jumped is empty (good to go)
				 */
				return true;
			}
		}
		else {
			/**
			 * if moving two spaces, must ensure no jumping is occurring
			 */
			jump = board.getSpaceFromVals(from.getRow()+1,from.getCol());
			if(jump.getPiece() != null){
				/**
				 * there exists a piece in the space that would be jumped
				 */
				return false;
			}else{
				/**
				 * the space being jumped is empty (good to go)
				 */
				return true;
			}

		}

	}
	/**
	 *  <p>Approves the moves that pawn pieces (under normal conditions) are allowed to make on the chess board.
	 *  Enpassant is handled by the board.</p>
	 *  @author John Strauser
	 */
	@Override
	public boolean move(Space from, Space to, Board board) {
		if(to.getPiece() != null){
			/**attacking, can only move diagonal one tile if piece in to is other player's*/
			int colDiff = from.getCol() - to.getCol();
			if(Math.abs(colDiff) == 1){
				if(to.getPiece().getColor() != from.getPiece().getColor()){
					if(from.getPiece().getColor() == 0){
						if(from.getRow()- 1 == to.getRow()){
							firstMove = 0;
							return true;
						}
					}else{
						if(from.getRow()+ 1 == to.getRow()){
							firstMove = 0;
							return true;
						}
					}
				}
			}
		}else{
			if(from.getCol() == to.getCol()){
				if(firstMove == 1){
					int dist = from.getRow() - to.getRow();
					if(color == 0){
						if(dist <= 2 && dist > 0){
							if(dist == 2){
								return Jump(board, from, 0);
							}else{
								firstMove = 0;
								return true;
							}
						}
					}else{
						if(dist >= -2 && dist < 0){
							if(dist == -2){
								return Jump(board,from,1);
							}else{
								firstMove = 0;
								return true;
							}
						}
					}
				}else{
					int dist = from.getRow() - to.getRow();
					if(color == 0){
						if(dist == 1){
							return true;
						}
					}else{
						if(dist == -1){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	@Override
	public boolean checkMove(Space from, Space to, Board board) {
		if(to.getPiece() != null){
			/**attacking, can only move diagonal one tile if piece in to is other player's*/
			int colDiff = from.getCol() - to.getCol();
			if(Math.abs(colDiff) == 1){
				if(to.getPiece().getColor() != from.getPiece().getColor()){
					if(from.getPiece().getColor() == 0){
						if(from.getRow()- 1 == to.getRow()){
							return true;
						}
					}else{
						if(from.getRow()+ 1 == to.getRow()){
							return true;
						}
					}
				}
			}
		}else{
			if(from.getCol() == to.getCol()){
				if(firstMove == 1){
					int dist = from.getRow() - to.getRow();
					if(color == 0){
						if(dist <= 2 && dist > 0){
							if(dist == 2){
								return CheckJump(board, from, 0);
							}else{
								return true;
							}
						}
					}else{
						if(dist >= -2 && dist < 0){
							if(dist == -2){
								return CheckJump(board,from,1);
							}else{
								return true;
							}
						}
					}
				}else{
					int dist = from.getRow() - to.getRow();
					if(color == 0){
						if(dist == 1){
							return true;
						}
					}else{
						if(dist == -1){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
