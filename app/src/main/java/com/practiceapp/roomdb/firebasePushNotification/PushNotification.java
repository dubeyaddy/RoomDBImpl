package com.practiceapp.roomdb.firebasePushNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.practiceapp.roomdb.R;

public class PushNotification extends AppCompatActivity {

    public static final String channel_1= "channel 1";
    public static final String channel_2= "channel 2";
    EditText title=findViewById(R.id.et_title);
    EditText message= findViewById(R.id.et_msg);
    EditText userToken= findViewById(R.id.et_token);
    public NotificationManagerCompat notificationManager;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        setTitle("firebase Push notification");

        notificationManager = NotificationManagerCompat.from(this);

//        LayoutInflater inflater= LayoutInflater.from(PushNotification.this);
//        View view= inflater.inflate(R.layout.activity_push_notification,null);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

//        EditText title=findViewById(R.id.et_title);
//        EditText message= findViewById(R.id.et_msg);
//        EditText userToken= findViewById(R.id.et_token);
        Button btn_sendAll= findViewById(R.id.btn_sendAll);

        btn_sendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()){

//                    FcmNotificationsSender notificationsSender= new FcmNotificationsSender("/topics/all",title.getText().toString(),message.getText().toString(),getApplicationContext(),PushNotification.this);
//                    notificationsSender.SendNotifications();
                    sendOnChannel1();

                }else{
                    Toast.makeText(PushNotification.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    channel_1,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    channel_2,
                    "channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel1.setDescription("This is channel 2");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
    public void sendOnChannel1(){

        Notification notification= new NotificationCompat.Builder(this,channel_1)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle((CharSequence) title)
                .setContentText((CharSequence) message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }
}
