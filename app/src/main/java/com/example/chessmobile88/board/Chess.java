package com.example.chessmobile88.board;

import com.example.chessmobile88.state.State;

import java.util.ArrayList;
import java.util.List;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;


/**
 * <p>Where the chess application will be run.</p>
 * <p>Infinite loop of Player taking a turn, checking if the move is valid,
 * checking if they are attempting a special move (such as en passant, castling, or promotion)
 * and executing the move given the correct conditions are met</p>
 * <p>Also included within the Chess App is logic for Check and Checkmate</p>
 * <p>When a player is in check, they are not allowed to make moves that are going to keep them in check next turn
 * and if they have no more moves to play, then the game is over and declared Checkmate.</p>
 * 
 * @author Joshua Pineda and John Strauser
 *
 */
public class Chess {
	/**
	 * Helper function mainly for producing an error message and reprinting the Board
	 * @return an integer indicating to start the loop again on the player's turn.
	 */
	public static int error() {
		System.out.println("Illegal move, try again");
		return 1;
	}
	/**
	 * @author John Strauser
	 * @param board - Board
	 * @param king - Space that contains a King type piece
	 * @return - Whether or not the King Space is in Check
	 */
	public static boolean inCheck(Board board, Space king){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Space curr = board.getSpaceFromVals(i, j);
				if(curr.getPiece() != null){
					if(curr.getPiece().getColor() != king.getPiece().getColor()) {
						if(curr.getPiece().move(curr, king, board)){
							return true;
						}
					}
					
				}
			}
		}
		return false;
	}
	/**
	 * <p>Finds the Space that contains the King</p>
	 * @author John Strauser
	 * @param board - Board
	 * @param color - Current turn
	 * @return Space that contains the King
	 */
	public static Space getKing(Board board, int color){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Space curr = board.getSpaceFromVals(i, j);
				if(curr.getPiece() != null){
					if(curr.getPiece().getColor() == color && curr.getPiece().getType() == 'K'){
						return curr;
					}
				}
			}
		}
		return null;
	}
	/**
	 * <p>Looks for a move out of Check 
	 * (moving another piece to get in the way of Check or moving the King piece out of check</p>
	 * @param board
	 * @author John Strauser
	 * @param king
	 * @return
	 */
	public static boolean moveOutOfCheck(Board board, Space king){
		Board temp = new Board(board);
		int color = king.getPiece().getColor();
		/**check every piece that is the same color as king
		* if making a move with that piece can take king out of check, return true
		* else return false
		* use temp as a temporary board to restore old board after move
		*/
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				Space curr = board.getSpaceFromVals(i, j);
				if(curr.getPiece() != null){
					if(curr.getPiece().getColor() == color){
						for(int k=0; k<8; k++){
							for(int l=0; l<8; l++){
								Space target = board.getSpaceFromVals(k, l);
								if(curr.getPiece().move(curr, target, board)){
									/**
									 * make the move and check if king is still in check
									 */
									board.changeSpace(board, curr, target);
									Space tempKing = getKing(board,color);
									if(tempKing != null){
										if(!inCheck(board,tempKing)){
											/**
											 * if the king is no longer in check, then there exists a move that can take the king out of check and this is not checkmate
											 */
											board.copy(temp);
											return true;
										}else{
											/**
											 * otherwise undo the move and continue checking
											 */
											board.copy(temp);
										}
									}else{
										board.copy(temp);
									}
								}
							}
						}
					}
				}
			}
		}
		/**
		 * if this statement is reached it means there are no moves that can take the king out of check and thus a checkmate has occurred
		 */
		return false;
	}
	
	/**
	 * <p>Main flow of chess game takes place</p> 
	 * @param args
	 * @author Joshua Pineda and John Strauser
	 */
	/*public static void main(String[] args) {
		Board board = new Board();
		Board prev = new Board(board);
		State s = new State();
		int redo = 0;
		String move = null;
		boolean enpassant = false;
		Space enpassantSpace = null;
		*//**
		 * While the game is not in checkmate, continue
		 *//*
		while (s.getStatus() == 0 || s.getStatus() == 1) {
			if (redo == 0) {
				*//**
				 * Check state here 
				 * *//*
				board.printBoard();
			}

			*//**
			 * Get a valid input from user
			 *//*
			do {move = s.turn();}
			while (!ChessTools.checkMove(move));
			
			if (move.equals("resign")) {
				s.EndGame(s);
				System.exit(0);
			}

			*//**
			 * Convert input to Spaces
			 *//*
			Space[] mv = ChessTools.convert(move);
			
			*//**
			 * Check for a draw
			 *//*
			if (mv[2] != null) {
				if (mv[2] != null && mv[2].getAux().equals("draw?")) {
					System.out.println("draw");
					System.exit(0);
				}
				*//**Otherwise it is an attempted promotion or an illegal move*//*
				*//**Make sure piece being moved is pawn, and promotion type is valid*//*
				if(!ChessTools.isValidPromotion(board.getSpace(mv[0]),mv[2])) {
					redo = error();
					continue;
				}
			}
			*//**
			 * Check what piece is on first space
			 *//*
			Space src = board.getSpace(mv[0]);
			if (src.getPiece() == null) {redo = error();} 
			
			*//**Check for correct piece color and do standard piece protocol*//*
			else if (src.getPiece().getColor() == s.getTurn()) {
				Space dest = board.getSpace(mv[1]);
				*//** If we move a rook, check/update castling status *//*
				if(src.getPiece().getColor() == 0){
					board.CheckCastlingStatus(board, board.getSpaceFromVals(7, 0), s.getTurn());
					board.CheckCastlingStatus(board, board.getSpaceFromVals(7, 7), s.getTurn());
				}else{
					board.CheckCastlingStatus(board, board.getSpaceFromVals(0, 0), s.getTurn());
					board.CheckCastlingStatus(board, board.getSpaceFromVals(0, 7), s.getTurn());
				}
				
				*//** If we are moving a king, check castle everytime *//*
				if (src.getPiece().getType() == 'K') {
					if (board.CastleMove(board, src, dest, s.getTurn())) {
						prev.copy(board);
						board.Castle(board, src, dest);
						redo = 0;
					} 
					else if (src.getPiece().move(src, dest, board)) {
						*//**
						 * Change the board
						 *//*
						prev.copy(board);
						board.changeSpace(board, src, dest);
						redo = 0;
					} 
					else {
						redo = error();
					}
				}

				*//**
				 * Verify with piece whether move is valid
				 *//*
				else if (src.getPiece().move(src, dest, board)) {
					*//**
					 * check for enpassant
					 *//*
					if (src.getPiece().getType() == 'p') {
						if (Math.abs(src.getRow() - dest.getRow()) == 2) {
							enpassant = true;
						} 
						else {
							enpassant = false;
							enpassantSpace = null;
						}

					}
					else {
						enpassant = false;
						enpassantSpace = null;
					}

					
					if (mv[2] != null && src.getPiece().getType() == 'p') {
						if (board.Promotion(board,dest)) {
							prev.copy(board);
							board.changeSpace(board, src, dest);
							board.PromotePiece(board,dest,mv[2]);
						}
					}
					else {
						prev.copy(board);
						board.changeSpace(board, src, dest);
						redo = 0;
						if (enpassant) {
							enpassantSpace = board.getSpace(dest);
						}
					}

				} 
				else if (enpassant && src.getPiece().getType() == 'p' && dest.getPiece() == null) {
					*//**
					 * enpassant occurs when a pawn attacks "behind" a pawn that just made its first move
					 *//*
					int rowDiff = Math.abs(src.getRow() - dest.getRow());
					int colDiff = Math.abs(src.getCol() - dest.getCol());

					if (rowDiff == 1 && colDiff == 1) {
						// black attacking white via enpassant
						Space target = board.getSpaceFromVals(src.getRow(), dest.getCol());
						if (target.compareTo(enpassantSpace)) {
							prev.copy(board);
							target.setPiece(null);
							board.changeSpace(board, src, dest);
							redo = 0;
							enpassant = false;
							enpassantSpace = null;
						} else {
							redo = error();
						}
					} else {
						redo = error();
					}

				} 
				else {
					redo = error();
				}
			}
			else {
				redo = error();
			}
			
			*//**
			 * if redo = 0 here that means the move was valid and the turn has been changed
			 * check for check and checkmate here
			 *//*
			if(redo == 0){
				Space wKing = getKing(board, 0);
				Space bKing = getKing(board, 1);
				
				boolean wInCheck = false;
				boolean bInCheck = false;
				
				if(bKing == null || wKing == null){
					System.out.println("Weird error, a king cannot be found on the board");
				}else{
					wInCheck = inCheck(board, wKing);
					bInCheck = inCheck(board, bKing);
				}
				*//**
				 * if white's turn and the king is in check after their turn, invalid move
				 *//*
				if(wInCheck && s.getTurn()==0){
					redo = error();
					board.copy(prev);
				}
				*//**
				 * if black's turn and the king is in check after their turn, invalid move
				 *//*
				if(bInCheck && s.getTurn()==1){
					redo = error();
					board.copy(prev);
				}
				
				if(redo == 0){
					if(wInCheck && s.getTurn()==1){
						*//**
						 * check for checkmate
						 *//*
						if(!moveOutOfCheck(board, wKing)){
							*//**
							 * no move out of check can be made, checkmate
							 *//*
							System.out.println("Checkmate.");
							s.EndGame(s);
							s.changeState(2);
						}
						else
						{
							System.out.println("Check");
							s.changeState(1);
						}
					}
					if(bInCheck && s.getTurn()==0){
						*//**
						 * check for checkmate
						 *//*
						if(!moveOutOfCheck(board, bKing)){
							*//**
							 * sno move out of check can be made, checkmate
							 *//*
							System.out.println("Checkmate.");
							s.EndGame(s);
							s.changeState(2);
						}
						else {
							System.out.println("Check");
							s.changeState(1);
						}
					}
					
					s.changeTurn(board);
				}
			}
		}

	}*/

}
