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


        try {
            InputStream file = getResources().openRawResource(
                    R.raw.dat);
            System.out.println("Opened");
            Scanner scanner = new Scanner(file);
            String strLine = "";
            int i= 0;

            String s = "";
            while (scanner.hasNextLine()) {
                 s = scanner.next();
                i++;
                System.out.println(s);
            }
            System.out.println("closed");
            scanner.close();
            System.out.println("closed");
            String arrStr [] = new String[(i+1)*2];
            file = getResources().openRawResource(
                    R.raw.dat);
            scanner = new Scanner(file);
            i = 0;
            while (scanner.hasNextLine()) {
                strLine = scanner.next();
                arrStr[i] = strLine;
                i++;

            }
            scanner.close();
            System.out.println("second close");
            final int j = i - 20;

            int a[]=new int[21];
            int b[]=new int[21];

            while(j<i && i > 1)

            {

                if(arrStr[i] != null && arrStr[i-1] != null) {
                    System.out.println("third while");
                    String dat = arrStr[i];
                    System.out.println(dat);
                    a[i] = Integer.parseInt(Character.toString(dat.charAt(0)));
                    i--;
                    dat = arrStr[i];
                    b[i] = Integer.parseInt(Character.toString(dat.charAt(0)));
                    System.out.println(b[i]);
                }
                else{
                    a[i] = 0;
                    i--;
                    b[i] = 0;
                }
                i--;
            }
            System.out.println("At bottom of 3");

            GraphView graph = (GraphView) findViewById(R.id.graph);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                new DataPoint(a[1],b[1]),
                new DataPoint(a[2],b[2]),
                new DataPoint(a[3],b[3]),
                new DataPoint(a[4],b[4]),
                new DataPoint(a[5],b[5]),
                new DataPoint(a[6],b[6]),
                new DataPoint(a[7],b[7]),
                new DataPoint(a[8],b[8]),
                new DataPoint(a[9],b[9]),
                new DataPoint(a[10],b[10]),
                new DataPoint(a[11],b[11]),
                new DataPoint(a[12],b[12]),
                new DataPoint(a[13],b[13]),
                new DataPoint(a[14],b[14]),
                    new DataPoint(a[15],b[15]),
                    new DataPoint(a[16],b[16]),
                    new DataPoint(a[17],b[17]),
                    new DataPoint(a[18],b[18]),
                    new DataPoint(a[19],b[19]),
                    new DataPoint(a[20],b[20])

            });
            System.out.println("Data");
            graph.addSeries(series);

//Read File Line By Line
        }
       catch (Exception e){
           e.printStackTrace();
       }


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


