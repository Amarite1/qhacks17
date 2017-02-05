package io.qhacks.mentalstate;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("enabled", false)) {
            DailyPollManager.enableNotifications(this, 21);
            prefs.edit().putBoolean("enabled", true);
        }
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 1),
                new DataPoint(2, 0)
        });
        graph.addSeries(series);

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


