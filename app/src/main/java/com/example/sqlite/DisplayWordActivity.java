package com.example.sqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import helper.MyHelper;
import model.Word;

public class DisplayWordActivity extends AppCompatActivity {

    private ListView lstWords;
    private Button btnSearch;
    private EditText etSearch;
    public  String phrase = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_word);

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        lstWords = findViewById(R.id.lstWords);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 phrase = etSearch.getText().toString();
                 LoadWords(phrase);
            }
        });
        LoadWords(phrase);

    }

    public void LoadWords(String phrase){
        final MyHelper myHelper = new MyHelper(this);
        final SQLiteDatabase sqLiteDatabase = myHelper.getWritableDatabase();

        List<Word> wordList = new ArrayList<>();

        if(phrase.equals("")){
            wordList = myHelper.GetAllWords(sqLiteDatabase);
        }
        else {
            wordList = myHelper.GetWordByName(phrase, sqLiteDatabase);
        }

        final HashMap<String,String> hashMap = new HashMap<>();

        for (int i=0; i<wordList.size(); i++){
            hashMap.put(wordList.get(i).getWord(), wordList.get(i).getMeaning());
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(hashMap.keySet())
        );

        lstWords.setAdapter(stringArrayAdapter);

        lstWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = parent.getItemAtPosition(position).toString();
                String meaning = hashMap.get(word);

                Intent intent = new Intent(DisplayWordActivity.this,MeaningActivity.class);
                intent.putExtra("meaning",meaning);
                intent.putExtra("word",word);
                startActivity(intent);
            }
        });
    }


}
