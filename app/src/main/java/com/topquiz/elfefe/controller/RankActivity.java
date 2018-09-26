package com.topquiz.elfefe.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.topquiz.elfefe.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_FIRSTNAME;
import static com.topquiz.elfefe.controller.MainActivity.RANK_KEY_SCORE;

public class RankActivity extends AppCompatActivity {

    private SharedPreferences mPlayerSettings, mScoreSettings;

    private Button mBack, mAlphabet, mValeur;
    private ListView mRanking;

    String name;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(5),
                         KEY_SCORE = new ArrayList<>(5);


    private LinkedHashMap<String, String> resultat;
    private ArrayList<String> playerName = new ArrayList<>();
    private ArrayList<Integer> scoreName = new ArrayList<>();
    private RankAdapter mPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("RankActivity::onStart()");

        setContentView(R.layout.activity_rank);

        // Create two shared preference db one for the strings and the other for the integers
        mPlayerSettings = this.getSharedPreferences("PLAYER_KEY", MODE_PRIVATE);
        mScoreSettings = this.getSharedPreferences("SCORE_KEY", MODE_PRIVATE);

        // Get the Extras from the mainActivity
        name = getIntent().getStringExtra(RANK_KEY_FIRSTNAME);
        valueScore = getIntent().getIntExtra(RANK_KEY_SCORE, 0);


        setPlayerScorePreference();

        mRanking = findViewById(R.id.listview);
        mBack = findViewById(R.id.activity_rank_back_btn);
        mAlphabet = findViewById(R.id.activity_rank_alphabet_btn);
        mValeur = findViewById(R.id.activity_rank_valeur_btn);

        // Initiate resultat with the Extras
        setViewList();

        // Sort resultat by values
        List<Map.Entry<String, String>> entries =
                new ArrayList<>(resultat.entrySet());
        Collections.sort(entries, (a, b) -> a.getValue().compareTo(b.getValue()));
        Map<String, String> resultatByValue = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : entries) {
            resultatByValue.put(entry.getKey(), entry.getValue());
        }

        // Sort resultat by keys
        Map resultatBykey = new TreeMap(resultat);

        // Make the listView with resultat data
        mPlayer = new RankAdapter(this, resultat);
        mAlphabet.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultatBykey));
        mValeur.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultatByValue));

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
        resultat = new LinkedHashMap<>();
        boolean verification = true;
        if (mPlayerSettings.getAll().size() < 5) {
            for (int x = 0; x <= mPlayerSettings.getAll().size(); x++) {

                // If the shared preference cursor is empty are the same as the Extra value put the Extra value
                if ((verification) && ((mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals("")) ||
                        (mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals(name)))) {
                    mPlayerSettings.edit().putString(KEY_PLAYER.get(x), name).apply();
                    mScoreSettings.edit().putInt(KEY_SCORE.get(x), valueScore).apply();
                    verification = false;
                }

                // Get the values from the Shared Preference and put them on resultat
                playerName.add(x, mPlayerSettings.getString(KEY_PLAYER.get(x), ""));
                scoreName.add(x, mScoreSettings.getInt(KEY_SCORE.get(x), 0));
                String name = scoreName.get(x).toString();

                resultat.put(playerName.get(x), name);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RankActivity::onResume()");



        mRanking.setAdapter(mPlayer);

    }

    public void backToMain() {
        mBack.setOnClickListener(v -> finish());
    }
}
