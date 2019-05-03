package com.example.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MeaningActivity extends AppCompatActivity {
    private TextView tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            String word = bundle.getString("word");
            String meaning = bundle.getString("meaning");

            tvShow = findViewById(R.id.tvShow);
            tvShow.setText(word+" -> "+meaning);
        }else{
            Toast.makeText(this,"No meaning", Toast.LENGTH_SHORT).show();
        }

    }
}
