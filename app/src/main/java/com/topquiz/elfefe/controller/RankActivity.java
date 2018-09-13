package com.topquiz.elfefe.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.topquiz.elfefe.R;
import com.topquiz.elfefe.model.Ranks;

import java.util.List;

public class RankActivity extends AppCompatActivity {

    private TableRow mTableRow;
    private TextView mPlayer;
    private TextView mRank;
    private TextView mScore;
    private Button mBack;

    private List<TableRow> mTableRowFive;
    private List<TextView> mPlayerFive;
    private List<TextView> mRankFive;
    private List<TextView> mScoreFive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        mTableRow = (TableRow) findViewById(R.id.activity_rank_trow);
        mPlayer = (TextView) findViewById(R.id.activity_rank_player_txt);
        mRank = (TextView) findViewById(R.id.activity_rank_rank_txt);
        mScore = (TextView) findViewById(R.id.activity_rank_score_txt);
        mBack = (Button) findViewById(R.id.activity_rank_back_btn);

        backToMain();

    }


    public void backToMain(){
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
