package com.reinvent.foreground;

import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		Button start = findViewById(R.id.buttonStartService);
		Button stop = findViewById(R.id.buttonStopService);
		start.setOnClickListener(view->{
			startService();
		});
		stop.setOnClickListener(view->{
			stopService();
		});
   }
   
   public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", getText(R.string.notification_message));
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }
}