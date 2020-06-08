package com.HATFmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

import co.mobiwise.library.MusicPlayerView;

public class PlayActivity extends AppCompatActivity {

    public static int flag = 0;
    private MusicPlayerView mpv;
    private ImageView speed_down, speed_up, last_song, next_song, back_play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Serializable s = getIntent().getSerializableExtra("song");

        mpv = (MusicPlayerView) findViewById(R.id.mpv);
        mpv.setButtonColor(Color.DKGRAY);
        mpv.setCoverDrawable(R.drawable.album1);
        mpv.setProgressEmptyColor(Color.GRAY);
        mpv.setProgressLoadedColor(Color.BLUE);
        mpv.setTimeColor(Color.WHITE);

        mpv.setOnClickListener(v -> {
            if (flag == 0) {
                // 第一次单击触发的事件
                mpv.start();
                flag = 1;
            } else {
                // 第二次单击button改变触发的事件
                mpv.stop();
                flag = 0;
            }
        });

        speed_down = findViewById(R.id.speed_down);
        speed_up = findViewById(R.id.speed_up);
        last_song = findViewById(R.id.last_song);
        next_song = findViewById(R.id.next_song);
        back_play = findViewById(R.id.back_play);
        //加速进度
        speed_down.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                mpv.setVelocity(1);
                return true;
            }
        });

        back_play.setOnClickListener(v -> {
            Toast.makeText(PlayActivity.this, "", Toast.LENGTH_LONG)
                    .show();
            Intent intent = new Intent(PlayActivity.this, MainActivity.class);
            PlayActivity.this.startActivity(intent);
        });

    }
}
