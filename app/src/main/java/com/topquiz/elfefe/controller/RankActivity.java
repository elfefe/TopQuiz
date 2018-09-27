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

    private Button mBack;
    private ListView mRanking;

    String name;
    int valueScore;

    private List<String> KEY_PLAYER = new ArrayList<>(),
                         KEY_SCORE = new ArrayList<>();

    private Map<String, String> resultat;
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

        mRanking = findViewById(R.id.listview);
        mBack = findViewById(R.id.activity_rank_back_btn);
        Button mAlphabet = findViewById(R.id.activity_rank_alphabet_btn);
        Button mValeur = findViewById(R.id.activity_rank_valeur_btn);

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

        // Make the listView with resultat data
        mPlayer = new RankAdapter(this, resultat);
        mAlphabet.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultat));
        mValeur.setOnClickListener(v -> mPlayer = new RankAdapter(this, resultatByValue));

        backToMain();
    }


    private void setViewList() {
        resultat = new TreeMap<>();
        boolean verification = true;
        int x = 0;

        do {
            System.out.println(x);
            KEY_PLAYER.add(x, "INTENT_PLAYER_KEY"+x);
            KEY_SCORE.add(x, "INTENT_SCORE_KEY"+x);

            // If the shared preference cursor is empty put the Extra value and stop the loop
            if (mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals("") ||
                mPlayerSettings.getString(KEY_PLAYER.get(x), "").equals(name)){

                mPlayerSettings.edit().putString(KEY_PLAYER.get(x), name).apply();
                mScoreSettings.edit().putInt(KEY_SCORE.get(x), valueScore).apply();
                verification = false;
            }

            // Get the values from the Shared Preference and put them on resultat

                playerName.add(x, mPlayerSettings.getString(KEY_PLAYER.get(x), ""));
                scoreName.add(x, mScoreSettings.getInt(KEY_SCORE.get(x), 0));
                String names = scoreName.get(x).toString();
                resultat.put(playerName.get(x), names);

            x++;
        }while (verification);
    }



    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RankActivity::onResume()");

        mPlayer.notifyDataSetChanged();

        mRanking.setAdapter(mPlayer);
    }

    public void backToMain() {
        mBack.setOnClickListener(v -> finish());
    }
}
