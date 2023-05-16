package com.example.procalendar.Calendar_Alarm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.procalendar.MainActivity;
import com.example.procalendar.MyDataBase;
import com.example.procalendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MyMusic extends Service
{
    MediaPlayer mediaPlayer;
    PendingIntent pendingIntent;
    NotificationManager notificationManager;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.nhacchuong);
        mediaPlayer.start();
        MyDataBase myDataBase = new MyDataBase(this);
        Calendar c = Calendar.getInstance();
        String now = c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.YEAR) + " - "
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
        for (int i = 0; i < myDataBase.getNotesCount(); i++)
        {
            String dateTime = myDataBase.getData(i).getDate();
            if (dateTime.equals(now))
            {
                addNotification(myDataBase.getData(i));
                break;
            }
        }
        return START_NOT_STICKY;
    }
    private void addNotification(WorkAlarm workAlarm)
    {
        String id = "_channel_01";
        int importance = NotificationManager.IMPORTANCE_LOW;
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
            mChannel.enableLights(true);
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.ic_alarm)
                            .setContentTitle(workAlarm.getDate())
                            .setStyle(new NotificationCompat.BigTextStyle().bigText(workAlarm.getNote()))
                            .setContentText(workAlarm.getNote())
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
            builder.setContentIntent(pendingIntent);
            builder.setChannelId(id);
            notificationManager.createNotificationChannel(mChannel);
            Random random = new Random();
            int m = random.nextInt(9999 - 1000) + 1000;
            notificationManager.notify(m, builder.build());
        }
    }
}
