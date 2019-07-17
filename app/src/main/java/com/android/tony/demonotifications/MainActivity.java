package com.android.tony.demonotifications;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class MainActivity extends AppCompatActivity {
    final String channelId = "Tony";
    Button githubButton, linkedinButton, remoteButton;
    NotificationManager manager;
    Intent intent;
    PendingIntent pendingIntent;
    NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        githubButton = findViewById(R.id.githubbutton);
        linkedinButton = findViewById(R.id.linkedInButton);
        remoteButton = findViewById(R.id.remotebutton);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = getIntent();
        Bundle bundle = RemoteInput.getResultsFromIntent(intent);
        if(bundle!=null)
        {
            TextView textView = findViewById(R.id.notificationtextView);
            textView.setText(bundle.getCharSequence("Remote").toString());
        }

        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNotification("https://github.com/Tony3898", "Open Github Account");
            }
        });

        linkedinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNotification("https://www.linkedin.com/in/tejas-rana-668595128/", "Open LinkedIn Account");
            }
        });

        remoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myNotificationChannel();
                pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, new Intent(getApplicationContext(), MainActivity.class), 0);
                notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
                notificationBuilder.setContentIntent(pendingIntent);
                notificationBuilder.setSmallIcon(R.drawable.ic_android);
                notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.self));
                notificationBuilder.setContentTitle(getResources().getString(R.string.app_name));
                notificationBuilder.setContentText("Remote");
                notificationBuilder.setAutoCancel(true);
                notificationBuilder.setDefaults(Notification.DEFAULT_ALL);


                RemoteInput remoteInput = new RemoteInput.Builder("Remote").setLabel("Type Message...").build();
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_android, "Reply..", pendingIntent).addRemoteInput(remoteInput).build();
                notificationBuilder.addAction(action);

                manager.notify(1, notificationBuilder.build());
            }
        });


    }

    void generateNotification(String path, String msg) {
        myNotificationChannel();
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        //intent.setData(Uri.parse(path));
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.ic_android);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.self));
        notificationBuilder.setContentTitle(getResources().getString(R.string.app_name));
        notificationBuilder.setContentText(msg);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);

        manager.notify(1, notificationBuilder.build());
    }

    void myNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, "Developer Message", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("My Self Channel");
            notificationChannel.enableVibration(true);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
