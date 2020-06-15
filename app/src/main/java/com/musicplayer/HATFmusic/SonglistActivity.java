package com.musicplayer.HATFmusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HATFmusic.musicplayer.activity.PlayActivity;

public class SonglistActivity extends AppCompatActivity implements SongRvAdapter.SongClickListener {
    private RecyclerView songRv;
    private SongRvAdapter rvAdapter;
    private SongLab lab = SongLab.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);

        songRv = findViewById(R.id.song_rv);
        rvAdapter = new SongRvAdapter(this, this);
        this.songRv.setAdapter(rvAdapter);
        this.songRv.setLayoutManager(new LinearLayoutManager(this));

        initData();

    }

    private void initData() {
         Handler handler = new Handler() {
            //按快捷键Ctrl o
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 1) {
                    rvAdapter.notifyDataSetChanged();
                }
            }
        };
         lab.getData(handler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //把主线程的handler传递给子线程使用
    }

    @Override
    public void onSongClick(int position) {

    }
}

