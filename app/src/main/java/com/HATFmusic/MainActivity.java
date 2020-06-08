package com.HATFmusic;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView lastIv,playIv,nextIv,iconIv ,music_bottom_iv_icon;
    TextView singerIv,titleIv;
    //数据源
    //list

    private SongLab lab = SongLab.getInstance();
    private SongRvAdapter rvAdapter;
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

        nextIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        //把主线程的handler传递给子线程使用
        lab.getData(handler);
    }


}
