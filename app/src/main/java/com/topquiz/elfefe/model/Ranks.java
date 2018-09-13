package com.topquiz.elfefe.model;

import android.widget.TextView;

public class Ranks {
    private TextView mPlayer;
    private TextView mRank;
    private TextView mScore;


    public Ranks(TextView player, TextView rank, TextView score) {
        mPlayer = player;
        mRank = rank;
        mScore = score;
    }
}
