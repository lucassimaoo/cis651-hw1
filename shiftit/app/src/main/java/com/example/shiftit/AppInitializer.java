package com.example.shiftit;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class AppInitializer extends Application {

    public static final String CHANNEL_ID = "app-channel";
    private static AppInitializer instance;

    public static Context getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "app-channel", importance);
            channel.setDescription("app-channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
