package com.HATFmusic;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button InternetList;
    private Button LocalList;



    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        LocalList = findViewById(R.id.LocalList);
        InternetList = findViewById(R.id.InternetList);

        LocalList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocalmusicActivity.class);
            MainActivity.this.startActivity(intent);
        });
        InternetList.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SonglistActivity.class);
            MainActivity.this.startActivity(intent);
        });
    }
}
