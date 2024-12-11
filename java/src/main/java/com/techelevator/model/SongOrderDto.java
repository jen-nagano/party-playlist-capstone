package com.techelevator.model;

public class SongOrderDto {


    // Getters and setters

    private int songId;  // Adjusted to match the join table column
    private int position; // Adjusted to match the join table column

    // Getters and setters
    public int getSongId() {
        return songId;
    }

    public void setSong_id(int songId) {
        this.songId = songId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "SongOrderDto{" +
                "songId=" + songId +
                ", position=" + position +
                '}';
    }
}
