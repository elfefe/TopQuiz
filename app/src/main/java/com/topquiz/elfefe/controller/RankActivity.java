package com.topquiz.elfefe.controller;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.topquiz.elfefe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;

public class RankActivity extends AppCompatActivity {

    private TableRow mTableRow;
    private TextView mPlayer,mScore;
    private Button mBack, mAlphabet, mValeur;

    String name;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5);
    private List<String> KEY_SCORE = new ArrayList<>(5);

    private SharedPreferences mPlayerSettings;
    private SharedPreferences mScoreSettings;

    private HashMap<String,String> resultat;
    private Iterator<String> playerNames;
    private Iterator<String> scoreNames;

    private SortedSet<String> keys;
    private List<String> mapValues;

    private TextView mPlayer0;
    private TextView mPlayer1;
    private TextView mPlayer2;
    private TextView mPlayer3;
    private TextView mPlayer4;

    private TextView mScore0;
    private TextView mScore1;
    private TextView mScore2;
    private TextView mScore3;
    private TextView mScore4;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        mPlayerSettings = this.getSharedPreferences("PLAYER_KEY",MODE_PRIVATE);
        mScoreSettings = this.getSharedPreferences("SCORE_KEY",MODE_PRIVATE);

        setPlayerScorePreference();

        mTableRow = (TableRow) findViewById(R.id.activity_rank_trow);
        mPlayer = (TextView) findViewById(R.id.activity_rank_player_txt);
        mScore = (TextView) findViewById(R.id.activity_rank_score_txt);
        mBack = (Button) findViewById(R.id.activity_rank_back_btn);
        mAlphabet = (Button) findViewById(R.id.activity_rank_alphabet_btn);
        mValeur = (Button) findViewById(R.id.activity_rank_valeur_btn);

        mPlayer0 = (TextView) findViewById(R.id.activity_rank_player1_txt);
        mPlayer1 = (TextView) findViewById(R.id.activity_rank_player2_txt);
        mPlayer2 = (TextView) findViewById(R.id.activity_rank_player3_txt);
        mPlayer3 = (TextView) findViewById(R.id.activity_rank_player4_txt);
        mPlayer4 = (TextView) findViewById(R.id.activity_rank_player5_txt);

        mScore0 = (TextView) findViewById(R.id.activity_rank_score1_txt);
        mScore1 = (TextView) findViewById(R.id.activity_rank_score2_txt);
        mScore2 = (TextView) findViewById(R.id.activity_rank_score3_txt);
        mScore3 = (TextView) findViewById(R.id.activity_rank_score4_txt);
        mScore4 = (TextView) findViewById(R.id.activity_rank_score5_txt);


        backToMain();


        name = getIntent().getStringExtra(RANK_KEY_FIRSTNAME);
        valueScore = getIntent().getIntExtra(RANK_KEY_SCORE, 0);

        resultat = new HashMap<>();


        for (int x=0;x<mPlayerSettings.getAll().size();x++){
            String playerName = mPlayerSettings.getString(KEY_PLAYER.get(x),"");
            Integer name = mScoreSettings.getInt(KEY_SCORE.get(x),0);
            String scoreName = name.toString();

            resultat.put(playerName,scoreName);
        }

        playerNames = resultat.keySet().iterator();
        scoreNames = resultat.values().iterator();

        keys = new TreeSet<>(resultat.keySet());
        mapValues = new ArrayList<>(resultat.values());



        mPlayer0.setText(playerNames.next());
        mPlayer1.setText(playerNames.next());
        mPlayer2.setText(playerNames.next());
        mPlayer3.setText(playerNames.next());
        mPlayer4.setText(playerNames.next());

        mScore0.setText(scoreNames.next());
        mScore1.setText(scoreNames.next());
        mScore2.setText(scoreNames.next());
        mScore3.setText(scoreNames.next());
        mScore4.setText(scoreNames.next());


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

        if (mPlayerSettings.getAll().size() == 0) {
            mPlayerSettings.edit().putString(KEY_PLAYER.get(0), " ").apply();
            mPlayerSettings.edit().putString(KEY_PLAYER.get(1), " ").apply();
            mPlayerSettings.edit().putString(KEY_PLAYER.get(2), " ").apply();
            mPlayerSettings.edit().putString(KEY_PLAYER.get(3), " ").apply();
            mPlayerSettings.edit().putString(KEY_PLAYER.get(4), " ").apply();

            mScoreSettings.edit().putInt(KEY_SCORE.get(0), 0).apply();
            mScoreSettings.edit().putInt(KEY_SCORE.get(1), 0).apply();
            mScoreSettings.edit().putInt(KEY_SCORE.get(2), 0).apply();
            mScoreSettings.edit().putInt(KEY_SCORE.get(3), 0).apply();
            mScoreSettings.edit().putInt(KEY_SCORE.get(4), 0).apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("GameActivity::onResume()");
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
