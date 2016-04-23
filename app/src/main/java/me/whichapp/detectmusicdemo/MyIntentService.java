package me.whichapp.detectmusicdemo;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


public class MyIntentService extends Service
{

    private static final int SECONDS = 10;

    private static final String TAG = "MyService";
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
        Log.v(TAG, "Music Detect Service start");
        if(intent != null)
        {
            if(intent.getBooleanExtra(PLAYING_KEY, false))
            {
                track = intent.getStringExtra(TRACK_KEY);
                if(track == null || track.isEmpty())
                {
                    Log.i(TAG, "Empty track name from intent, loading filename");
                    Cursor cursor = getContentResolver().query(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Audio.Media.DISPLAY_NAME},
                            MediaStore.Audio.Media._ID + " = ?", new String[]{String.valueOf( intent.getLongExtra("id", -1))}, null);
                    if(cursor != null && cursor.getCount() > 0)
                    {
                        cursor.moveToNext();
                        track = cursor.getString(0);
                        cursor.close();
                    }
                }
                onHandleIntent();
            }
            else
            {
                Log.d(TAG, "Music Paused");
                timer.cancel();
            }
        }
        return super.onStartCommand(intent, flags, startId);
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
            flag = false;
            timer.start();
        }
    }
}
