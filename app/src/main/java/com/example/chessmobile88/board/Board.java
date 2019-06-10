package com.example.chessmobile88.board;


import com.example.chessmobile88.piece.Bishop;
import com.example.chessmobile88.piece.King;
import com.example.chessmobile88.piece.Knight;
import com.example.chessmobile88.piece.Pawn;
import com.example.chessmobile88.piece.Piece;
import com.example.chessmobile88.piece.Queen;
import com.example.chessmobile88.piece.Rook;



/**
 * <p>Where the rendering of the board will take place and management of Spaces.
 * Board mainly consists of a 8x8 array of Space Objects</p>
 * <p>Also takes care of special events in the game like Castling and Promotions.</p>
 * 
 * @author Joshua Pineda and John Strauser
 *
 */
public class Board implements java.io.Serializable {
	/**
	 * Rook and Castle variables for use in Castling approval
	 */
	public int WCastle=1, BCastle=1;
	public int WRookL=1,WRookR=1,BRookL=1,BRookR=1;
	Space[][] board = new Space[8][8];
	/**
	 * Allow access to color during Move() of pieces
	 */
	public int turn = 0;
	public void setTurn(int turn) {
		this.turn = turn;
	}


	/**
	 * Need to know position of kings in order to check if they are in check
	 * */
	Space WKingspace = new Space(7,4);
	Space BKingspace = new Space(0,4);
	
	/**
	 * <p>Original Board Constructor that creates a Board object 
	 * with an 8x8 array of Space objects
	 * Pieces are then populated into their starting Space objects</p>
	 */
	public Board() {
		/**
		 * initialize all spaces to null
		 */
		for(int i=0; i<8; i++) {
			for(int j=0; j<8;j++) {
				board[i][j] = new Space(i,j);
			}
		}
		/**
		 * Piece Population
		 */
		Rook WRook = new Rook(0,'R');
		Piece WKnight = new Knight(0,'N');
		Piece WBishop =  new Bishop(0,'B');
		Piece WQueen = new Queen(0,'Q');
		King WKing = new King(0,'K');
		
		Rook BRook = new Rook(1,'R');
		Piece BKnight = new Knight(1,'N');
		Piece BBishop =  new Bishop(1,'B');
		Piece BQueen = new Queen(1,'Q');
		King BKing = new King(1,'K');
		
		
		for (int i = 0; i < 8; i++) {
			Piece WPawn = new Pawn(0,'p');
			Piece BPawn = new Pawn(1,'p');
			board[6][i].setPiece(WPawn);
			board[1][i].setPiece(BPawn);
			switch(i) {
				case 0:
				case 7:
					board[0][i].setPiece(BRook);
					board[7][i].setPiece(WRook);
					break;
				case 1:
				case 6:
					board[0][i].setPiece(BKnight);
					board[7][i].setPiece(WKnight);
					break;
				case 2:
				case 5:
					board[0][i].setPiece(BBishop);
					board[7][i].setPiece(WBishop);
					break;
				case 3:
					board[0][i].setPiece(BQueen);
					board[7][i].setPiece(WQueen);
					break;
				case 4:
					board[0][i].setPiece(BKing);
					board[7][i].setPiece(WKing);
					break;
			}
			
		}
	}
	/**
	 * <p>constructor that creates a board object from another Board Object</p>
	 * @param board
	 */
	public Board(Board board){

		for(int i=0; i<8; i++) {
			for(int j=0; j<8;j++) {
				this.board[i][j] = new Space(i,j);
			}
		}
		copy(board);
	}
	/**
	 * <p> Creates a copy Board object of another Board object</p>
	 * @param board - Board Object
	 */
	public void copy(Board board){
		/**
		 * copies @param board to this.board
		 */
		for(int i=0; i<8; i++) {
			for(int j=0; j<8;j++) {
				this.board[i][j].setPiece(board.board[i][j].getPiece());
			}
		}
	}
	/**
	 * <p>Takes in a Board object, changes the 'src' Space object's piece to null, 
	 * moves the piece to the 'dest' Space object on the board.</p>
	 * @param b - Board Object
	 * @param src - Space Object
	 * @param dest - Space Object
	 */
	public void changeSpace(Board b, Space src, Space dest) {
	
		b.board[dest.getRow()][dest.getCol()].setPiece(src.getPiece());
		b.board[src.getRow()][src.getCol()].setPiece(null);
		
	}
	/**
	 * <p>Checks validity of a promotion move</p>

	 * @param b - Board
	 * @param dest - Space user is trying to move to
	 * @return validity of a promotion move
	 */
	public boolean Promotion(Board b, Space dest) {
		/**
		 * At this point, we already know its a valid move, if pawn is at the end of the board
		 * */
		/**
		 * Check Top of board for white*/
		if (b.turn == 0) {
			if (dest.getRow() == 0)
				return true;
		}
		/**
		 * Bottom of board for black*/
		else {
			if (dest.getRow() == 7)
				return true;
		}
		return false;
	}
	/**
	 * <p>Promotes a pawn to a chosen piece type</p>
	 * @param b - Board
	 * @param dest - Space user is trying to move to 
	 * @param aux - The piece type they are trying to promote to
	 */
	public void PromotePiece(Board b, Space dest,Space aux) {
		Piece promo = null;
		switch (aux.getAux()) {
			case "N":
				promo = new Knight(b.turn,'N');
				break;
			case "Q":
				promo = new Queen(b.turn,'Q');
				break;
			case "R":
				promo = new Rook(b.turn,'R');
				break;
			case "B":
				promo = new Bishop(b.turn,'B');
				break;
			default:
				promo = new Queen(b.turn,'Q');
				break;
		}
		b.getSpace(dest).setPiece(promo);
		
	}
	/**
	 * <p>Checks if a player's rook has moved, part of validation of a castle move</p>
	 * @param b
	 * @param src
	 * @param color
	 */
	public void CheckCastlingStatus(Board b,Space src,int color) {
		if(src.getCol() == 0){
			if(src.getPiece() != null){
				if(src.getPiece().getType() != 'R'){
					if(color == 0){
						b.WRookL=0;
					}else{
						b.BRookL=0;
					}
				}
			}else{
				if(color == 0){
					b.WRookL=0;
				}else{
					b.BRookL=0;
				}
			}
		}else if(src.getCol() == 7){
			if(src.getPiece() != null){
				if(src.getPiece().getType() != 'R'){
					if(color == 0){
						b.WRookR=0;
					}else{
						b.BRookR=0;
					}
				}
			}else{
				if(color == 0){
					b.WRookR=0;
				}else{
					b.BRookR=0;
				}
			}
			
		}
	}
	
	/**
	 * <p>Checks the validity of a Castle Move</p>
	 * @param b-  the board
	 * @param src - the King space
	 * @param dest - space where King wants to castle
	 * @return validity of the castle move being attempted
	 */
	public boolean CastleMove(Board b, Space src, Space dest,int color) {
		/**Check if King is moving within row*/
		int rowDiff = Math.abs(src.getRow() - dest.getRow());
		if (rowDiff != 0) {
			return false;
		}
		/**Check if King trying to move 2 spaces*/
		int colDiff = src.getCol() - dest.getCol();
		if (Math.abs(colDiff) != 2){
			return false;
		}
		Space s;
		/**Left-side Castling */
		if(colDiff > 0) {
			for(int i = src.getCol()-1; i>0; i--) {
				if(b.getSpaceFromVals(src.getRow(), i).getPiece() != null) {
					return false;
				}
			}
			s = b.getSpaceFromVals(src.getRow(), 0);
			if (s.getPiece() == null){
				return false;
			}
			if (s.getPiece().getType() != 'R'){
				return false;
			}
			
			/**Check if Rook on the left has moved*/
			if (color == 0) {
				if (b.WRookL != 1){
					return false;
				}
			}
			else {
				if (b.BRookL != 1){
					return false;
				}
				
			}
			

			
			
		}
		/**Right-side castling - check if spaces on right are free*/
		else if(colDiff < 0){
			for(int i = src.getCol()+1; i<7; i++) {
				if(b.getSpaceFromVals(src.getRow(), i).getPiece() != null) {
					return false;
				}
			}
			s = board[src.getRow()][7];
			if (s.getPiece() == null) {
				return false;

			}
			if (s.getPiece().getType() != 'R') {
				return false;

			}
			if (s.getPiece().firstMove != 1) {
				return false;

			}
			/**Check if Rook on the right has moved*/
			if (color == 0) {
				if (b.WRookR != 1){
					return false;
				}
					
			}
			else {
				if (b.BRookR != 1){
					return false;
				}
				
			}

		}
		if (!src.getPiece().move(src,dest,b))
			return false;
		
		return true;
	}
	/**
	 * <p>Moves the King and the Rook during a Castle move</p>
	 * @param b - Board
	 * @param src - Space object the Piece is coming from
	 * @param dest - Space object the Piece wishes to go
	 */
	public void Castle(Board b,Space src, Space dest) {
		
		changeSpace(b,src,dest);
		Space rsrc, rdest;
		
		int colDiff = src.getCol() - dest.getCol();

		/**King moves left, Left Castle*/
		if (colDiff > 0) {
			rsrc = board[src.getRow()][0];
			rdest= board[src.getRow()][3];
			changeSpace(b,rsrc,rdest);
		}
		/**King moves right, Right Castle*/
		else {
			rsrc = board[src.getRow()][7];
			rdest = board[src.getRow()][5];
			changeSpace(b,rsrc,rdest);
		}
	}
	/**
	 * <p>Returns a Space object on the Board given a Space Object with row and column numbers</p>
	 * @param s
	 * @return
	 */
	public Space getSpace(Space s) {
		int row = s.getRow();
		int col = s.getCol();
		return board[row][col];
	}
	/**
	 * <p>Obtains a Space object from a Board object with row and column numbers</p>
	 * @param row
	 * @param col
	 * @return
	 */
	public Space getSpaceFromVals(int row, int col){
		return board[row][col];
	}
	/**
	 * <p>Prints the board and Piece representations on the board</p>
	 * @author John Strauser
	 */
	public void printBoard() {
	
		/**
		 * Every subsequent row until final row
		 * first character is row number
		 * next 8 are from spaces
		 * final character is row number
		 */
		for(int i=0; i<8; i++) {
			for(int j=0; j<10; j++) {
				if(j == 9) {
					System.out.print(8-i+" ");
					if(j == 9) {
						System.out.println();
					}
					
				}

				else {
					if (j!=0) {
						Space temp = board[i][j-1];
						if(temp.getPiece() != null) {
							System.out.print(temp.getPiece());
						}else {
							if((i+j)%2 == 0) {
								System.out.print("   ");
							}else {
								System.out.print("## ");
							}
						}
					}

				}
			}
		}
		/**
		 * Final row for column letters
		 */
		System.out.println(" a  b  c  d  e  f  g  h");
	}
}
