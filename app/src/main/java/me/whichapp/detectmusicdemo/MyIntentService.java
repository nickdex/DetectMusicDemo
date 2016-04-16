package me.whichapp.detectmusicdemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends Service
{

    private static final String TAG = "MyIntentService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static boolean flag;
    private static long time = 0;
    private static long currentTime = 0;

    CountThread thread = new CountThread("Count Thread");
    private boolean show = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        onHandleIntent();
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
        try
        {
            Log.v(TAG, System.currentTimeMillis() - currentTime + " difference");
            if(System.currentTimeMillis() - currentTime < 10 * 1000)
            {
                show = false;
                Log.d(TAG, "Thread Stopped");
                return;
            }

            if(!thread.isAlive())
            {
                Log.v(TAG, "Count Started");
                show = true;
                thread.run();
            }

        }catch (IllegalThreadStateException e)
        {
            Log.d(TAG, "Everything's cool");
        }

    }

    final Handler mUiHandler = new Handler();

    class CountThread extends Thread {

        public CountThread(String threadName)
        {
            super(threadName);
        }

        @Override
        public void run() {
            int i;
            for (i = 0; i < 10; i++) {
               if(!show)
               {
                   break;
               }

               try {
                   Log.v(TAG, i+ " Seconds");
                   currentTime = System.currentTimeMillis();
                   Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mUiHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    show = false;
                    Log.i(TAG, "Time Complete. Proceed as required");
                }
            });
            if(!show)
            {
                Log.v(TAG, "Inside thread - loop exited");
            }
        }
    }
}
