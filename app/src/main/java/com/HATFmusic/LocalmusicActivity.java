package com.HATFmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.source.MediaSource;

import java.util.ArrayList;
import java.util.List;

public class LocalmusicActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView nextIv,playIv,lastIv;
    TextView singerTv,songTv;
    RecyclerView musicRv;


    //数据源
    List<LocalMusicBean>mDatas;
    private LocalMusicAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        initView();
        mDatas = new ArrayList<>();
        //创建适配器
        adapter = new LocalMusicAdapter(this,mDatas);
        musicRv.setAdapter(adapter);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        musicRv.setLayoutManager(layoutManager);


        //加载本地数据源
        loadLocalMusicData();

    }
    private void loadLocalMusicData(){
        //加载本地存储当中的音乐mp3文件
        //1.获取ContentResover对象
        ContentResolver resolver = getContentResolver();
        //2.获取本地音乐地址
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        //3.开始查询地址
        Cursor cursor = resolver.query(uri,null,null,null,null);
        //4.遍历Cursor对象
        int id=0;
        while(cursor.moveToNext()){
            String song = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            id++;
            String sid = String.valueOf(id);
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        //将一行当中的数据封装到对象中
        LocalMusicBean bean=new LocalMusicBean(sid,song,singer,path);
        mDatas.add(bean);
        }
        //数据源变化，提示适配器更新
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        nextIv = findViewById(R.id.local_music_next);
        playIv = findViewById(R.id.local_music_play);
        lastIv = findViewById(R.id.local_music_last);
        singerTv = findViewById(R.id.localmusic_bottom_singer);
        songTv = findViewById(R.id.localmusic_bottom_song);
        musicRv = findViewById(R.id.local_music_rv);

        nextIv.setOnClickListener(this);
        playIv.setOnClickListener(this);
        lastIv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_music_last:
                break;
            case R.id.local_music_play:
                break;
            case R.id.local_music_next:
                break;
        }
    }
}
