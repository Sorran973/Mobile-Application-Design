package com.example.practice3;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyServiceMediaPlayer extends Service {

    private static final String LOG_TAG = "MyService_Media_Player";
    private MediaPlayer mediaPlayer;

    public MyServiceMediaPlayer() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.sample);
        mediaPlayer.setLooping(true);
        Log.d(LOG_TAG, "Service is created");
        Toast.makeText(this, "Service is created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        Log.d(LOG_TAG, "Service is started");
        Toast.makeText(this, "Service is started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service is destroyed", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
        Log.d(LOG_TAG, "Service is destroyed");
    }
}