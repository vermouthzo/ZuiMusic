package com.HATFmusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.HATFmusic.adapter.PlayRvAdapter;
import com.HATFmusic.PlayService;
import com.HATFmusic.R;
import com.HATFmusic.Song;
import com.HATFmusic.SongLab;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;


public class PlayActivity extends AppCompatActivity {

    private ImageView last_song, next_song, back_play, iv_cover, btn_play, comment;
    private TextView songname, auther;
    private static TextView tv_progress, tv_total;  //当前播放时间，总时间
    private SongLab lab = SongLab.getInstance();
    private static SeekBar sb;  //进度控制台
    private Song currentSong;
    private RecyclerView songRv;
    private PlayRvAdapter rvAdapter;
    private ObjectAnimator animator;  //动画组件
    //private MusicControl musicControl;   //音乐服务控制器Binder实例
    private MyServiceCoon myServiceCoon;  //连接实例
    private Intent intent = getIntent();
    private boolean isUnbind = false;  //记录服务是否被解绑

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Serializable s = getIntent().getSerializableExtra("song");

        if (s != null && s instanceof Song) {
            currentSong = (Song) s;
            updateUI();
        }
        last_song = findViewById(R.id.last_song);
        next_song = findViewById(R.id.next_song);

        init();
        comment = findViewById(R.id.comment);
        comment.setOnClickListener(v -> {
            Intent intent = new Intent(this, CommentActivity.class);
            PlayActivity.this.startActivity(intent);
        });

        this.songRv = findViewById(R.id.com);
        rvAdapter = new PlayRvAdapter(this, p -> {
            //跳转到新界面，使用意图Intent
            Intent intent = new Intent(PlayActivity.this, CommentActivity.class);
            //通过位置p得到当前歌曲song
            Song c = lab.getSong(p);
            intent.putExtra("song", c);

            startActivity(intent);
        });
        //this.songRv.setAdapter(rvAdapter);
        //this.songRv.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 自定义方法，初始化播放器
     */
    private void init() {
        iv_cover = findViewById(R.id.iv_cover);
        animator = ObjectAnimator.ofFloat(iv_cover, "rotation", 0.0f, 360.0f);
        animator.setDuration(10000);  //旋转一周用的时长
        animator.setInterpolator(new LinearInterpolator());  //匀速旋转
        animator.setRepeatCount(-1);  //表示无限循环

        sb = findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //滑动条变化时的处理
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == seekBar.getMax()) {   //滑动到最大值时，结束动画
                    animator.pause();  //停止动画播放
                }
            }

            //开始滑动时的处理
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //停止滑动时的处理
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();  //获取seekBar的进度值
                //调用 服务的seekTo方法改进音乐的进度
                musicControl.seekTo(progress);
            }
        });
        tv_progress = findViewById(R.id.tv_progress);
        tv_total = findViewById(R.id.tv_total);
        btn_play = findViewById(R.id.btn_play);
        intent = new Intent(this, PlayService.class);   //打开服务的意图
        myServiceCoon = new MyServiceCoon();   //实例化服务连接对象
        startService(intent);
        bindService(intent, myServiceCoon, BIND_ABOVE_CLIENT);
        //btn_play.setOnClickListener(this);
    }

    private void updateUI() {
        songname = findViewById(R.id.songname);
        auther = findViewById(R.id.auther);
        songname.setText(currentSong.getSongname());
        auther.setText(currentSong.getAuther());
        //bind();
    }

    public static Handler handler = new Handler() {
        //处理子线程传来的消息
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();  //获取信息
            int duration = bundle.getInt("duration");   //总时长
            int currentDuration = bundle.getInt("currentDuration");   //当前的播放时长

            sb.setMax(duration);   //设置总长度
            sb.setProgress(currentDuration);    //设置 当前 的进度值

            //显示总时长开始
            int minute = duration / 1000 / 60;  //分钟
            int second = duration / 1000 % 60;  //秒
            String strMinute = "";
            String strSecond = "";
            if (minute < 10) {
                strMinute = "0" + minute;
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {
                strSecond = "0" + second;
            } else {
                strSecond = second + "";
            }
            tv_total.setText(strMinute + ":" + strSecond);
            //显示总时长结束

            //显示播放时长开始
            minute = currentDuration / 1000 / 60;  //分钟
            second = currentDuration / 1000 % 60;  //秒
            if (minute < 10) {
                strMinute = "0" + minute;
            } else {
                strMinute = minute + "";
            }
            if (second < 10) {
                strSecond = "0" + second;
            } else {
                strSecond = second + "";
            }
            tv_progress.setText(strMinute + ":" + strSecond);
            //显示播放时长结束
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play:  //播放
                musicControl.play();
                animator.start();
                break;
//            case R.id.btn_pause:   //暂停
//                musicControl.pausePlay();
//                animator.pause();
//                break;
//            case R.id.btn_continue:  //继续
//                musicControl.continuePlay();
//                animator.start();
//                break;
//            case R.id.btn_exit:   //退出
//                myUnbind();
//                finish();   //关闭界面
//                break;
        }
    }

    public void bind() {

        //图片圆角处理
        RoundedCorners rc = new RoundedCorners(150);
        RequestOptions ro = RequestOptions.bitmapTransform(rc)
                .override(300, 300);
        Uri uri = Uri.parse(currentSong.getCover());
        //获得上下文
        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.album1)
                .into(this.iv_cover);
    }

    //自定义解绑方法
    private void myUnbind() {
        if (!isUnbind) {
            isUnbind = true;
            musicControl.pausePlay();  //暂停播放
            unbindService(myServiceCoon); //解绑服务
            stopService(intent);  //停止服务
        }
    }

    class MyServiceCoon implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicControl = (PlayService.MusicControl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}