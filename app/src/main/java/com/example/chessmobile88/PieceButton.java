package com.example.chessmobile88;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.example.chessmobile88.piece.Piece;

public class PieceButton extends android.support.v7.widget.AppCompatImageButton {
    public boolean selected = false;
    public Piece piece;

    public PieceButton(Context context) {
        super(context);
    }

    public PieceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PieceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    /*public void copyPieceButton(PieceButton otherPiece){
        this.piece.setColor(otherPiece.piece.getColor());
        this.piece.setType(otherPiece.piece.getType());
    }*/
}