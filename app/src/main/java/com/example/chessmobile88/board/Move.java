package com.example.chessmobile88.board;

public class Move implements java.io.Serializable {
    private Space fromSpace;
    private Space toSpace;
    private Board boardAfterMove;

    //This constructor should be used for making the first move in an array
    public Move(){
        fromSpace = null;
        toSpace = null;
        boardAfterMove = new Board();
    }
    //This constructor should be used for making each move after the first in an array
    public Move(Space from, Space to, Board board){
        fromSpace = new Space(from.getRow(),from.getCol(),from.getPiece());
        toSpace = new Space(to.getRow(),to.getCol(),to.getPiece());
        boardAfterMove = new Board(board);
    }

    public Space getFromSpace() {
        return fromSpace;
    }

    public Space getToSpace() {
        return toSpace;
    }

    public Board getBoardAfterMove() {
        return boardAfterMove;
    }
    public void setBoardAfterMove(Board board) {
        boardAfterMove = board;
    }
    public String toString(){
        String output = "";
        char[] cols = {'a','b','c','d','e','f','g','h'};
        if(fromSpace.getPiece() == null){
            return "from is null";
        }
        if(fromSpace.getPiece().getColor() == 0){
            output = "White moved the ";
        }else{
            output = "Black moved the ";
        }
        if(fromSpace.getPiece().getType()=='p'){
            output += "Pawn from ";
        }else if(fromSpace.getPiece().getType()=='R'){
            output += "Rook from ";
        }else if(fromSpace.getPiece().getType()=='N'){
            output += "Knight from ";
        }else if(fromSpace.getPiece().getType()=='B'){
            output += "Bishop from ";
        }else if(fromSpace.getPiece().getType()=='Q'){
            output += "Queen from ";
        }else if(fromSpace.getPiece().getType()=='K'){
            output += "King from ";
        }
        output += cols[fromSpace.getCol()]+""+fromSpace.getRow()+" to "+cols[toSpace.getCol()]+""+toSpace.getRow();


        return output;
    }
}
