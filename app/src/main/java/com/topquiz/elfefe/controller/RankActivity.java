package com.topquiz.elfefe.controller;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.topquiz.elfefe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;

public class RankActivity extends AppCompatActivity {

    private TableRow mTableRow;
    private TextView mPlayer,mRank,mScore;
    private Button mBack;

    String name;
    String[] scores= new String[5];
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5);
    private List<String> KEY_SCORE = new ArrayList<>(5);

    private TextView[] mPlayerAll = new TextView[5],
                       mScoreAll = new TextView[5];

    private List<Integer> mScoreData = new ArrayList<>();
    private List<String> mPlayerData = new ArrayList<>(5);

    private SharedPreferences mPreferences;
    private SharedPreferences mSetting;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        mPreferences = this.getSharedPreferences("PLAYER_KEY",MODE_PRIVATE);
        mSetting = this.getSharedPreferences("SCORE_KEY",MODE_PRIVATE);

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

        set();

//        scores = new String[]{
//                mScoreData.get(0)+"",
//                mScoreData.get(1)+"",
//                mScoreData.get(2)+"",
//                mScoreData.get(3)+"",
//                mScoreData.get(4)+"",
//        };

        mPlayerAll[0].setText(mPlayerData.get(0));
        mPlayerAll[1].setText(mPlayerData.get(1));
        mPlayerAll[2].setText(mPlayerData.get(2));
        mPlayerAll[3].setText(mPlayerData.get(3));
        mPlayerAll[4].setText(mPlayerData.get(4));

        mScoreAll[0].setText(scores[0]);
        mScoreAll[1].setText(scores[1]);
        mScoreAll[2].setText(scores[2]);
        mScoreAll[3].setText(scores[3]);
        mScoreAll[4].setText(scores[4]);

    }

    public void set(){
        boolean verification = true;
            for (int x = 0; x < mPreferences.getAll().size(); x++) {
                if ((mPreferences.getString(KEY_PLAYER.get(x), null).equals(name)) && (verification)) {
                    mPreferences.edit().putString(KEY_PLAYER.get(x), name).apply();
                    mSetting.edit().putInt(KEY_SCORE.get(x),valueScore).apply();
                    verification = false;
                } else if ((mPreferences.getString(KEY_PLAYER.get(x), null).equals("")) && (verification)) {
                    mPreferences.edit().putString(KEY_PLAYER.get(x), name).apply();
                    mSetting.edit().putInt(KEY_SCORE.get(x),valueScore).apply();
                    verification = false;
                }
            }

            System.out.println(mPreferences.getString(KEY_PLAYER.get(0), null));


        mScoreData.add(mSetting.getInt(KEY_SCORE.get(0),-1));
        mScoreData.add(mSetting.getInt(KEY_SCORE.get(1),-1));
        mScoreData.add(mSetting.getInt(KEY_SCORE.get(2),-1));
        mScoreData.add(mSetting.getInt(KEY_SCORE.get(3),-1));
        mScoreData.add(mSetting.getInt(KEY_SCORE.get(4),-1));


//        Collections.sort(mScoreData);

        mPlayerData.add(mScoreData.indexOf(mSetting.getInt(KEY_SCORE.get(0),-1)), mPreferences.getString(KEY_PLAYER.get(0), " "));
        mPlayerData.add(mScoreData.indexOf(mSetting.getInt(KEY_SCORE.get(1),-1)), mPreferences.getString(KEY_PLAYER.get(1), " "));
        mPlayerData.add(mScoreData.indexOf(mSetting.getInt(KEY_SCORE.get(2),-1)), mPreferences.getString(KEY_PLAYER.get(2), " "));
        mPlayerData.add(mScoreData.indexOf(mSetting.getInt(KEY_SCORE.get(3),-1)), mPreferences.getString(KEY_PLAYER.get(3), " "));
        mPlayerData.add(mScoreData.indexOf(mSetting.getInt(KEY_SCORE.get(4),-1)), mPreferences.getString(KEY_PLAYER.get(4), " "));


        for (int x=0;x<mScoreData.size();x++){
            if (mScoreData.get(x) == -1)
                scores[x] = "";
            else
                scores[x] = mScoreData.get(x).toString();

        }
            

        System.out.println("Nombre de joueur "+mPlayerData.size()+mPlayerData.subList(0,mPlayerData.size()));
        System.out.println("Nombre de Score "+mScoreData.size()+mScoreData.subList(0,mScoreData.size()));

        System.out.println( mPreferences.getString(KEY_PLAYER.get(0),null)+
                mPreferences.getString(KEY_PLAYER.get(1),null)+
                mPreferences.getString(KEY_PLAYER.get(2),null)+
                mPreferences.getString(KEY_PLAYER.get(3),null)+
                mPreferences.getString(KEY_PLAYER.get(4),null));
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
