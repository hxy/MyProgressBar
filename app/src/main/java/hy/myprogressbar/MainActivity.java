package hy.myprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MyProgressBar mpb;
    private int percent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mpb = (MyProgressBar)findViewById(R.id.pb);
//        mpb.setAchievedBarHeight(5);
//        mpb.setNoAchievedBarHeight(2);
//        mpb.setTextColor(0xff9F79EE);
//        mpb.setTextSize(26);
//        mpb.setAchievedBarColor(0xff6A5ACD);
//        mpb.setNoAchievedBarColor(0xff000000);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mpb.post(new Runnable() {
                    @Override
                    public void run() {
                        mpb.setPercent(++percent);
                        if(percent==100){
                            percent = 0;
                        }
                    }
                });
            }
        },1000,100);
    }
}
