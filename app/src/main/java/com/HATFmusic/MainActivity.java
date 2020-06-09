package com.HATFmusic;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView lastIv,playIv,nextIv,iconIv,songList1,songList2,songList3,songList4,songList5,songList6;


    private TextView singerIv,titleIv;
    private ConstraintLayout musicRv;

    private SongLab lab = SongLab.getInstance();
    private SongRvAdapter rvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        /*初始化控件的函数*/
        nextIv=findViewById(R.id.music_bottom_iv_next);
        playIv=findViewById(R.id.music_bottom_iv_play);
        lastIv=findViewById(R.id.music_bottom_iv_last);
        iconIv=findViewById(R.id.music_bottom_iv_icon);
        singerIv=findViewById(R.id.music_bottom_iv_singer);
        titleIv=findViewById(R.id.music_bottom_iv_title);
        musicRv=findViewById(R.id.music_bottom_fav);
        songList1=findViewById(R.id.songList1);
        songList2=findViewById(R.id.songList2);
        songList3=findViewById(R.id.songList3);
        songList4=findViewById(R.id.songList4);
        songList5=findViewById(R.id.songList5);
        songList6=findViewById(R.id.songList6);


        nextIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);
        playIv.setOnClickListener(this);

        songList1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SonglistActivity.class);
            MainActivity.this.startActivity(intent);
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.music_bottom_iv_last:
                Log.d("TAG", "上一曲被点击了");
                break;
            case R.id.music_bottom_iv_play:
                Log.d("TAG", "播放被点击了");
                break;
            case R.id.music_bottom_iv_next:
                Log.d("TAG", "下一曲被点击了");
                break;
        }
    }

}
