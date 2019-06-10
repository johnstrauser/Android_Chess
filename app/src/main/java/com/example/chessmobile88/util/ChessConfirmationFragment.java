package com.example.chessmobile88.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.chessmobile88.GameActivity;
import com.example.chessmobile88.SaveGameActivity;
import com.example.chessmobile88.board.Move;
import com.example.chessmobile88.board.listHelper;

import java.util.ArrayList;


public class ChessConfirmationFragment extends DialogFragment {
    public static final String TITLE_KEY = "title_key";
    public static final String TURN_KEY = "turn_key";
    public static final String TYPE_KEY = "type_key";

    public static ChessConfirmationFragment newInstance(String title, int turn, int type, listHelper helper) {
        ChessConfirmationFragment frag = new ChessConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(TITLE_KEY, title);
        args.putInt(TURN_KEY,turn);
        args.putInt(TYPE_KEY,type);
        args.putSerializable("list_helper",helper);
        frag.setArguments(args);
        return frag;
    }

    /**
     *
     * @param savedInstanceState - bundle
     * @return a dialog box confirming the current player wants to either resign or draw
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(TITLE_KEY);
        final int turn = getArguments().getInt(TURN_KEY);
        int type = getArguments().getInt(TYPE_KEY);
        final String dialogType;
        String color = (turn == 0)? "White":"Black";
        if (type == 1){
            dialogType = "resign";
        }else{
            dialogType = "draw";
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Are you sure you want to " + dialogType + " " + color + "?");
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), SaveGameActivity.class);
                intent.putExtra("type",dialogType);
                intent.putExtra("turn",turn);
                intent.putExtra("list",getArguments().getSerializable("list_helper"));
                startActivity(intent);

            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        return alertDialogBuilder.create();
    }


}
