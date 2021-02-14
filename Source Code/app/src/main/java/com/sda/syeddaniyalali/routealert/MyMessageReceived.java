package com.sda.syeddaniyalali.routealert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import static android.support.constraint.Constraints.TAG;


public class MyMessageReceived extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...


        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("TAG", "From: " + remoteMessage.getFrom());
       shownotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("Tag", "Message data payload: " + remoteMessage.getData());
        }
         }

    private void shownotifications(String title, String body) {
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_ID="com.sda.syeddaniyalali.routealert";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel(NOTIFICATION_ID,"Notifications",NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("My Channel");
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,NOTIFICATION_ID);
            builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.drawable.ic_launcher_background).setContentTitle(title).setContentText(body).setContentInfo("Info");
            notificationManager.notify(new Random().nextInt(),builder.build());
        }









}
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

    }

}
