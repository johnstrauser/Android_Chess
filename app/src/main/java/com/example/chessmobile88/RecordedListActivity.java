package com.example.chessmobile88;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.chessmobile88.board.Move;
import com.example.chessmobile88.util.fileUtil;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class RecordedListActivity extends AppCompatActivity {
    public ListView files_list;
    String[] fileArray;
    ArrayList<fileUtil> utilList = new ArrayList<>();
    ArrayAdapter<fileUtil> adapter;
    public static final String FILE_NAME = "file_name";
    int allowClick = 1;
    int sortName = 0;
    int sortDate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordedlist);
        files_list = findViewById(R.id.files_list);

        //TODO - to test showGame uncomment this line and comment the line below it
        //testArray = new String[]{"test1","test2","test3","test4","test5"};
        fileArray = fileList();
        if(fileArray.length == 0){
            fileArray = new String[]{"No Save Files Exist"};
            allowClick = 0;
        }
        for(int i=0; i<fileArray.length; i++){
            String fileName = fileArray[i];
            //Read fileUtil from fileName
            try {
                FileInputStream file = openFileInput(fileName);
                ObjectInputStream in = new ObjectInputStream(file);

                utilList.add((fileUtil)in.readObject());

                in.close();
                file.close();
            }catch(EOFException e) {
                //not an issue
                //just that there is no more data in the file to read
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        adapter = new ArrayAdapter<>(this,R.layout.activity_listview,utilList);


        files_list.setAdapter(adapter);

        if(allowClick==1){
            files_list.setOnItemClickListener(
                    (p,v,pos,id) -> showGame(pos));
        }

    }
    private void showGame(int pos) {
        Bundle bundle = new Bundle();
        //bundle.putString(FILE_NAME,testArray[pos]);
        bundle.putString(FILE_NAME,utilList.get(pos).getFileName());
        Intent intent = new Intent(this, showGame.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void sortName(View v){
        Comparator<fileUtil> fileSorter;
        if(sortName == 1){
            sortName = 2;
            sortDate = 0;
            fileSorter = new Comparator<fileUtil>() {
                @Override
                public int compare(fileUtil o1, fileUtil o2) {
                    return o2.getFileName().compareTo(o1.getFileName());
                }
            };
        }else{
            sortName = 1;
            sortDate = 0;
            fileSorter = new Comparator<fileUtil>() {
                @Override
                public int compare(fileUtil o1, fileUtil o2) {
                    return o1.getFileName().compareTo(o2.getFileName());
                }
            };
        }
        utilList.sort(fileSorter);
        adapter = new ArrayAdapter<>(this,R.layout.activity_listview,utilList);
        files_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void sortDate(View v){
        Comparator<fileUtil> fileSorter;
        if(sortDate == 1){
            sortName = 0;
            sortDate = 2;
            fileSorter = new Comparator<fileUtil>() {
                @Override
                public int compare(fileUtil o1, fileUtil o2) {
                    return o2.getDate().compareTo(o1.getDate());
                }
            };
        }else{
            sortName = 0;
            sortDate = 1;
            fileSorter = new Comparator<fileUtil>() {
                @Override
                public int compare(fileUtil o1, fileUtil o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            };
        }
        utilList.sort(fileSorter);
        adapter = new ArrayAdapter<>(this,R.layout.activity_listview,utilList);
        files_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
