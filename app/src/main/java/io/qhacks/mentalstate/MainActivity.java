package io.qhacks.mentalstate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.InputStreamReader;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        try {
            FileInputStream fstream = new FileInputStream("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine = "";
            int i= 0;
            //String[] arrStr = new String [i];

            while ((strLine = br.readLine()) != null) {
                i++;
            }


            FileInputStream fstream2 = new FileInputStream("data.txt");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream2));
            String[] arrStr = new String [i];

            while ((strLine = br2.readLine()) != null) {
                arrStr[i] = strLine;
                i++;
            }


            FileInputStream fstream3 = new FileInputStream("data.txt");
            BufferedReader br3 = new BufferedReader(new InputStreamReader(fstream3));
            final int j = i - 14;

            int a[]=new int[14];
            int b[]=new int[14];
            while(i>j )

            {
                String dat = arrStr[i];
                if (arrStr[i] != null) {
                    a[i] = Integer.parseInt(Character.toString(dat.charAt(0)));
                    b[i] = Integer.parseInt(Character.toString(dat.charAt(2)));
                }
                else{
                    a[i] = 0;
                    b[i] = 0;
                }
                i--;

            }
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
                new DataPoint(a[14],b[14])
            });
            graph.addSeries(series);

//Read File Line By Line
        }
        catch (java.io.FileNotFoundException e){
            e.printStackTrace();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }


}

}


