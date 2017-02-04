package io.qhacks.mentalstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by benwi on 2017-02-04.
 */
public class DailyPollResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int responseType = intent.getExtras().getInt("type");
        switch(responseType){
            case 0:{ //good day
                //todo: send to server
                break;
            }
            case 1:{ //bad day
                //todo: send to server
                break;
            }
        }
    }
}
