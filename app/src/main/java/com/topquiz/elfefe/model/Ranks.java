package com.topquiz.elfefe.model;

public class Ranks {
    private String player;
    private int score;

    public Ranks(String player, int score){
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
