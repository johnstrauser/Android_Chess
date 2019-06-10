package com.example.chessmobile88;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chessmobile88.R;
import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Move;
import com.example.chessmobile88.board.Space;
import com.example.chessmobile88.board.listHelper;
import com.example.chessmobile88.piece.Bishop;
import com.example.chessmobile88.piece.Knight;
import com.example.chessmobile88.piece.Piece;
import com.example.chessmobile88.piece.Queen;
import com.example.chessmobile88.piece.Rook;
import com.example.chessmobile88.util.ChessConfirmationFragment;
import com.example.chessmobile88.util.ChessErrorFragment;

import java.util.ArrayList;
import java.util.Random;

import static com.example.chessmobile88.board.Chess.getKing;
import static com.example.chessmobile88.board.Chess.inCheck;
import static com.example.chessmobile88.board.Chess.moveOutOfCheck;

public class GameActivity extends FragmentActivity {
    //UI
    public static Board board;
    public static Board prevBoard;
    public static SquareAdapter squareAdapter;
    public Button resignBtn, drawBtn, aiBtn;
    public ImageButton undoBtn;
    public static TextView turnlabel;
    //Functionality
    public static ArrayList<Move> moves;
    public static Space prevFrom;
    public static Piece prevFpiece;
    public static Space prevTo;
    public static int prevTurn;
    public static int undone = 0;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GridView boardView = (GridView) findViewById(R.id.gridView);
        board = new Board();
        squareAdapter = new SquareAdapter(this,0);
        squareAdapter.setParent(boardView);
        squareAdapter.setBoard(board);
        boardView.setAdapter(squareAdapter);

        mContext = this;

        turnlabel = findViewById(R.id.turn_label);
        turnlabel.setText("White's turn");


        undoBtn = findViewById(R.id.undoMove);
        undoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(undone == 0){
                    if (prevBoard != null){
                        board.copy(prevBoard);
                        board.turn = (board.turn==0)? 1:0;
                        squareAdapter.setBoard(board);
                        //TODO: unhighlight the piece that goes back


                        if(prevFrom.getPiece()!= null){
                            if(prevFrom.getPiece().getType() == 'p'){
                                if(prevFrom.getPiece().getColor() == 0){
                                    if(prevFrom.getRow() == 6){
                                        prevFrom.getPiece().firstMove = 1;
                                    }
                                }else{
                                    if(prevFrom.getRow() == 1){
                                        prevFrom.getPiece().firstMove = 1;
                                    }
                                }
                            }
                        }
                        //make sure turn label goes undo too
                        String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                        turnlabel.setText(currLabel);
                        undone = 1;

                        //Remove last move from moves
                        if(moves.size()>1){
                            moves.remove(moves.size()-1);
                        }
                    }
                }

            }
        });

        resignBtn = findViewById(R.id.resignButton);
        resignBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showConfirmationDialog(board.turn,1, new listHelper(moves));
            }
        });

        drawBtn = findViewById(R.id.drawButton);
        drawBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showConfirmationDialog(board.turn,0, new listHelper(moves));
            }
        });
        aiBtn = findViewById(R.id.aiButton);
        aiBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: Pick a random piece of current player (?)
                //TODO: execute move with that piece, update adapter
                int currentTurn = board.turn;
                //Collect list of spaces that have a piece of the current turns color
                ArrayList<Space> colorPieces = new ArrayList<>();
                int i,j;
                for(i=0; i<8; i++){
                    for(j=0; j<8; j++){
                        if(board.getSpaceFromVals(i,j).getPiece() != null){
                            if(board.getSpaceFromVals(i,j).getPiece().getColor()==currentTurn){
                                //Add to list of spaces
                                colorPieces.add(board.getSpaceFromVals(i,j));
                            }
                        }
                    }
                }
                //Remove pieces that don't have a valid move possible
                int k;
                for(k=0; k<colorPieces.size(); k++){
                    Space currSpace = colorPieces.get(k);
                    int valid = 0;
                    for(i=0; i<8; i++){
                        for(j=0; j<8; j++){
                            if (valid == 0) {
                                if (currSpace.getPiece().checkMove(currSpace, board.getSpaceFromVals(i, j), board) == true) {
                                    valid = 1;
                                }
                            }
                        }
                    }
                    if(valid == 0){
                        colorPieces.remove(k);
                        k--;
                    }
                }
                if(colorPieces.size() > 0){
                    //Select a random piece
                    int randomInt = new Random().nextInt(colorPieces.size());
                    //Perform first move possible for that piece
                    Space currSpace = colorPieces.get(randomInt);
                    for(i=0; i<8; i++){
                        for(j=0; j<8; j++){
                            if (currSpace.getPiece().move(currSpace, board.getSpaceFromVals(i, j), board) == true) {
                                prevBoard = new Board();
                                prevBoard.copy(board);
                                prevBoard.turn = board.turn;
                                board.turn = (board.turn == 0) ? 1:0;

                                prevFrom = new Space(currSpace);
                                prevFpiece = currSpace.getPiece();
                                prevTo = new Space(board.getSpaceFromVals(i, j));
                                prevTurn = prevBoard.turn;

                                board.changeSpace(board,currSpace,board.getSpaceFromVals(i, j));
                                moves.add(new Move(prevFrom,prevTo,board));
                                squareAdapter.setBoard(board);
                                undone=0;

                                //Switch turn label
                                String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                                turnlabel.setText(currLabel);

                                promotionCheck(board);
                                //TODO - perform check and checkmate checks
                                checkAndCheckMate(board);

                                return;
                            }
                        }
                    }
                }


            }
        });

        //Add "empty" move to list of moves
        moves  = new ArrayList<>();
        moves.add(new Move());
        undone =0;
    }

   /* public void onClickUndo(){
        this.board = new Board(squareAdapter.prevBoard);
        SquareAdapter newAdapter = new SquareAdapter(GameActivity.this.getApplicationContext(),this.board);
        GridView boardView = (GridView) findViewById(R.id.gridView);
        boardView.setAdapter(newAdapter);
    }*/

    public static void gameLoop(PieceButton selPiece){
        int currentTurn = board.turn;
        Space selSpace = squareAdapter.getPieceViewSpace(selPiece,board);
        //If you click the same piece twice
        if (selPiece.selected){
            selPiece.selected = false;
            squareAdapter.firstSel = false;
            squareAdapter.firstPiece = null;
            selPiece.setImageResource(squareAdapter.getPieceImage(selSpace,false));

        }else{
            //current clicked piece button has NOT been clicked
            Piece temp = squareAdapter.getPieceViewSpace(selPiece,board).getPiece();
            if (!squareAdapter.firstSel){
                //check if we are clicking an empty square
                //Case 1- Clicking a non-null first piece
                if (temp != null){
                    if (temp.getColor() == currentTurn){
                        selPiece.selected = true;
                        squareAdapter.firstSel = true;
                        squareAdapter.firstPiece = selPiece;
                        selPiece.setImageResource(squareAdapter.getPieceImage(selSpace,true));
                    }

                }

            }else{

                //Case 2- Clicking a new second piece, KNOW - first piece selected (firstSel == true)
                Space fromSpace = squareAdapter.getPieceViewSpace(squareAdapter.firstPiece,board);
                Piece fromPiece = fromSpace.getPiece();
                if(fromPiece != null){
                    if(fromPiece.getType() == 'K'){

                        int diffking = fromSpace.getCol() - selSpace.getCol();
                        int present = 0;
                        if (diffking == 2){

                            //moving to left
                            for (int i = fromSpace.getCol()-1; i > 0;i--){
                                Space tempspace = board.getSpaceFromVals(fromSpace.getRow(),i);
                                if (tempspace.getPiece() != null){
                                    present = 1;
                                }
                            }
                            if (present == 0){
                                Space roo = board.getSpaceFromVals(fromSpace.getRow(),0);
                                if (roo.getPiece() != null){
                                    if (roo.getPiece().getType() == 'R'){
                                        prevBoard = new Board();
                                        prevBoard.copy(board);
                                        prevBoard.turn = board.turn;
                                        board.turn = (board.turn == 0) ? 1:0;

                                        Space moveFrom = new Space(fromSpace);
                                        prevFrom = fromSpace;
                                        prevFpiece = fromPiece;
                                        Space moveTo = new Space(selSpace);
                                        prevTo = selSpace;
                                        prevTurn = prevBoard.turn;

                                        board.changeSpace(board,fromSpace,selSpace);
                                        board.changeSpace(board,roo,board.getSpaceFromVals(fromSpace.getRow(),3));
                                        squareAdapter.setBoard(board);
                                        moves.add(new Move(moveFrom,moveTo,board));
                                        undone = 0;

                                        String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                                        turnlabel.setText(currLabel);
                                        //TODO: Promotion
                                        promotionCheck(board);

                                        //TODO - perform check and checkmate checks
                                        checkAndCheckMate(board);
                                    }
                                }
                            }

                        }else if (diffking == -2){
                            //moving to right
                            for (int i = fromSpace.getCol()+1; i < 7;i++){
                                Space tempspace = board.getSpaceFromVals(fromSpace.getRow(),i);
                                if (tempspace.getPiece() != null){
                                    present = 1;
                                }
                            }
                            if (present == 0){
                                Space roo = board.getSpaceFromVals(fromSpace.getRow(),7);
                                if (roo.getPiece() != null){
                                    if (roo.getPiece().getType() == 'R'){
                                        prevBoard = new Board();
                                        prevBoard.copy(board);
                                        prevBoard.turn = board.turn;
                                        board.turn = (board.turn == 0) ? 1:0;

                                        prevFrom = new Space(fromSpace);
                                        prevFpiece = fromPiece;
                                        prevTo = new Space(selSpace);
                                        prevTurn = prevBoard.turn;

                                        board.changeSpace(board,fromSpace,selSpace);
                                        board.changeSpace(board,roo,board.getSpaceFromVals(fromSpace.getRow(),5));
                                        squareAdapter.setBoard(board);
                                        moves.add(new Move(prevFrom,prevTo,board));
                                        undone = 0;

                                        String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                                        turnlabel.setText(currLabel);
                                        //TODO: Promotion
                                        promotionCheck(board);

                                        //TODO - perform check and checkmate checks
                                        checkAndCheckMate(board);
                                    }
                                }
                            }
                        }else if(fromPiece.move(fromSpace,selSpace,board)){
                            if (currentTurn != prevTurn){
                                //we can take away their first move privilege from their piece
                                //CURRENTLY NOT WORKING
                                prevFpiece.firstMove = 0;
                            }
                            //TODO: Prevent pawns from moving 2 spaces twice
                            //changes turn
                            prevBoard = new Board();
                            prevBoard.copy(board);
                            prevBoard.turn = board.turn;
                            board.turn = (board.turn == 0) ? 1:0;

                            prevFrom = new Space(fromSpace);
                            prevFpiece = fromPiece;
                            prevTo = new Space(selSpace);
                            prevTurn = prevBoard.turn;

                            //Set destination image
                            //selPiece.setImageResource(getPieceImage(fromSpace,false));
                            //Making the first PieceButton blank with a null Space object.
                            //squareAdapter.firstPiece.setImageResource(getPieceImage(new Space(),false));
                            selPiece.setImageResource(squareAdapter.getPieceImage(fromSpace,false));
                            board.changeSpace(board,fromSpace,selSpace);
                            moves.add(new Move(prevFrom,prevTo,board));
                            squareAdapter.firstPiece.setImageResource(squareAdapter.getPieceImage(new Space(),false));
                            squareAdapter.firstSel = false;
                            undone = 0;


                            //Switch turn label
                            String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                            turnlabel.setText(currLabel);
                            //TODO: Promotion
                            promotionCheck(board);

                            //TODO - perform check and checkmate checks
                            checkAndCheckMate(board);
                        }else{
                            Toast.makeText(mContext, "Bad move, try again.", Toast.LENGTH_LONG).show();
                            squareAdapter.firstPiece.setImageResource(squareAdapter.getPieceImage(fromSpace,false));
                            squareAdapter.firstPiece.selected = false;
                            squareAdapter.firstSel = false;
                        }
                    }else if(fromPiece.move(fromSpace,selSpace,board)){
                        if (currentTurn != prevTurn){
                            //we can take away their first move privilege from their piece
                            //CURRENTLY NOT WORKING
                            prevFpiece.firstMove = 0;
                        }
                        //TODO: Prevent pawns from moving 2 spaces twice
                        //changes turn
                        prevBoard = new Board();
                        prevBoard.copy(board);
                        prevBoard.turn = board.turn;
                        board.turn = (board.turn == 0) ? 1:0;

                        prevFrom = new Space(fromSpace);
                        prevFpiece = fromPiece;
                        prevTo = new Space(selSpace);
                        prevTurn = prevBoard.turn;

                        //Set destination image
                        //selPiece.setImageResource(getPieceImage(fromSpace,false));
                        //Making the first PieceButton blank with a null Space object.
                        //squareAdapter.firstPiece.setImageResource(getPieceImage(new Space(),false));
                        selPiece.setImageResource(squareAdapter.getPieceImage(fromSpace,false));
                        board.changeSpace(board,fromSpace,selSpace);
                        moves.add(new Move(prevFrom,prevTo,board));
                        squareAdapter.firstPiece.setImageResource(squareAdapter.getPieceImage(new Space(),false));
                        squareAdapter.firstSel = false;
                        undone = 0;


                        //Switch turn label
                        String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
                        turnlabel.setText(currLabel);
                        //TODO: Promotion
                        promotionCheck(board);

                        //TODO - perform check and checkmate checks
                        checkAndCheckMate(board);
                    }else{
                        Toast.makeText(mContext, "Bad move, try again.", Toast.LENGTH_LONG).show();
                        squareAdapter.firstPiece.setImageResource(squareAdapter.getPieceImage(fromSpace,false));
                        squareAdapter.firstPiece.selected = false;
                        squareAdapter.firstSel = false;
                    }
                }else{
                    Toast.makeText(mContext, "Bad move, try again.", Toast.LENGTH_LONG).show();
                    squareAdapter.firstPiece.setImageResource(squareAdapter.getPieceImage(fromSpace,false));
                    squareAdapter.firstPiece.selected = false;
                    squareAdapter.firstSel = false;
                }
            }


        }
    }
    public static void checkAndCheckMate(Board board){
        Space wKing = getKing(board, 0);
        Space bKing = getKing(board, 1);

        boolean wInCheck = false;
        boolean bInCheck = false;
        int checkError = 0;

        if(bKing == null || wKing == null){
            //TODO remove this before final submission, only for error checking
            //System.out.println("Weird error, a king cannot be found on the board");
        }else{
            wInCheck = inCheck(board, wKing);
            bInCheck = inCheck(board, bKing);
        }
        /**
         * if white's turn and the king is in check after their turn, invalid move
         */
        if(wInCheck && prevBoard.turn==0){
            //TODO - Error toast here
            Toast.makeText(mContext, "Move cannot result in player being in check.", Toast.LENGTH_LONG).show();
            checkError = 1;
            board.copy(prevBoard);
            board.turn = (board.turn==0)? 1:0;
            squareAdapter.setBoard(board);

            //make sure turn label goes undo too
            String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
            turnlabel.setText(currLabel);

            //Remove last move from moves
            if(moves.size()>1){
                moves.remove(moves.size()-1);
            }
        }
        /**
         * if black's turn and the king is in check after their turn, invalid move
         */
        if(bInCheck && prevBoard.turn==1){
            Toast.makeText(mContext, "Move cannot result in player being in check.", Toast.LENGTH_LONG).show();
            checkError = 1;
            board.copy(prevBoard);
            board.turn = (board.turn==0)? 1:0;
            squareAdapter.setBoard(board);

            //make sure turn label goes undo too
            String currLabel = (board.turn == 0)? "White's turn":"Black's turn";
            turnlabel.setText(currLabel);

            //Remove last move from moves
            if(moves.size()>1){
                moves.remove(moves.size()-1);
            }
        }

        if(checkError == 0){
            if(wInCheck && prevBoard.turn==1){
                /**
                 * check for checkmate
                 */
                if(!moveOutOfCheck(board, wKing)){
                    /**
                     * no move out of check can be made, checkmate
                     */
                    Intent intent = new Intent(mContext, SaveGameActivity.class);
                    intent.putExtra("type","win");
                    intent.putExtra("turn",prevBoard.turn);
                    intent.putExtra("list",new listHelper(moves));
                    mContext.startActivity(intent);
                }
                else
                {
                    Toast.makeText(mContext, "Check", Toast.LENGTH_LONG).show();

                }
            }
            if(bInCheck && prevBoard.turn==0){
                /**
                 * check for checkmate
                 */
                if(!moveOutOfCheck(board, bKing)){
                    /**
                     * no move out of check can be made, checkmate
                     */
                    Intent intent = new Intent(mContext, SaveGameActivity.class);
                    intent.putExtra("type","win");
                    intent.putExtra("turn",prevBoard.turn);
                    intent.putExtra("list",new listHelper(moves));
                    mContext.startActivity(intent);
                }
                else {
                    Toast.makeText(mContext, "Check", Toast.LENGTH_LONG).show();

                }
            }


        }
    }
    public static void promotionCheck(Board board){
        //Check the top and bottom of the board
        //If it contains a pawn, change to queen
        String[] options = {"Queen", "Rook", "Knight", "Bishop"};
        for(int i=0; i<8; i+=7){
            for(int j=0; j<8; j++){
                Space space = board.getSpaceFromVals(i,j);
                if(space.getPiece() != null){
                    if(space.getPiece().getType() == 'p'){
                        if(i==0 && space.getPiece().getColor() == 0){
                            //TODO - Allow player to select piece to promote to
                            final int i2 = i;
                            final int j2 = j;
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Pick a piece to promote to");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // the user clicked on colors[which]
                                    performPromotion(board,i2,j2,options[which]);

                                }
                            });
                            builder.show();
                        }else if(i==7 && space.getPiece().getColor() == 1){
                            //TODO - Allow player to select piece to promote to
                            final int i2 = i;
                            final int j2 = j;
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Pick a piece to promote to");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // the user clicked on colors[which]
                                    performPromotion(board,i2,j2,options[which]);

                                }
                            });
                            builder.show();
                        }
                    }
                }
            }
        }


    }
    public static void performPromotion(Board board, int i, int j, String piece){
        Space space = board.getSpaceFromVals(i,j);
        int color = space.getPiece().getColor();
        char type = piece.charAt(0);
        if(type == 'K'){
            type = 'N';
            space.setPiece(new Knight(color,type));
        }else if(type == 'Q'){
            space.setPiece(new Queen(color,type));
        }else if(type == 'R'){
            space.setPiece(new Rook(color,type));
        }else if(type == 'B'){
            space.setPiece(new Bishop(color,type));
        }

        squareAdapter.setBoard(board);

        Move move = moves.get(moves.size()-1);
        move.setBoardAfterMove(new Board(board));
        moves.set(moves.size()-1,move);
    }
    /**
     *
     * @param currentTurn - the current turn
     * @param type - Type of confirmation dialog
     * <p>Shows a confirmation dialog based on the type of dialog requested (draw or resign) and the current player</p>
     */
    private void showConfirmationDialog(int currentTurn,int type, listHelper helper) {
        FragmentManager fm = getSupportFragmentManager();
        //Toast.makeText(this,"fromSpace = "+ moves.get(1).getFromSpace().getCol()+","+moves.get(1).getFromSpace().getRow()+" toSpace = "+moves.get(1).getToSpace().getCol()+","+moves.get(1).getToSpace().getRow(),Toast.LENGTH_LONG).show();

        ChessConfirmationFragment alertDialog =
                ChessConfirmationFragment.newInstance("Confirmation",currentTurn,type,helper);
        String color = (currentTurn == 0)? "White":"Black";
        alertDialog.show(fm, "confirmation");
    }
    /*public static void showErrorDialog(String message){
        FragmentManager fm = ChessErrorFragment.getMyContext().getSupportFragmentManager();
        ChessErrorFragment alertDialog =
                ChessErrorFragment.newInstance(message);
        alertDialog.show(fm,"err");


    }*/








}
