package com.topquiz.elfefe.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.topquiz.elfefe.R;
import com.topquiz.elfefe.model.User;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mRankButton;
    private User mUser;

    public static final String RANK_KEY_FIRSTNAME = "RANK_KEY_FIRSTNAME";
    public static final String RANK_KEY_SCORE = "RANK_KEY_SCORE";

    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private SharedPreferences mPreferences;

    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        mUser = new User();

        mPreferences = this.getPreferences(MODE_PRIVATE);

        mGreetingText = findViewById(R.id.activity_main_greeting_txt);
        mNameInput = findViewById(R.id.activity_main_name_input);
        mPlayButton = findViewById(R.id.activity_main_play_btn);
        mRankButton = findViewById(R.id.activity_main_rank_btn);

        mPlayButton.setEnabled(false);

        greetUser();

        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(v -> {
            String firstname = mNameInput.getText().toString();
            mUser.setFirstname(firstname);

            mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstname()).apply();

            // User clicked the button
            Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);

            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();

            greetUser();

        }
    }

    private void greetUser() {
        final String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            final int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Welcome back, " + firstname
                    + "!\nYour last score was " + score
                    + ", will you do better this time?";
            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
        }
    }

    private void sendRankingData(){
        final String rankName = mPreferences.getString(PREF_KEY_FIRSTNAME, null);
        final int rankScore = mPreferences.getInt(PREF_KEY_SCORE, 0);

        if (rankName != null) {
            mRankButton.setAlpha(1);
            mRankButton.setEnabled(true);
            mRankButton.setOnClickListener(v -> {

                final Intent rankActivityIntent = new Intent(MainActivity.this, RankActivity.class);

                rankActivityIntent.putExtra(RANK_KEY_FIRSTNAME,rankName);
                rankActivityIntent.putExtra(RANK_KEY_SCORE,rankScore);

                startActivity(rankActivityIntent);
            });
        }else{
            mRankButton.setAlpha(0);
            mRankButton.setEnabled(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");

        sendRankingData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}