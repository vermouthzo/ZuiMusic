package com.musicplayer.HATFmusic;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SongApi {
    @GET("/song")
    Call<List<Song>> getAllSongs();

    /**
     * 获取热门评论
     *
     * @param songId 歌曲编号
     * @return
     */
    @GET("/song/{songId}/hotcomments")
    Call<List<Comment>> getHotComments(@Path("songId") String songId);
    //    替换getChannel()

    /**
     * 新增评论
     *
     * @param songId 歌曲编号
     * @param c      评论对象
     * @return
     */
    @POST("/song/{songId}/comment")
    Call<Song> addComment(@Path("songId") String songId, @Body Comment c);
}