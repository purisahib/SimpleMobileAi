package com.pradeep.psai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.pradeep.psai.gotopast.GotoActivity;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartNotification startNotification=new StartNotification();
        Context context=MainActivity.this;


    }

    @Override
    protected void onStart() {
        super.onStart();
        //parentActivity = getActivity();
        Intent mIntent = new Intent(MainActivity.this,GotoActivity.class);
        startActivity(mIntent);
        //private Activity parentActivity=getActiv;
    }
}
