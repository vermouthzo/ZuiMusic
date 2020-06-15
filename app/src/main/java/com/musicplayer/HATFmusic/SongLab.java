package com.musicplayer.HATFmusic;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SongLab {

    private List<Song> data;

    public final static int MSG_HOT_COMMENTS = 2;
    public final static int MSG_ADD_COMMENT = 3;
    public final static int MSG_NET_FAILURE = 4;
    //用常量代替硬编码内容
    public final static String TAG = "ZuiMusic";
    public final static int MSG_CHANNELS = 1;
    public final static int MSG_FAILURE = -1;



    private static SongLab INSTANCE = null;

    // 未实现单例模式，可能会是实现多例模式
    private SongLab() {
        //初始化空白列表
        data = new ArrayList<>();
    }

    public static SongLab getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SongLab();
        }
        return INSTANCE;
    }

    /**
     * 获取当前数据源中共有多少首歌曲
     *
     * @return
     */
    public int getSize() {
        return data.size();
    }

    /**
     * 获取一首歌曲
     *
     * @param position 频道的序号
     * @return 歌曲对象Song
     */
    public Song getSong(int position) {
        return this.data.get(position);
    }

    /**
     * 访问网络得到真实数据。
     */
    public void getData(final Handler handler) {
        Retrofit retrofit = RetrofitClient.getInstance();
        //调用单例
        SongApi api = retrofit.create(SongApi.class);
        Call<List<Song>> call = api.getAllSongs();
        //enqueue把代码放在子线程中进行
        //enqueue会自己生成子线程， 去执行后续代码
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call,
                                   Response<List<Song>> response) {
                if (null != response && null != response.body()) {
                    //如果网络访问成功
                    Log.d("zui", "网络访问成功！");
                    Log.d("zui", "从阿里云得到的数据是：");
                    Log.d("zui", response.body().toString());
                    Message msg = new Message();
                    data = response.body();
                    //发出通知
                    msg.what = 1;  //自己规定1代表从阿里云获取数据完毕
                    handler.sendMessage(msg);
                } else {
                    Log.w("zui", "response没有数据！");
                    Message msg = new Message();
                    msg.what = 4;
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                //如果网络访问失败
                Log.e("DianDian", "网络访问出错了", t);
                Message msg = new Message();
                msg.what = 4;
                handler.sendMessage(msg);
            }
        });
    }

    /**
     * 添加评论
     */
    public void addComment(String channelId, Comment comment, final Handler handler) {
        //调用单例
        Retrofit retrofit = RetrofitClient.getInstance();
        SongApi api = retrofit.create(SongApi.class);
        Call<Song> call = api.addComment(channelId, comment);
        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                Log.d(TAG, "添加评论后服务器返回的数据是：");
                Log.d(TAG, response.body().toString());
                Message msg = new Message();
                msg.what = MSG_ADD_COMMENT;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Log.e(TAG, "访问网络失败！", t);
                Message msg = new Message();
                msg.what = MSG_NET_FAILURE;
                handler.sendMessage(msg);
            }
        });
    }

}
