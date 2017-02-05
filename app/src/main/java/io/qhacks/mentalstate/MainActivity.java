package io.qhacks.mentalstate;


import android.content.res.AssetManager;
import android.os.Environment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Context _this = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        //mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // specify an adapter (see also next example)
//        String[] myDataset = {"Notifications"};
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);

        //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/data.txt";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            JSONArray json = new JSONArray(prefs.getString("datajson", "[]"));
            DataPoint[] dataPoints = new DataPoint[json.length()];
            for(int i=0; i<json.length(); i++) {
                dataPoints[i] = new DataPoint(json.getJSONObject(i).getInt("date"), json.getJSONObject(i).getInt("type"));
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            GraphView graph = (GraphView) findViewById(R.id.graph);
            graph.getViewport().setMinX(json.getJSONObject(0).getInt("date"));
            graph.getViewport().setMaxX(json.getJSONObject(json.length()-1).getInt("date"));
            graph.getViewport().setMinY(-0.5);
            graph.getViewport().setMaxY(1.5);
            graph.addSeries(series);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!prefs.getBoolean("enabled", false)) {
            DailyPollManager.enableNotifications(this, 21);
            prefs.edit().putBoolean("enabled", true);
        }


        Button goodButton = (Button) findViewById(R.id.goodbutton);
        goodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_this, DailyPollResponseReceiver.class);
                intent.putExtra("type", 1);
                sendBroadcast(intent);
            }
        });

        Button badButton = (Button) findViewById(R.id.button4);
        badButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_this, DailyPollResponseReceiver.class);
                intent.putExtra("type", 0);
                sendBroadcast(intent);
            }
        });
    }

}


