package com.example.learncodeonlinedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    RecyclerAdapterForQuestions recyclerAdapterForQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("conceptName"));//setting the received concept/topic name to current screen title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewForQuestions);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerAdapterForQuestions = new RecyclerAdapterForQuestions(this, new JSONArray());
        recyclerView.setAdapter(recyclerAdapterForQuestions);

        prepareDataFromApi("https://learncodeonline.in/api/android/datastructure.json");//fetching and generating list from API
        findViewById(R.id.layout_footer_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://courses.learncodeonline.in";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void prepareDataFromApi(final String urlToRead) {

        AsyncTask asyncTaskToGetData = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                findViewById(R.id.tvAtContentFooterForCredits).setVisibility(View.GONE);
                findViewById(R.id.layout_footer_ad).setVisibility(View.GONE);
                findViewById(R.id.progressBarATMain).setVisibility(View.VISIBLE);
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    StringBuilder result = new StringBuilder();
                    URL url = new URL(urlToRead);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        result.append(line);
                    }
                    rd.close();
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                try {
                    //setting the received json data to RecyclerView's adapter
                    JSONArray dataJsonArray = new JSONObject(((String) o).toString()).getJSONArray("questions");
                    recyclerAdapterForQuestions.setDataArray(dataJsonArray);
                    recyclerAdapterForQuestions.notifyDataSetChanged();
                    findViewById(R.id.tvAtContentFooterForCredits).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_footer_ad).setVisibility(View.VISIBLE);
                    findViewById(R.id.progressBarATMain).setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        asyncTaskToGetData.execute();

    }

}
