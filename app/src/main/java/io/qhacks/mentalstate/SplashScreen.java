package io.qhacks.mentalstate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Allan on 2017-02-04.
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
RunAnimation();
        Thread timerThread = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.anim1);
        a.reset();
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.clearAnimation();
        tv.startAnimation(a);

    }

}
