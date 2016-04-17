package me.whichapp.detectmusicdemo;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MyIntentService extends Service
{

    private static final String TAG = "MyIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static boolean flag = false;

    private String track;


    CountDownTimer timer = new CountDownTimer(30 * 1000, 1000)
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
        AudioManager manager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if(manager.isMusicActive())
        {
            track = intent.getStringExtra("track");
            onHandleIntent();
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
