package com.example.chessmobile88.util;

import com.example.chessmobile88.board.Move;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class fileUtil implements java.io.Serializable{
    private String fileName;
    private Date date;
    ArrayList<Move> moves;

    public fileUtil(String fileName, Date date, ArrayList<Move> moves){
        this.fileName = fileName;
        this.date = date;
        this.moves = moves;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public String toString(){
        return fileName + " | " + date.toString();
    }
}
