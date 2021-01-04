package com.example.ufceventer;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufceventer.db.DatabaseClient;
import com.example.ufceventer.models.PickAndScore;
import com.squareup.picasso.Picasso;

public class PickAndScoreActivity extends AppCompatActivity {
    private EditText f1R1, f2R1, f1R2, f2R2, f1R3, f2R3, f1R4, f2R4, f1R5, f2R5; // fighter1Round1...
    private RadioGroup pick;
    private String fighter1Name, fighter1Record, fighter1Pic, fighter2Name, fighter2Record, fighter2Pic;
    private int dbId;
    private boolean isMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras().getBoolean("isMain")) {
            setContentView(R.layout.activity_pick_and_score_main_event);
            isMain = true;
        } else {
            setContentView(R.layout.activity_pick_and_score);
        }

        TextView textViewFighter1Name = findViewById(R.id.fighter1);
        TextView textViewFighter1Record = findViewById(R.id.fighter1Record);
        ImageView imageViewFighter1Pic = findViewById(R.id.fighter1Pic);
        TextView textViewFighter2Name = findViewById(R.id.fighter2);
        TextView textViewFighter2Record = findViewById(R.id.fighter2Record);
        ImageView imageViewFighter2Pic = findViewById(R.id.fighter2Pic);
        RadioButton rbFighter1Wins = findViewById(R.id.fighter1Wins);
        RadioButton rbFighter2Wins = findViewById(R.id.fighter2Wins);
        Button saveButton = findViewById(R.id.buttonSavePicks);
        pick = findViewById(R.id.radioGroupPick);
        f1R1 = findViewById(R.id.editText1);
        f2R1 = findViewById(R.id.editText2);
        f1R2 = findViewById(R.id.editText3);
        f2R2 = findViewById(R.id.editText4);
        f1R3 = findViewById(R.id.editText5);
        f2R3 = findViewById(R.id.editText6);
        f1R4 = findViewById(R.id.editText7);
        f2R4 = findViewById(R.id.editText8);
        f1R5 = findViewById(R.id.editText9);
        f2R5 = findViewById(R.id.editText10);

        fighter1Name = getIntent().getStringExtra("fighter1Name");
        fighter1Record = getIntent().getStringExtra("fighter1Record");
        fighter1Pic = getIntent().getStringExtra("fighter1Pic");
        String fighter1Wins = getIntent().getStringExtra("fighter1Name") + " " + getResources().getString(R.string.wins);
        fighter2Name = getIntent().getStringExtra("fighter2Name");
        fighter2Record = getIntent().getStringExtra("fighter2Record");
        fighter2Pic = getIntent().getStringExtra("fighter2Pic");
        String fighter2Wins = getIntent().getStringExtra("fighter2Name") + " " + getResources().getString(R.string.wins);
        dbId = getIntent().getIntExtra("dbId", -1);

        textViewFighter1Name.setText(fighter1Name);
        textViewFighter1Record.setText(fighter1Record);
        Picasso.get().load(fighter1Pic).into(imageViewFighter1Pic);
        rbFighter1Wins.setText(fighter1Wins);
        textViewFighter2Name.setText(fighter2Name);
        textViewFighter2Record.setText(fighter2Record);
        Picasso.get().load(fighter2Pic).into(imageViewFighter2Pic);
        rbFighter2Wins.setText(fighter2Wins);

        PickAndScore pickAndScore = new PickAndScore();
        pickAndScore.setMainEvent(isMain);
        pickAndScore.setFighter1Name(fighter1Name);
        pickAndScore.setFighter1Record(fighter1Record);
        pickAndScore.setFighter1Pic(fighter1Pic);
        pickAndScore.setFighter2Name(fighter2Name);
        pickAndScore.setFighter2Record(fighter2Record);
        pickAndScore.setFighter2Pic(fighter2Pic);

        if (dbId != -1) {
            saveButton.setText(getResources().getString(R.string.updatePicks));
            getCurrentPicks(dbId);
        }

        saveButton.setOnClickListener(v -> {
            createAndUpdate(pickAndScore, dbId);
        });

    }

    private void getCurrentPicks(int dbId) {
        class GetCurrentPicks extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    PickAndScore pickAndScores = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().getPickAndScoreDAO().getPickAndScoreWithId(dbId);
                    pick.check(pickAndScores.getOutcome());
                    f1R1.setText(String.valueOf(pickAndScores.getF1R1()));
                    f2R1.setText(String.valueOf(pickAndScores.getF2R1()));
                    f1R2.setText(String.valueOf(pickAndScores.getF1R2()));
                    f2R2.setText(String.valueOf(pickAndScores.getF2R2()));
                    f1R3.setText(String.valueOf(pickAndScores.getF1R3()));
                    f2R3.setText(String.valueOf(pickAndScores.getF2R3()));
                    if (isMain) {
                        f1R4.setText(String.valueOf(pickAndScores.getF1R4()));
                        f2R4.setText(String.valueOf(pickAndScores.getF2R4()));
                        f1R5.setText(String.valueOf(pickAndScores.getF1R5()));
                        f2R5.setText(String.valueOf(pickAndScores.getF2R5()));
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(PickAndScoreActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }
                return null;
            }
        }
        GetCurrentPicks getCurrentPicks = new GetCurrentPicks();
        getCurrentPicks.execute();
    }

    private void createAndUpdate(PickAndScore pickAndScore, int dbId) {
        final int r1F1 = !f1R1.getText().toString().equals("") ? Integer.parseInt(f1R1.getText().toString()) : 0;
        final int r1F2 = !f2R1.getText().toString().equals("") ? Integer.parseInt(f2R1.getText().toString()) : 0;
        final int r2F1 = !f1R2.getText().toString().equals("") ? Integer.parseInt(f1R2.getText().toString()) : 0;
        final int r2F2 = !f2R2.getText().toString().equals("") ? Integer.parseInt(f2R2.getText().toString()) : 0;
        final int r3F1 = !f1R3.getText().toString().equals("") ? Integer.parseInt(f1R3.getText().toString()) : 0;
        final int r3F2 = !f2R3.getText().toString().equals("") ? Integer.parseInt(f2R3.getText().toString()) : 0;
        if (pick.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Pick winner or draw", Toast.LENGTH_LONG).show();
            return;
        }
        final int id = pick.getCheckedRadioButtonId();

        class CreateAndUpdateAsync extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                pickAndScore.setF1R1(r1F1);
                pickAndScore.setF2R1(r1F2);
                pickAndScore.setF1R2(r2F1);
                pickAndScore.setF2R2(r2F2);
                pickAndScore.setF1R3(r3F1);
                pickAndScore.setF2R3(r3F2);
                if (pickAndScore.isMainEvent()) {
                    pickAndScore.setF1R4(!f1R4.getText().toString().equals("") ? Integer.parseInt(f1R4.getText().toString()) : 0);
                    pickAndScore.setF2R4(!f2R4.getText().toString().equals("") ? Integer.parseInt(f2R4.getText().toString()) : 0);
                    pickAndScore.setF1R5(!f1R5.getText().toString().equals("") ? Integer.parseInt(f1R5.getText().toString()) : 0);
                    pickAndScore.setF2R5(!f2R5.getText().toString().equals("") ? Integer.parseInt(f2R5.getText().toString()) : 0);
                }
                pickAndScore.setOutcome(id);

                if (dbId != -1) {
                    try {
                        pickAndScore.id = dbId;
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().getPickAndScoreDAO().update(pickAndScore);
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(PickAndScoreActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                    }
                } else {
                    try {
                        DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().getPickAndScoreDAO().insert(pickAndScore);
                    } catch (SQLiteConstraintException e) {
                        Toast.makeText(PickAndScoreActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                finish();
            }
        }

        CreateAndUpdateAsync saveChanges = new CreateAndUpdateAsync();
        saveChanges.execute();

    }

}
