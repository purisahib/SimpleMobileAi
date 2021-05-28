package com.pradeep.psai;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class StartNotification extends AppCompatActivity implements
        RecognitionListener{
    private static final String CHANNEL_ID ="channel_id01" ;
    public static final int NOTIFICATION_ID =1 ;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    PendingIntent mPendingIntent;
    TaskStackBuilder mTaskStackBuilder;
    Intent mResultIntent;
    static Context context1;


    public void showNotification(Context context, String getPackageName, NotificationManager systemService) {
        createNotificationChannel(systemService);
        context1=context;

        //inflating the views(custom_normal.xml and custom-expanded.xml)
        RemoteViews remoteCollapsedViews= new RemoteViews(getPackageName,R.layout.notification_layout);
        RemoteViews remoteExpandableViews= new RemoteViews(getPackageName,R.layout.notification_layout);

        //start this (mainActivity) on by trapping notification
        Intent mainIntent=new Intent(context,StartNotification.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent mainPIntent= PendingIntent.getActivity(context,0,mainIntent,PendingIntent.FLAG_ONE_SHOT);

        //click lick button to start likeActivity
        /*Intent likeIntent=new Intent(this,LikeActivity.class);
        likeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent likePIntent= PendingIntent.getActivity(this,0,likeIntent,PendingIntent.FLAG_ONE_SHOT);

        //click dislick button to start dislike Activity
        Intent dislikeIntent=new Intent(this,DislikeActivity.class);
        dislikeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent dislikePIntent= PendingIntent.getActivity(this,0,dislikeIntent,PendingIntent.FLAG_ONE_SHOT);*/


        NotificationCompat.Builder builder=new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Title pf Notification");
        builder.setContentText("This is the descrition of the notification");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setAutoCancel(true);
        builder.setContentIntent(mainPIntent);

        //custom style
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        builder.setCustomContentView(remoteCollapsedViews);
        builder.setCustomBigContentView(remoteExpandableViews);

        //icon will be display on 7 and above
        /*builder.addAction(R.drawable.ic_like,"Like",likePIntent);
        builder.addAction(R.drawable.ic_dislike,"Dislike",dislikePIntent);*/

        // show image in notification
        /*Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.logo);
        builder.setLargeIcon(bitmap);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null));*/

        //show extamble big description
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("ofhehvoneoi mrvgrb wy y rt h tdd te  hvtrr    g rh th  j  y oiioent4iu[iretnputnpvunyrvtv[yy9ttv"));

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel(NotificationManager systemService) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name="My Notification";
            String description = "My notification escription";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel= new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager= systemService;
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

//notifcation workng like
    //Audio to text
        private static final int REQUEST_RECORD_PERMISSION = 100;
        private TextView returnedText;
        private Button toggleButton;
        private ProgressBar progressBar;
        private SpeechRecognizer speech = null;
        private Intent recognizerIntent;
        private String LOG_TAG = "VoiceRecognitionActivity";


        @Override
        protected void onStart() {
            super.onStart();

            returnedText = (TextView) findViewById(R.id.textView1);
            progressBar = (ProgressBar) findViewById(R.id.progressBar1);
            //toggleButton = (Button) findViewById(R.id.toggleButton1);

            progressBar.setVisibility(View.INVISIBLE);
            speech = SpeechRecognizer.createSpeechRecognizer(this);
            Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
            speech.setRecognitionListener(this);
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "english");
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //recognizerIntent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault() );
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
            //toggleButton.setOnClickListener(new View.OnClickListener() {
                //@Override
                //public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    ActivityCompat.requestPermissions((Activity) context1,
                            new String[]{Manifest.permission.RECORD_AUDIO},
                            REQUEST_RECORD_PERMISSION);
                             toggleButton.setText("ON");

            //    }
           // });
        /*toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    ActivityCompat.requestPermissions
                            (MainActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    REQUEST_RECORD_PERMISSION);
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });*/
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_RECORD_PERMISSION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        speech.startListening(recognizerIntent);
                    } else {
                        Toast.makeText(context1, "Permission Denied!", Toast
                                .LENGTH_SHORT).show();
                    }
            }
        }
        @Override
        public void onResume() {
            super.onResume();
        }
        @Override
        protected void onPause() {
            super.onPause();
        }
        @Override
        protected void onStop() {
            super.onStop();
            if (speech != null) {
                speech.destroy();
                Log.i(LOG_TAG, "destroy");
            }
        }
        @Override
        public void onBeginningOfSpeech() {
            Log.i(LOG_TAG, "onBeginningOfSpeech");
            progressBar.setIndeterminate(false);
            progressBar.setMax(10);
        }
        @Override
        public void onBufferReceived(byte[] buffer) {
            Log.i(LOG_TAG, "onBufferReceived: " + buffer);
        }
        @Override
        public void onEndOfSpeech() {
            Log.i(LOG_TAG, "onEndOfSpeech");
            progressBar.setIndeterminate(true);
            //toggleButton.setChecked(false);
            toggleButton.setText("OFF");
        }
        @Override
        public void onError(int errorCode) {
            String errorMessage = getErrorText(errorCode);
            Log.d(LOG_TAG, "FAILED " + errorMessage);
            returnedText.setText(errorMessage);
            //toggleButton.setChecked(false);
            toggleButton.setText("OFF");
        }
        @Override
        public void onEvent(int arg0, Bundle arg1) {
            Log.i(LOG_TAG, "onEvent");
        }
        @Override
        public void onPartialResults(Bundle arg0) {
            Log.i(LOG_TAG, "onPartialResults");
        }
        @Override
        public void onReadyForSpeech(Bundle arg0) {
            Log.i(LOG_TAG, "onReadyForSpeech");
        }
        @Override
        public void onResults(Bundle results) {
            Log.i(LOG_TAG, "onResults");
            ArrayList<String> matches = results
                    .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String text = "";
            for (String result : matches)
                //text += result + "\n";
                text = result + "\n";
            returnedText.setText(text);
        }
        @Override
        public void onRmsChanged(float rmsdB) {
            Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
            progressBar.setProgress((int) rmsdB);
        }
        public static String getErrorText(int errorCode) {
            String message;
            switch (errorCode) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio recording error";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Client side error";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Insufficient permissions";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Network error";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Network timeout";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "No match";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RecognitionService busy";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "error from server";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "No speech input";
                    break;
                default:
                    message = "Didn't understand, please try again.";
                    break;
            }
            return message;
        }
}
