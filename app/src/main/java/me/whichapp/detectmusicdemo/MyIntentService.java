package me.whichapp.detectmusicdemo;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class MyIntentService extends Service
{

    private static final int SECONDS = 10;

    private static final String TAG = "MyIntentService";
    public static final String TRACK_KEY = "track";

    public static final String PLAYING_KEY = "playing";

    private static boolean flag = false;

    private static String track;


    CountDownTimer timer = new CountDownTimer(SECONDS * 1000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            flag = true;
            Log.d(TAG, millisUntilFinished + " milliseconds remaining");
        }

        @Override
        public void onFinish()
        {
            flag = false;
            Toast.makeText(MyIntentService.this, track, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(intent.getBooleanExtra(PLAYING_KEY, false))
        {
            track = intent.getStringExtra(TRACK_KEY);
            onHandleIntent();
        }
        else
        {
            Log.d(TAG, "Music Paused");
            timer.cancel();
        }
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


    protected void onHandleIntent()
    {
        if(!flag)
        timer.start();
        else
        {
            timer.cancel();
            timer.start();
        }
    }
}
