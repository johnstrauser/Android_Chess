package com.example.chessmobile88.board;

import com.example.chessmobile88.piece.Piece;


/**
 * <p>Representation of a space on the chess board.</p>
 * @author Joshua Pineda and John Strauser
 * 
 */
public class Space implements java.io.Serializable{
	
	private int row;
	private int col;
	/**
	 * The designated piece on a space. 'null' if there is no piece on the space.
	 */
	private Piece p;
	/**
	 * When a player attempts draw or promotion, aux helps handle these functions run. 
	 */
	private String aux;
	

	/**
	 * Creates an empty Space object with no Piece attached.
	 */
	public Space() {
		setRow(-1);
		setCol(-1);
		p = null;
	}
	/**
	 * Creates a Space with a designated position 
	 * @param row - designates the row position of Space
	 * @param col - designates the column position of Space
	 */
	public Space(int row, int col) {
		this.setRow(row);
		this.setCol(col);
		p = null;
	}
	/**
	 * 
	 * <p>Creates a Space with a designated position and specific piece</p>
	 * @param row - designates the row position of Space
	 * @param col - designates the column position of Space
	 * @param p - The piece on the Space
	 */
	public Space(int row, int col, Piece p) {
		this.setRow(row);
		this.setCol(col);
		this.p = p;
	}
	public Space(Space space){
		this.setRow(space.getRow());
		this.setCol(space.getCol());
		this.p=space.getPiece();
	}
	/**
	 * <p>Returns piece on a given space</p>
	 * @return Piece on the given space
	 */
	public Piece getPiece() {
		return p;
	}
	/**
	 * <p>Sets a particular Piece object on a Space object</p>
	 * @param p - the piece to be set
	 */
	public void setPiece(Piece p) {
		this.p = p;
	}
	/**
	 * 
	 * @return the row number (index) of a given Space object
	 */
	public int getRow() {
		return row;
	}
	/**
	 * <p>Sets row position of a given Space object</p>

	 * @param row - Row index
	 */
	public void setRow(int row) {
		this.row = row;
	}
	/**
	 * 
	 * @return column index of a given Space object
	 */
	public int getCol() {
		return col;
	}
	/**
	 * <p>Sets column position of a given Space object</p>

	 * @param col - Column index
	 */
	public void setCol(int col) {
		this.col = col;
	}
	/**
	 * <p>Returns a Space's auxiliary attribute (used in promotion/draws)</p>

	 * @return aux of a given Space object
	 */
	public String getAux() {
		return aux;
	}
	/**
	 * <p>Sets a Space's auxiliary attribute</p>

	 * @param aux - Auxiliary attribute 
	 */
	public void setAux(String aux) {
		this.aux = aux;
	}
	/**
	 * <p>Returns True if Spaces have equal row and column positions</p>

	 * @param target - Space to be compared
	 * @return if the spaces are equal in position
	 */
	public boolean compareTo(Space target) {
		if(this.row == target.getRow() && this.col == target.getCol()){
			return true;
		}
		return false;
	}
	
}
