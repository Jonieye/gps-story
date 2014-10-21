package com.joni.android.first;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver
{
	// GPSTracker class
	GPSTracker gps;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		gps = new GPSTracker(context);
        // check if GPS enabled
		if (gps.canGetLocation()) {
			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();
			if (latitude <38 && latitude > 36 && longitude < -120 && longitude > -130) {
				//Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
				Toast.makeText(context.getApplicationContext(), "You're Home", Toast.LENGTH_LONG).show();
				//assetManager = context.getAssets();
				if (MainActivity.mPlayer == null) {
					MainActivity.mPlayer = MediaPlayer.create(context, R.raw.test02);
					MainActivity.mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
					MainActivity.mPlayer.start();
				}
			} else {
				Toast.makeText(context.getApplicationContext(), "You're not home", Toast.LENGTH_LONG).show();
			}
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}
}
