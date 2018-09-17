package com.topquiz.elfefe.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.topquiz.elfefe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;
import static java.lang.System.out;

public class RankActivity extends AppCompatActivity {

    private TableRow mTableRow;
    private TextView mPlayer,mRank,mScore;
    private Button mBack;

    String name,scores;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5);
    private List<String> KEY_SCORE = new ArrayList<>(5);

    private TextView[] mPlayerAll = new TextView[5],
                       mScoreAll = new TextView[5];

    private List<String> mScoreData = new ArrayList<>();
    private SparseArray<String> mPlayerData = new SparseArray<>(5);

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        mPreferences = this.getPreferences(MODE_PRIVATE);

        setPlayerScorePreference();

        mTableRow = (TableRow) findViewById(R.id.activity_rank_trow);
        mPlayer = (TextView) findViewById(R.id.activity_rank_player_txt);
        mRank = (TextView) findViewById(R.id.activity_rank_rank_txt);
        mScore = (TextView) findViewById(R.id.activity_rank_score_txt);
        mBack = (Button) findViewById(R.id.activity_rank_back_btn);

        mPlayerAll[0] = (TextView) findViewById(R.id.activity_rank_player1_txt);
        mPlayerAll[1] = (TextView) findViewById(R.id.activity_rank_player2_txt);
        mPlayerAll[2] = (TextView) findViewById(R.id.activity_rank_player3_txt);
        mPlayerAll[3] = (TextView) findViewById(R.id.activity_rank_player4_txt);
        mPlayerAll[4] = (TextView) findViewById(R.id.activity_rank_player5_txt);

        mScoreAll[0] = (TextView) findViewById(R.id.activity_rank_score1_txt);
        mScoreAll[1] = (TextView) findViewById(R.id.activity_rank_score2_txt);
        mScoreAll[2] = (TextView) findViewById(R.id.activity_rank_score3_txt);
        mScoreAll[3] = (TextView) findViewById(R.id.activity_rank_score4_txt);
        mScoreAll[4] = (TextView) findViewById(R.id.activity_rank_score5_txt);


        backToMain();


        name = getIntent().getStringExtra(RANK_KEY_FIRSTNAME);
        valueScore = getIntent().getIntExtra(RANK_KEY_SCORE, 0);

        scores = valueScore+"";

        set();


        mPlayerAll[0].setText(mPlayerData.get(0));
        mPlayerAll[1].setText(mPlayerData.get(1));
        mPlayerAll[2].setText(mPlayerData.get(2));
        mPlayerAll[3].setText(mPlayerData.get(3));
        mPlayerAll[4].setText(mPlayerData.get(4));

        System.out.println( (mPreferences.getString(KEY_PLAYER.get(0),null))+
                            (mPreferences.getString(KEY_PLAYER.get(1),null))+
                            (mPreferences.getString(KEY_PLAYER.get(2),null))+
                            (mPreferences.getString(KEY_PLAYER.get(3),null))+
                            (mPreferences.getString(KEY_PLAYER.get(4),null)));
    }

    public void set(){
        boolean verification = true;
            for (int x = 0; x < mPreferences.getAll().size(); x++) {
                if (verification) {
                    if (mPreferences.getString(KEY_PLAYER.get(x), null).equals(name)) {
                        mPreferences.edit().putString(KEY_PLAYER.get(x), name).apply();
                        verification = false;
                    } else if ((mPreferences.getString(KEY_PLAYER.get(x), null).equals(" ")) && (verification)) {
                        mPreferences.edit().putString(KEY_PLAYER.get(x), name).apply();
                        verification = false;
                    }
                }
            }

        System.out.println(mPreferences.getAll().size()+"    "+mPreferences.getAll().toString()+"     ");

        mPlayerData.append(0, mPreferences.getString(KEY_PLAYER.get(0), null));
        mPlayerData.append(1, mPreferences.getString(KEY_PLAYER.get(1), null));
        mPlayerData.append(2, mPreferences.getString(KEY_PLAYER.get(2), null));
        mPlayerData.append(3, mPreferences.getString(KEY_PLAYER.get(3), null));
        mPlayerData.append(4, mPreferences.getString(KEY_PLAYER.get(4), null));
    }

    private void setPlayerScorePreference(){
        this.KEY_PLAYER.add(0,"INTENT_PLAYER_KEY1");
        this.KEY_PLAYER.add(1,"INTENT_PLAYER_KEY2");
        this.KEY_PLAYER.add(2,"INTENT_PLAYER_KEY3");
        this.KEY_PLAYER.add(3,"INTENT_PLAYER_KEY4");
        this.KEY_PLAYER.add(4,"INTENT_PLAYER_KEY5");

        this.KEY_SCORE.add(0,"INTENT_SCORE_KEY1");
        this.KEY_SCORE.add(1,"INTENT_SCORE_KEY2");
        this.KEY_SCORE.add(2,"INTENT_SCORE_KEY3");
        this.KEY_SCORE.add(3,"INTENT_SCORE_KEY4");
        this.KEY_SCORE.add(4,"INTENT_SCORE_KEY5");

        if (mPreferences.getAll().size() == 0) {
            mPreferences.edit().putString(KEY_PLAYER.get(0), " ").apply();
            mPreferences.edit().putString(KEY_PLAYER.get(1), " ").apply();
            mPreferences.edit().putString(KEY_PLAYER.get(2), " ").apply();
            mPreferences.edit().putString(KEY_PLAYER.get(3), " ").apply();
            mPreferences.edit().putString(KEY_PLAYER.get(4), " ").apply();
        }
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
