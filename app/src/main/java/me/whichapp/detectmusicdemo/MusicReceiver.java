package me.whichapp.detectmusicdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MusicReceiver extends BroadcastReceiver
{
    private static final String TAG = "MusicReceiver";

    public static final String TOGGLEPAUSE_ACTION = "com.android.music.musicservicecommand.togglepause";
    public static final String PAUSE_ACTION = "com.android.music.musicservicecommand.pause";
    public static final String PREVIOUS_ACTION = "com.android.music.musicservicecommand.previous";
    public static final String NEXT_ACTION = "com.android.music.musicservicecommand.next";

    public static final String META_CHANGED_ACTION = "com.android.music.metachanged";
    public static final String PLAYBACK_COMPLETE_ACION = "com.android.music.playbackcomplete";
    public static final String QUEUE_CHANGED_ACTION = "com.android.music.queuechanged";
    public static final String PLAY_STATE_CHANGED_ACTION = "com.android.music.playstatechanged";

    public static final String MUSIX_META_CHANGED_ACTION = "com.musixmatch.android.lyrify.metachanged";
    public static final String MUSIX_PLAY_STATE_CHANGED_ACION = "com.musixmatch.android.lyrify.playstatechanged";

    public static final String ES_META_CHANGED_ACTION = "com.estrongs.android.pop.metachanged";
    public static final String ES_PLAY_STATE_CHANGED_ACION = "com.estrongs.android.pop.playstatechanged";

    public MusicReceiver()
    {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String cmd = intent.getStringExtra("command");
        Log.v(TAG, action + " / " + cmd);
        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String track = intent.getStringExtra("track");
        Log.v(TAG, artist + ":" + album + ":" + track);
        Toast.makeText(context, track, Toast.LENGTH_SHORT).show();
    }
}
