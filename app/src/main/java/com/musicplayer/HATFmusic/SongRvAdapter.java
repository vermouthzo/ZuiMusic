package com.musicplayer.HATFmusic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


public class SongRvAdapter extends RecyclerView.Adapter<SongRvAdapter.SongRowHolder>{

    private SongLab lab = SongLab.getInstance();
    private SongClickListener listener;
    private Context context;

    public SongRvAdapter(Context context, SongClickListener lis) {
        this.listener = lis;
        this.context = context;
    }

    @NonNull
    @Override
    public SongRvAdapter.SongRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.song_row, parent, false);
        SongRowHolder holder = new SongRowHolder(rowView);
        return holder;
    }

    /**
     * 用于确定每一行的内容是什么，即填充行中各个视图的内容。
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull SongRowHolder holder, int position) {
        Song c = lab.getSong(position);
        holder.bind(c);
    }

    //自定义新接口
    public interface SongClickListener {
        void onSongClick(int position);
    }
    /**
     * 用于确定列表总共有几行（即多少个SongRowHolder对象）
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return lab.getSize();
    }

//    public interface SongClickListener{
//        void onSongClick(int position);
//    }

    /**
     * 单行布局对应得Java控制类
     */

     class SongRowHolder extends RecyclerView.ViewHolder {
        private TextView songname; //歌曲名称
        private TextView auther;   //作者
        private ImageView cover, iv_cover;   //封面图片

         SongRowHolder(@NonNull View row) {
            super(row);
            this.songname = row.findViewById(R.id.song_name);
            this.auther = row.findViewById(R.id.auther);
            this.cover = row.findViewById(R.id.song_cover);
            row.setOnClickListener(v -> {
                int position = getLayoutPosition();
                Log.d("zui", position + 1 + "行被点击啦！");
                //调用时机的跳转代码
                listener.onSongClick(position);
            });
        }



        /**
         * 自定义方法，用于向内部的title修改
         *
         * @param c
         */
        public void bind(Song c) {
            this.songname.setText(c.getSongname());
            this.auther.setText(c.getAuther());

            //图片圆角处理
            RoundedCorners rc = new RoundedCorners(150);
            RequestOptions ro = RequestOptions.bitmapTransform(rc)
                    .override(300, 300);

            //获得上下文
            Glide.with(context)
                    .load(c.getCover())
                    .placeholder(R.drawable.comment_pic)
                    .apply(ro)
                    .into(this.cover);
        }
    }
}
