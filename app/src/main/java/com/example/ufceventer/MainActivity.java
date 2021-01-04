package com.example.ufceventer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<EventListItem> eventList;
    private String url = "https://www.sherdog.com/organizations/Ultimate-Fighting-Championship-UFC-2";
    Document eventsDoc = null;
    private Button viewPicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ScrapeEvents().execute();

        eventList = new ArrayList<>();
        recyclerView = findViewById((R.id.recyclerviewEvent));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new LinearLayoutManager(this)));
        adapter = new EventAdapter(eventList, this);
        recyclerView.setAdapter(adapter);
        viewPicks = findViewById(R.id.buttonViewPicks);

        viewPicks.setOnClickListener(v -> {
            Intent picks = new Intent(this, ViewPicksActivity.class);
            this.startActivity(picks);
        });
    }

    public class ScrapeEvents extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                eventsDoc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (eventsDoc != null) {
                try {
                    Element table = eventsDoc.select("table").get(0);
                    Elements rows = table.select("tr");

                    for (int e = 1; e < rows.size(); e++) {
                        Element row = rows.get(e);
                        Elements columns = row.select("td");

                        Element dateColumn = columns.get(0);
                        Elements dateSpans = dateColumn.select("span");
                        String month = dateSpans.get(1).text();
                        String day = dateSpans.get(2).text();
                        String year = dateSpans.get(3).text();

                        Element nameColumn = columns.get(1);
                        String title = nameColumn.select("span").text();
                        String fightsUri = nameColumn.select("a").attr("href");

                        eventList.add(
                                new EventListItem(
                                        title,
                                        day + "/" + month + "/" + year,
                                        fightsUri
                                ));
                        adapter.notifyDataSetChanged();
                    }
                } catch (Error e) {
                    Log.i("error", String.valueOf(e));
                }
                }


        }

    }
}