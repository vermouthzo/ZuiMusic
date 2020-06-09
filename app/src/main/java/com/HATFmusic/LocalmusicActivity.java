package com.HATFmusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalmusicActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView nextIv,playIv,lastIv;
    TextView singerTv,songTv;
    RecyclerView musicRv;


    //数据源
    List<LocalMusicBean>mDatas;
    private LocalMusicAdapter adapter;
    private int position;
    //记录当前正在播放的音乐的位置
    int currentPlayPosition = -1;
    MediaPlayer mediaPlayer;
//记录暂停音乐时的进度条的位置
    int currentPausePositionInSong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localmusic);
        initView();
        checkPermission();
        mediaPlayer = new MediaPlayer();
        mDatas = new ArrayList<>();
        //创建适配器
        adapter = new LocalMusicAdapter(this,mDatas);
        musicRv.setAdapter(adapter);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        musicRv.setLayoutManager(layoutManager);


        //加载本地数据源
        loadLocalMusicData();
        //设置每一项的点击事件
        setEvenListencer();

 }
 private  void checkPermission(){
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
         //android 6.0以上
         // Log.v(TAG,"测试手机版本为：android 6.0以上");
         int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
         if (writePermission != PackageManager.PERMISSION_GRANTED) {
             requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
         }else{
         }
     }else{//android 6.0以下
         // Log.v(TAG,"测试手机版本为：android 6.0以下");         }     }
     }

 }

    private void setEvenListencer() {
        adapter.setOnItemClickListener(new LocalMusicAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                currentPlayPosition = position;
                LocalMusicBean musicBean = mDatas.get(position);
                playMusicInMusicBean(musicBean);
            }
        });

    }

    public void playMusicInMusicBean(LocalMusicBean musicBean) {
       /*根据传入数据播放*/
        //设置底部显示的歌手名称和歌曲名
        singerTv.setText(musicBean.getSinger());
        songTv.setText(musicBean.getSong());
        stopMusic();
        //重置多媒体播放器
        mediaPlayer.reset();
        //设置新路径
        try {
            mediaPlayer.setDataSource(musicBean.getPath());
            playMusic();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /*点击播放按钮播放音乐
* 播放音乐的两种情况
* 1.从暂停到播放
* 2.从暂停到播放
* */
    private void playMusic() {
        /*播放音乐的函数*/
       if (mediaPlayer!=null&&!mediaPlayer.isPlaying()){
          if (currentPausePositionInSong == 0){
              try {
                  mediaPlayer.prepare();
                  mediaPlayer.start();
              } catch (IOException e){
                  e.printStackTrace();
              }
           }else{
              //从暂停到播放
              mediaPlayer.seekTo(currentPausePositionInSong);
              mediaPlayer.start();

          }

           playIv.setImageResource(R.drawable.exo_controls_pause);
       }
    }

    private void stopMusic() {
        /*停止音乐的函数*/
        if(mediaPlayer!=null){
            currentPausePositionInSong = 0;
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            playIv.setImageResource(R.drawable.exo_controls_play);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {//允许

            } else {//拒绝
                Toast.makeText(this, "请赋予读写权限，否则应用将无法使用！", Toast.LENGTH_LONG).show();
                LocalmusicActivity.this.finish();
            }
        }
    }
@Override
protected void onDestroy(){
        super.onDestroy();
        stopMusic();
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
                if (currentPlayPosition == 0){
                    Toast.makeText(this,"已经是第一首了，没有上一曲",Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPlayPosition = currentPlayPosition-1;
                LocalMusicBean lastBean = mDatas.get(currentPlayPosition);
                playMusicInMusicBean(lastBean);
                break;
            case R.id.local_music_play:
                if(currentPlayPosition == -1){
                    //并没有选中要播放的音乐
                    Toast.makeText(this,"请选择要播放的音乐",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mediaPlayer.isPlaying()){
                    //此时处于播放状态，需要暂停音乐
                    pauseMusic();
                }else{
                    //此时没有播放音乐，点击开始播放
                    playMusic();
                }
                break;
            case R.id.local_music_next:
                if (currentPlayPosition == mDatas.size()-1){
                    Toast.makeText(this,"已经是最后一曲了，没有下一曲",Toast.LENGTH_SHORT).show();
                    return;
                }
                currentPlayPosition = currentPlayPosition + 1;
                LocalMusicBean nextBean = mDatas.get(currentPlayPosition);
                playMusicInMusicBean(nextBean);
                break;
        }
    }


    //暂停音乐的函数
    private void pauseMusic(){
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()){
            currentPausePositionInSong=mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            playIv.setImageResource(R.drawable.exo_controls_play);
        }
    }
}
