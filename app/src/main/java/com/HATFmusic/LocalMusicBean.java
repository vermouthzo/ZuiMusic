package com.HATFmusic;

public class LocalMusicBean {
    private String id;
    private String song;
    private String singer;
    private String path;

    public LocalMusicBean(String id, String song, String singer, String path) {
        this.id = id;
        this.song = song;
        this.singer = singer;
        this.path = path;
    }

    public LocalMusicBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
