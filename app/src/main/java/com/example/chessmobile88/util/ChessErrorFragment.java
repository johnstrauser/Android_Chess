package com.example.chessmobile88.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.chessmobile88.R;

public class ChessErrorFragment extends DialogFragment {

    private FragmentActivity myContext;
    public static final String MESSAGE_KEY = "message_key";

    public static ChessErrorFragment newInstance(String message) {
        ChessErrorFragment frag = new ChessErrorFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE_KEY);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }


    public FragmentActivity getMyContext() {
        return myContext;
    }
}
