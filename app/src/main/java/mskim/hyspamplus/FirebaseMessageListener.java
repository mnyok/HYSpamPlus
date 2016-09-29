package mskim.hyspamplus;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by orc12 on 2016-09-29.
 */
public class FirebaseMessageListener extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map<String, String> data = message.getData();

        Log.i("FirebaseMessageData", data.toString());

        try {
            //data1: title, data2: link url
            sendPushNotification(data.get("data1"), data.get("data2"), data.get("data3"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // invoke notification and vibration
    // If user clicks notification, open link with default browser
    private void sendPushNotification(String title, String text, String link) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        browserIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, browserIntent, PendingIntent.FLAG_ONE_SHOT);

        // Notification viberate pattern
        // [0]: delay before vibrator on
        long[] vibePattern = new long[2];
        vibePattern[0] = 0;
        vibePattern[1] = 500;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.hy)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.spam))
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
//                .setLights(002074136, 500, 2000)
                .setTicker(text)
                .setVibrate(vibePattern)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
