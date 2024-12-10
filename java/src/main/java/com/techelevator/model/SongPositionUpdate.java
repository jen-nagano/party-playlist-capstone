package com.techelevator.model;

public class SongPositionUpdate {
    private int songId;
    private int position;

    public SongPositionUpdate(int songId, int position) {
        this.songId = songId;
        this.position = position;
    }

    public SongPositionUpdate() {

    }

    // Getters and setters

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
