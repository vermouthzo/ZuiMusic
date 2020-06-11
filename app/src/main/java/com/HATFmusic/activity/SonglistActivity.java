package com.HATFmusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.HATFmusic.R;
import com.HATFmusic.Song;
import com.HATFmusic.SongLab;
import com.HATFmusic.adapter.SongRvAdapter;

public class SonglistActivity extends AppCompatActivity {
    private RecyclerView songRv;
    private SongRvAdapter rvAdapter;
    private SongLab lab = SongLab.getInstance();

    private Handler handler = new Handler() {
        //按快捷键Ctrl o
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                rvAdapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);

        this.songRv = findViewById(R.id.song_rv);
        rvAdapter = new SongRvAdapter(this, p -> {
            //跳转到新界面，使用意图Intent
            Intent intent = new Intent(SonglistActivity.this, PlayActivity.class);
            //通过位置p得到当前歌曲song
            Song c = lab.getSong(p);
            intent.putExtra("song", c);

            startActivity(intent);
        });
        this.songRv.setAdapter(rvAdapter);
        this.songRv.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onResume() {
        super.onResume();
        //把主线程的handler传递给子线程使用
        lab.getData(handler);
    }
}

