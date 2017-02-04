package io.qhacks.mentalstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.R.attr.data;


public class DailyPollResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int responseType = intent.getExtras().getInt("type");
        String FILENAME = "data.txt";
        switch(responseType){

            case 0:{ //bad day
                //todo: send to server

                try{
                    FileInputStream fstream = new FileInputStream("data.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                    String strLine;
                    int i = 0;

//Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                        // Print the content on the console
                        System.out.println (strLine);
                        i++;
                    }

//Close the input stream
                    br.close();
                    String Data = "";
                    if (i == 0){
                         Data = "1 0";
                    }
                    else{
                        int num = i+1;
                        Data = String.valueOf(num) +" 0";
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true));
                    writer.write(Data);
                    writer.newLine();
                }
                catch (java.io.FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (java.io.IOException e){
                    e.printStackTrace();
                }
            }
            case 1:{ //good day
                //todo: send to server

                try{
                    FileInputStream fstream = new FileInputStream("data.txt");
                    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                    String strLine;
                    int i = 0;

//Read File Line By Line
                    while ((strLine = br.readLine()) != null)   {
                        // Print the content on the console
                        System.out.println (strLine);
                        i++;
                    }

//Close the input stream
                    br.close();
                    String Data = "";
                    if (i == 0){
                        Data = "1 1";
                    }
                    else{
                        int num = i+1;
                        Data = String.valueOf(num) +" 1";
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt", true));
                    writer.write(Data);
                    writer.newLine();
                }
                catch (java.io.FileNotFoundException e){
                    e.printStackTrace();
                }
                catch (java.io.IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
