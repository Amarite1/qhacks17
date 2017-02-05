package io.qhacks.mentalstate;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Calendar;


public class DailyPollResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(DailyPollManager.NOTIFICATION_ID);

        int responseType = intent.getExtras().getInt("type");
        Log.d("DPRR", "response: " + responseType);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonStr = prefs.getString("datajson", "[]");
        try {
            JSONArray json = new JSONArray(jsonStr);
            JSONObject obj = new JSONObject();
            obj.put("type", responseType);
            Calendar cal = Calendar.getInstance();
            obj.put("date", cal.get(Calendar.DAY_OF_MONTH)*1000000+cal.get(Calendar.MONTH)*10000+cal.get(Calendar.YEAR));
            json.put(obj);
            if(json.length() > 10) json.remove(0);
            prefs.edit().putString("datajson", json.toString()).apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
