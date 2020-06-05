package com.HATFmusic;

import java.io.Serializable;

public class Song implements Serializable {
    private String id; //歌曲id
    private String auther; //歌曲作者
    private String songname; //歌曲名称
    private String cover; //封面

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", auther='" + auther + '\'' +
                ", songname='" + songname + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
