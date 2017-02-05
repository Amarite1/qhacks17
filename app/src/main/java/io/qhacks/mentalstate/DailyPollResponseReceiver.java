package io.qhacks.mentalstate;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.InputStreamReader;



public class DailyPollResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(DailyPollManager.NOTIFICATION_ID);

        int responseType = intent.getExtras().getInt("type");
        Log.d("DPRR", "response: " + responseType);

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
