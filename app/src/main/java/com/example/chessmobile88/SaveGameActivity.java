package com.example.chessmobile88;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chessmobile88.board.Move;
import com.example.chessmobile88.board.listHelper;
import com.example.chessmobile88.util.fileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Where the user is prompted to be able to save the game, also where the result of the game is shown</p>
 */
public class SaveGameActivity extends Activity {
    public Button saveBtn, cancelBtn;
    public EditText gameName;
    public ArrayList<Move> moves;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Intent intent = getIntent();
        gameName = (EditText)findViewById(R.id.save_field);

        String type = intent.getStringExtra("type");
        int turn = intent.getIntExtra("turn",0);
        listHelper helper =(listHelper) intent.getSerializableExtra("list");
        moves = helper.getList();
        Toast.makeText(this, "moves.size=" + moves.size(), Toast.LENGTH_LONG).show();

        String player = (turn == 0)? "White":"Black";
        String opp = (turn == 0)? "Black":"White";
        TextView header = (TextView)findViewById(R.id.header_id);
        String textShown;

        if(type.equals("resign")) {
            textShown = player + " resigned. " + opp + " wins.";
        }else if (type.equals("win")){
            textShown = player + " wins.";
        }else{
            textShown = "Draw";
        }

        header.setText(textShown);


    }
    public void actionSave(View view){
        //TODO: Actually save here
        String fileName = gameName.getText().toString();
        if(!fileName.matches("[a-zA-Z0-9]+")){
            //TODO: ERROR
            Toast.makeText(this, "File name must only be alphanumeric characters", Toast.LENGTH_LONG).show();
        }else{
            fileName += ".ser";
            String[] takenNames = fileList();
            int validName = 1;
            for(int i=0; i<takenNames.length; i++){
                if(takenNames[i].equals(fileName)){
                    validName = 0;
                }
            }
            if(validName == 0){
                //TODO: print error toast
                Toast.makeText(this, "File name is already taken", Toast.LENGTH_LONG).show();
            }else {
                FileOutputStream fileStream = null;
                ObjectOutputStream out = null;
                try {
                    fileStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    out = new ObjectOutputStream(fileStream);

                    fileUtil file = new fileUtil(fileName,Calendar.getInstance().getTime(),moves);
                    out.writeObject(file);

                    Toast.makeText(this, "Saved to " + fileName, Toast.LENGTH_LONG).show();

                    out.close();
                    fileStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }

    }
    public void actionCancel(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

}
