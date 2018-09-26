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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;

public class RankActivity extends AppCompatActivity {

    private Button mBack, mAlphabet, mValeur;

    String name;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5),
            KEY_SCORE = new ArrayList<>(5);

    private SharedPreferences mPlayerSettings, mScoreSettings;

    List<Ranks> mData;

    private LinkedHashMap<String, String> resultat;

    ArrayList<String> playerName = new ArrayList<>();
    ArrayList<Integer> scoreName = new ArrayList<>();

    private Iterator<String> playerNames;
    private Iterator<String> scoreNames;

    private SortedSet<String> keys;
    private List<String> mapValues;

    private ListView mRanking;

    private RankAdapter mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rank);

        System.out.println("RankActivity::onStart()");

        mPlayerSettings = this.getSharedPreferences("PLAYER_KEY", MODE_PRIVATE);
        mScoreSettings = this.getSharedPreferences("SCORE_KEY", MODE_PRIVATE);

        resultat = new LinkedHashMap<>();

        keys = new TreeSet<>();
        mapValues = new ArrayList<>();

        setPlayerScorePreference();

        mRanking = findViewById(R.id.listview);
        mBack = findViewById(R.id.activity_rank_back_btn);
        mAlphabet = findViewById(R.id.activity_rank_alphabet_btn);
        mValeur = findViewById(R.id.activity_rank_valeur_btn);

        name = getIntent().getStringExtra(RANK_KEY_FIRSTNAME);
        valueScore = getIntent().getIntExtra(RANK_KEY_SCORE, 0);

        mPlayer = new RankAdapter(this, resultat);


        backToMain();
    }

    private void setPlayerScorePreference() {
        this.KEY_PLAYER.add(0, "INTENT_PLAYER_KEY1");
        this.KEY_PLAYER.add(1, "INTENT_PLAYER_KEY2");
        this.KEY_PLAYER.add(2, "INTENT_PLAYER_KEY3");
        this.KEY_PLAYER.add(3, "INTENT_PLAYER_KEY4");
        this.KEY_PLAYER.add(4, "INTENT_PLAYER_KEY5");

        this.KEY_SCORE.add(0, "INTENT_SCORE_KEY1");
        this.KEY_SCORE.add(1, "INTENT_SCORE_KEY2");
        this.KEY_SCORE.add(2, "INTENT_SCORE_KEY3");
        this.KEY_SCORE.add(3, "INTENT_SCORE_KEY4");
        this.KEY_SCORE.add(4, "INTENT_SCORE_KEY5");

    }

    private void setViewList() {
        mData = new ArrayList<>();
        boolean verification = true;
        if (mPlayerSettings.getAll().size() < 5) {
            for (int x = 0; x <= mPlayerSettings.getAll().size(); x++) {
                if ((verification) && ((mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals("")) ||
                        (mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals(name)))) {
                    mPlayerSettings.edit().putString(KEY_PLAYER.get(x), name).apply();
                    mScoreSettings.edit().putInt(KEY_SCORE.get(x), valueScore).apply();
                    verification = false;
                }
                playerName.add(x, mPlayerSettings.getString(KEY_PLAYER.get(x), ""));
                scoreName.add(x, mScoreSettings.getInt(KEY_SCORE.get(x), 0));
                String name = scoreName.get(x).toString();

                Ranks ranks = new Ranks(playerName.get(x), scoreName.get(x));


                resultat.put(playerName.get(x), name);

                mData.add(x, ranks);

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RankActivity::onResume()");

        setViewList();

        List<Map.Entry<String, String>> entries =
                new ArrayList<>(resultat.entrySet());
        Collections.sort(entries, (a, b) -> a.getValue().compareTo(b.getValue()));
        Map<String, String> resultatByValue = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            resultatByValue.put(entry.getKey(), entry.getValue());
        }

        Map resultatBykey = new TreeMap(resultat);

        mAlphabet.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultatBykey));
        mValeur.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultatByValue));

        mRanking.setAdapter(mPlayer);

    }

    public void backToMain() {
        mBack.setOnClickListener(v -> finish());
    }
}
