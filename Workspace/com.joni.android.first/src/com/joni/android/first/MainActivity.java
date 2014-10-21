package com.joni.android.first;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button btnShowLocation, btnStartLoc, btnStopLoc;
	
	// GPSTracker class
	GPSTracker gps;
	
	// Media Player
	static MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Run the stop gps initially just in case it was not stopped previously
        try{
        	AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        	if (alarmManager != null) {
        		Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                alarmManager.cancel(pendingIntent);
        	}
            
        } finally {
        	
        }
        
        btnShowLocation = (Button) findViewById(R.id.showLocation);
        btnStartLoc = (Button) findViewById(R.id.startLocation);
        btnStopLoc = (Button) findViewById(R.id.stopLocation);
        
        // show location button click event
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View arg0) {
        		// create class object
        		gps = new GPSTracker(MainActivity.this);
        		
        		// check if GPS enabled
        		if (gps.canGetLocation()) {
        			double latitude = gps.getLatitude();
        			double longitude = gps.getLongitude();
        			
        			// \n is for new line
        			Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        		} else {
        			// can't get location
        			// GPS or Network is not enabled
        			// Ask user to enable GPS/network in settings
        			gps.showSettingsAlert();
        		}
        	}
        });
        
        btnStartLoc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
		        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
		        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
		        // Repeating every 10 seconds
		        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10000, pendingIntent);
				
			}
		});
        
        btnStopLoc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
		        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
		        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
		        alarmManager.cancel(pendingIntent);
				
		        // reset playability
		        mPlayer = null;
			}
		});
        
        
    }
    
}
