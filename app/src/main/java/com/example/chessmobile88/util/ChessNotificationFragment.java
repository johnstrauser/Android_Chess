package com.example.chessmobile88.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ChessNotificationFragment extends DialogFragment {

    public static final String MESSAGE_KEY = "message_key";
    public static ChessConfirmationFragment newInstance(String message) {
        ChessConfirmationFragment frag = new ChessConfirmationFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_KEY, message);
        frag.setArguments(args);
        return frag;
    }
}
