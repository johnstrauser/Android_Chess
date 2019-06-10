package com.example.chessmobile88;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.chessmobile88.board.Board;
import com.example.chessmobile88.board.Space;
import com.example.chessmobile88.piece.Piece;
import com.example.chessmobile88.util.ChessErrorFragment;


public class SquareAdapter extends BaseAdapter{

    private Context mContext;
    private GridView parent;
    private Board board;

    private boolean error = false;
    private int count = 64;
    private int type;



    private Integer[] bgImages = {R.drawable.ic_basesquare, R.drawable.ic_basesquare2};
    private Integer[] white = {R.drawable.king_w, R.drawable.queen_w, R.drawable.rook_w, R.drawable.bishop_w, R.drawable.knight_w, R.drawable.pawn_w,
            R.drawable.king_w_s, R.drawable.queen_w_s, R.drawable.rook_w_s, R.drawable.bishop_w_s, R.drawable.knight_w_s, R.drawable.pawn_w_s};
    private Integer[] black = {R.drawable.king_b, R.drawable.queen_b, R.drawable.rook_b, R.drawable.bishop_b, R.drawable.knight_b, R.drawable.pawn_b,
            R.drawable.king_b_s, R.drawable.queen_b_s, R.drawable.rook_b_s, R.drawable.bishop_b_s, R.drawable.knight_b_s, R.drawable.pawn_b_s};
    private Integer[] blank = {R.drawable.blank, R.drawable.blank_s};

    public boolean firstSel = false;
    public PieceButton firstPiece = null;


    public PieceButton pFrom;
    public PieceButton pTo;
    @Override
    public int getCount() {
        return count;
    }

    public SquareAdapter(Context context, int type) {
        this.mContext = context;
        this.type = type;
        this.board = new Board();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int parentWidth = parent.getMeasuredWidth();
        int squareWidth = parentWidth/8;
        ImageView imageView;
        FrameLayout squareFrameView;
        if (convertView == null) {
            //Allow custom frame layout to be used in square.xml
            LayoutInflater layoutInflater = ((Activity)mContext).getLayoutInflater();
            squareFrameView = (FrameLayout) layoutInflater.inflate(R.layout.square,this.parent,false);
            ViewGroup.LayoutParams containerParams = squareFrameView.getLayoutParams();

            if(containerParams == null) {
                containerParams = new ViewGroup.LayoutParams(squareWidth, squareWidth);
            }
            containerParams.width = squareWidth;
            containerParams.height = squareWidth;

            android.support.v7.widget.AppCompatImageButton background =  squareFrameView.findViewById(R.id.square_background);
            background.setImageResource(bgImages[(position+position/8)%2]);

            PieceButton pieceView = (PieceButton) squareFrameView.findViewById(R.id.piece);
            pieceView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            pieceView.setPadding(0, 0, 0, 0);
            pieceView.setTag(position);

            /*Log.d("POSITION","Position = " + position);
            Log.d("ROW","Row = " + position/8);
            Log.d("COL","Col = " + position%8);*/

            Space currSpace = this.board.getSpaceFromVals(position/8,position%8);
            pieceView.setImageResource(getPieceImage(currSpace,false));

            if(type == 0){
                pieceView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        SquareAdapter adapter = SquareAdapter.this;
                        PieceButton selPiece = (PieceButton) v;
                        GameActivity.gameLoop(selPiece);
                    }
                });
            }

            squareFrameView.setLayoutParams(containerParams);


            return squareFrameView;
        }else {
            squareFrameView = (FrameLayout)convertView;
            ViewGroup.LayoutParams containerParams = convertView.getLayoutParams();
            containerParams.width = squareWidth;
            containerParams.height = squareWidth;
            squareFrameView.setLayoutParams(containerParams);
            PieceButton pieceView = (PieceButton)squareFrameView.findViewById(R.id.piece);
            Space pSpace = getPieceViewSpace(pieceView,board);
            pieceView.setImageResource(getPieceImage(pSpace,(pieceView.selected? true:false)));

        }

        return squareFrameView;
    }

    /**
     *
     * @param selPiece - a PieceButton object
     * @return - The space associated with this PieceButton
     */
    public Space getPieceViewSpace(PieceButton selPiece,Board board){
        int selPosition = (Integer)selPiece.getTag();
        Space selSpace = board.getSpaceFromVals(selPosition/8,selPosition%8);
        return selSpace;
    }

    /**
     *
     * @param space - Space object on the Board
     * @param selected - whether or not we are clicking the space
     * @return - An image of the piece on that space on the current Board (if there is one)
     */
    public int getPieceImage(Space space,boolean selected){
        if (space.getPiece() == null){
            return blank[0];
        }
        char piecetype = space.getPiece().getType();
        int color = space.getPiece().getColor();
        int aux = 0;
        if (selected){
            aux = 6;
        }
        //If white
        if (color == 0){
            switch(piecetype){
                case 'K': return white[0 + aux];
                case 'Q': return white[1 + aux];
                case 'R': return white[2 + aux];
                case 'B': return white[3 + aux];
                case 'N': return white[4 + aux];
                case 'p': return white[5 + aux];
                default: return blank[0];
            }
        }else{
            switch(piecetype){
                case 'K': return black[0 + aux];
                case 'Q': return black[1 + aux];
                case 'R': return black[2 + aux];
                case 'B': return black[3 + aux];
                case 'N': return black[4 + aux];
                case 'p': return black[5 + aux];
                default: return blank[0];
            }

        }

    }
    /*
        FUNCTION NOT WORKING:
        Reason: .getDialog() returns null when a new DialogFragment is created
        Solution: idk yet, so close
     */

    public void setParent(ViewGroup parent) {
        this.parent = (GridView)parent;
        this.parent.invalidateViews();
    }
    public void setBoard(Board currboard){

        this.board = new Board(currboard);
        this.parent.invalidateViews();
    }
    public Board getBoard(){return this.board;}


}
