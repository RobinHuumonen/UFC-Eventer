package com.example.ufceventer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ufceventer.db.DatabaseClient;
import com.example.ufceventer.models.PickAndScore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FightsActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private FightAdapter adapter;
   private List<FightListItem> fightList;
   private String uri;
   private String url;
   private String baseUrl = "https://www.sherdog.com/";
   Document fightsDoc = null;
    private Button viewPicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fights);
        uri = getIntent().getStringExtra("URI");
        if (uri.contains("/events/")) {
            url = "https://www.sherdog.com/" + uri;
            new ScrapeFights().execute();
        }
        fightList = new ArrayList<>();
        
        recyclerView = findViewById((R.id.recyclerviewFight));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        
        adapter = new FightAdapter(fightList, this);
        recyclerView.setAdapter(adapter);

        viewPicks = findViewById(R.id.buttonViewPicks);

        viewPicks.setOnClickListener(v -> {
            Intent picks = new Intent(this, ViewPicksActivity.class);
            this.startActivity(picks);
        });

    }

    public class ScrapeFights extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            // UI thread
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Worker thread
            try {
                fightsDoc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (fightsDoc != null) {
                try {
                    Element table = fightsDoc.select("body > div.container > div:nth-child(3) > div.col_left > section:nth-child(4) > div > div").get(0);
                    Elements rows = table.select("tr");

                    Element fighter1MainEventImg = fightsDoc.select("body > div.container > div:nth-child(3) > div.col_left > section:nth-child(3)" +
                            " > div > div.content.event > div.fight > div.fighter.left_side > a > img").get(0);

                    Element fighter2MainEventImg = fightsDoc.select("body > div.container > div:nth-child(3) > div.col_left > section:nth-child(3)" +
                            " > div > div.content.event > div.fight > div.fighter.right_side > a > img").get(0);

                    String fighter1MainEventPic = baseUrl + fighter1MainEventImg.attr("src");
                    String fighter2MainEventPic = baseUrl + fighter2MainEventImg.attr("src");

                    String fighter1MainEventName = fighter1MainEventImg.attr("alt");

                    fighter1MainEventName = fighter1MainEventName.replaceAll("(?:^|\\s)'([^']*?)'(?:\\s|$)", " ");

                    String fighter2MainEventName = fighter2MainEventImg.attr("alt");
                    fighter2MainEventName = fighter2MainEventName.replaceAll("(?:^|\\s)'([^']*?)'(?:\\s|$)", " ");

                    String fighter1MainEventRecord = fightsDoc.select("body > div.container > div:nth-child(3) > div.col_left > section:nth-child(3) > div > div.content.event > " +
                            "div.fight > div.fighter.left_side > span.record").text();

                    String fighter2MainEventRecord = fightsDoc.select("body > div.container > div:nth-child(3) > div.col_left > section:nth-child(3) > div > div.content.event > " +
                            "div.fight > div.fighter.right_side > span.record").text();

                    fightList.add(
                            new FightListItem(
                                    fighter1MainEventName,
                                    fighter1MainEventRecord,
                                    fighter1MainEventPic,
                                    fighter2MainEventName,
                                    fighter2MainEventRecord,
                                    fighter2MainEventPic,
                                    true,
                                    -1,
                                    0
                            )
                    );
                    adapter.notifyDataSetChanged();

                    for (int e = 1; e < rows.size(); e++) {
                        Element row = rows.get(e);
                        Elements columns = row.select("td");

                        Element fighter1Column = columns.get(1);
                        Element fighter2Column = columns.get(3);

                        String fighter1Name = fighter1Column.select("a").select("span").text();
                        String fighter2Name = fighter2Column.select("a").select("span").text();

                        String fighter1Pic = baseUrl + fighter1Column.select("img").attr("src");
                        String fighter2Pic = baseUrl + fighter2Column.select("img").attr("src");

                        String fighter1Record = fighter1Column.select("span").select("em").text();
                        String fighter2Record = fighter2Column.select("span").select("em").text();

                        fightList.add(
                                new FightListItem(
                                        fighter1Name,
                                        fighter1Record,
                                        fighter1Pic,
                                        fighter2Name,
                                        fighter2Record,
                                        fighter2Pic,
                                        false,
                                        -1,
                                        0
                                )
                        );
                        adapter.notifyDataSetChanged();
                    }
                } catch (Error e) {
                    Log.i("error", String.valueOf(e));
                }

            }

        }
    }

}
