package com.HATFmusic.musicplayer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
 Context context;
 //数据源
    List<Song> datas;

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(context).inflate()
        new MusicAdapter();
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MusicViewHolder extends RecyclerView.ViewHolder{
        TextView idTv,songTv,singerTv,timeTv;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            //idTv=itemView.findViewById(R.id.);
            //songTv=itemView.findViewById(R.id.);
            //singerTv = itemView.findViewById(R.id.);
            //timeTv=itemView.findViewById(R.id);
        }
    }
}
