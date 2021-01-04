package com.example.ufceventer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufceventer.db.DatabaseClient;
import com.example.ufceventer.models.PickAndScore;

import java.util.ArrayList;
import java.util.List;

public class ViewPicksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FightAdapter adapter;
    private List<FightListItem> fightList;
    private TextView heading;
    private Button bottomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fights);

        fightList = new ArrayList<>();
        recyclerView = findViewById((R.id.recyclerviewFight));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        adapter = new FightAdapter(fightList, this);
        recyclerView.setAdapter(adapter);
        getPicksAndScores(fightList);

        heading = findViewById(R.id.fights);
        bottomButton = findViewById(R.id.buttonViewPicks);
        bottomButton.setVisibility(View.GONE);
        heading.setText(getResources().getString(R.string.picks));
    }

    private void getPicksAndScores(List<FightListItem> fightList) {

        class GetPicksAndScoresAsyncTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    List<PickAndScore> picksAndScores = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().getPickAndScoreDAO().getPicksAndScores();
                    picksAndScores.forEach((pickAndScore) -> {
                        fightList.add(
                                new FightListItem(
                                        pickAndScore.getFighter1Name(),
                                        pickAndScore.getFighter1Record(),
                                        pickAndScore.getFighter1Pic(),
                                        pickAndScore.getFighter2Name(),
                                        pickAndScore.getFighter2Record(),
                                        pickAndScore.getFighter2Pic(),
                                        pickAndScore.isMainEvent(),
                                        pickAndScore.getId(),
                                        pickAndScore.getOutcome()
                                )
                        );
                        recyclerView.post(() -> adapter.notifyDataSetChanged());
                    });
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(ViewPicksActivity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        }

        GetPicksAndScoresAsyncTask getPicksAndScoresAsyncTask = new GetPicksAndScoresAsyncTask();
        getPicksAndScoresAsyncTask.execute();

    }
}

