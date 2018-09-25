package com.topquiz.elfefe.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.topquiz.elfefe.R;
import com.topquiz.elfefe.model.Ranks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;

public class RankActivity extends AppCompatActivity {

    private Button mBack, mAlphabet, mValeur;

    String name;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5),
                         KEY_SCORE = new ArrayList<>(5);

    private SharedPreferences mPlayerSettings,mScoreSettings;

    List<Ranks> mData;

    private HashMap<String,String> resultat;
    private Iterator<String> playerNames,scoreNames;

    private SortedSet<String> keys;
    private List<String> mapValues;

    private ListView mRanking;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        System.out.println("RankActivity::onStart()");

        mPlayerSettings = this.getSharedPreferences("PLAYER_KEY",MODE_PRIVATE);
        mScoreSettings = this.getSharedPreferences("SCORE_KEY",MODE_PRIVATE);

        resultat = new HashMap<>();

        keys = new TreeSet<>();
        mapValues = new ArrayList<>();

        setPlayerScorePreference();

        mRanking = findViewById(R.id.listview);
        mBack = findViewById(R.id.activity_rank_back_btn);
        mAlphabet = findViewById(R.id.activity_rank_alphabet_btn);
        mValeur = findViewById(R.id.activity_rank_valeur_btn);

        name = getIntent().getStringExtra(RANK_KEY_FIRSTNAME);
        valueScore = getIntent().getIntExtra(RANK_KEY_SCORE, 0);


        setViewList();

        playerNames = resultat.keySet().iterator();
        scoreNames = resultat.values().iterator();

        keys.addAll(resultat.keySet());
        mapValues.addAll(resultat.values());

        final RankAdapter mPlayer = new RankAdapter(this, mData);

        mRanking.setAdapter(mPlayer);


        backToMain();
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

    }

    private void setViewList(){
        mData = new ArrayList<>();
        boolean verification=true;
        if (mPlayerSettings.getAll().size()<5) {
            for (int x = 0; x <= mPlayerSettings.getAll().size(); x++) {
                if ((verification) && ((mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals("")) ||
                        (mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals(name)))) {
                    mPlayerSettings.edit().putString(KEY_PLAYER.get(x), name).apply();
                    mScoreSettings.edit().putInt(KEY_SCORE.get(x), valueScore).apply();
                    verification = false;
                }
                String playerName = mPlayerSettings.getString(KEY_PLAYER.get(x), "");
                Integer name = mScoreSettings.getInt(KEY_SCORE.get(x), 0);
                String scoreName = name.toString();

                Ranks ranks = new Ranks(playerName, name);

                resultat.put(playerName, scoreName);

                mData.add(x, ranks);
                System.out.println(verification);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RankActivity::onResume()");

        mAlphabet.setOnClickListener(v -> playerNames = keys.iterator());
        mValeur.setOnClickListener(v -> Collections.sort(mapValues));

    }

    public void backToMain(){
        mBack.setOnClickListener(v -> finish());
    }
}
