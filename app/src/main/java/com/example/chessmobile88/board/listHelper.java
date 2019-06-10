package com.example.chessmobile88.board;

import java.io.Serializable;
import java.util.ArrayList;

public class listHelper implements Serializable {

    private ArrayList<Move> movesList;
    public listHelper(ArrayList<Move> list){
        this.movesList = list;
    }
    public ArrayList<Move> getList(){
        return movesList;
    }
}
