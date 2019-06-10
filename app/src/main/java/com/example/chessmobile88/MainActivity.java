package com.example.chessmobile88;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chessmobile88.board.Move;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    public void onClickPlay(View v){

        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }
    public void onClickExit(View v){
        finish();
        System.exit(0);
    }
    public void onClickHistory(View v){
        Intent intent = new Intent(this,RecordedListActivity.class);
        startActivity(intent);
    }
}
