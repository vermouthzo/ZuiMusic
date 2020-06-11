package com.HATFmusic.musicplayer;

import java.io.Serializable;

public class Song implements Serializable {
    private String id; //歌曲id
    private String auther; //歌曲作者
    private String songname; //歌曲名称
    private String cover; //封面
    private int duration; //歌曲长度
    private long size; //歌曲的大小
    private String path; //歌曲的地址
    public Song() {
    }

    public Song(String id, String auther, String songname, String cover, int duration, long size, String path) {
        this.id = id;
        this.auther = auther;
        this.songname = songname;
        this.cover = cover;
        this.duration = duration;
        this.size = size;
        this.path = path;
    }

    public String getCover() {
        return cover;
    }

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


    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", auther='" + auther + '\'' +
                ", songname='" + songname + '\'' +
                ", cover='" + cover + '\'' +
                ", duration=" + duration +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }

}
