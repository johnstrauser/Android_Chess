package com.example.chessmobile88;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Move;
import com.example.chessmobile88.board.Space;
import com.example.chessmobile88.util.fileUtil;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class showGame extends AppCompatActivity {
    ArrayList<Move> moves;
    int currIndex = 0;
    Board board;
    GridView boardView;
    SquareAdapter squareAdapter;
    TextView moveText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_replay);
        //setup the board view
        boardView = (GridView) findViewById(R.id.gridViewReplay);
        board = new Board();
        squareAdapter = new SquareAdapter(this,1);
        squareAdapter.setParent(boardView);
        squareAdapter.setBoard(board);
        boardView.setAdapter(squareAdapter);

        // get the name and detail from bundle
        Bundle bundle = getIntent().getExtras();
        String fileName = bundle.getString(RecordedListActivity.FILE_NAME);
        //String fileName = "test.ser";
        //TODO
        //Read moves from fileName
        try {
            FileInputStream file = openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            fileUtil fileutil = (fileUtil)in.readObject();
            moves = fileutil.getMoves();

            Toast.makeText(this,"opened save "+fileName,Toast.LENGTH_LONG).show();

            in.close();
            file.close();
        }catch(EOFException e) {
            //not an issue
            //just that there is no more data in the file to read
        }catch(Exception e) {
            e.printStackTrace();
        }

        //call nextMove()
        //nextMove();

        // get the name and detail view objects
        moveText = findViewById(R.id.textReplay);



    }
    public void nextMoveHandler(View v){
        nextMove();
    }
    public void prevMoveHandler(View v){
        prevMove();
    }
    public void nextMove(){
        //TODO
        //If currIndex < moves.size-1
        //increment currIndex
        //Set board to moves.get(currIndex)
        //Set text to match new spaces for move
        if(currIndex < moves.size()-1){
            currIndex++;
            Move move = moves.get(currIndex);

            //Update the look of the board
            squareAdapter.setBoard(move.getBoardAfterMove());
            boardView.setAdapter(squareAdapter);

            setText(move);
        }else{
            Toast.makeText(this,"No more moves to display",Toast.LENGTH_LONG).show();
        }
        return;
    }
    public void prevMove(){
        //TODO
        //If currIndex > 0
        //decrement currIndex
        //Set board to moves.get(currIndex)
        //Set text to match new spaces for move
        if(currIndex > 0){
            currIndex--;
            Move move = moves.get(currIndex);

            //Update the look of the board
            squareAdapter.setBoard(move.getBoardAfterMove());
            boardView.setAdapter(squareAdapter);

            setText(move);
        }else{
            Toast.makeText(this,"Already at move 0",Toast.LENGTH_LONG).show();
        }
        return;
    }
    public void setText(Move move){
        if(move.getFromSpace() == null || move.getToSpace() == null){
            moveText.setText("Initial Board");
        }else{
            moveText.setText(moves.get(currIndex).toString());
        }
    }
}