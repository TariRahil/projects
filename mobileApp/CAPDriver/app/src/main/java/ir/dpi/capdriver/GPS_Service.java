package ir.dpi.capdriver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by zahra on 5/5/2018.
 */

public class GPS_Service  extends Service {

    private LocationListener listener;
    private LocationManager locationManager;
    String processFlag="";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocation(location.getLatitude(),location.getLongitude());

                Intent i = new Intent("location_update");
                i.putExtra("coordinates",location.getLongitude()+" "+location.getLatitude());

                sendBroadcast(i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null){
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }
    public void processFinish(String output)
    {
//        if(processFlag=="updateLocation")
//        {
//            Intent intent = new Intent(this, DriverNotify.class);
//            startActivity(intent);
//        }
//        else if(processFlag=="NoTrip")
//        {
//            Intent intent = new Intent(this, WellcomePage.class);
//            startActivity(intent);
//
//        }
//        else {
//            Intent intent = new Intent(this, WellcomePage.class);
//            startActivity(intent);
//
//        }

    }
    public void updateLocation(double latitude,double longitude )
    {
        processFlag = "updateLocation";
      //  GPSTracker  gps = new GPSTracker(LoginActivity.this);
        // check if GPS enabled
        //if(gps.canGetLocation()){

       String driverId= Utility.getPreferences(this, "UserId" );
//        double latitude = gps.getLatitude();
//        double longitude = gps.getLongitude();
        // \n is for new line
      //  Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        // }else{
        // can't get location
        // GPS or Network is not enabled
        // Ask user to enable GPS/network in settings
        //   gps.showSettingsAlert();
        //  }

        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = (AsyncResponse) this;

        String function = String.format("UpdateCarLocation/%s/%s/%s", driverId,latitude ,longitude);
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }
}
