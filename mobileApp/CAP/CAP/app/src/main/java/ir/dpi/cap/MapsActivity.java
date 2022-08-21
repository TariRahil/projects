package ir.dpi.cap;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.logging.Handler;

import ir.dpi.cap.Entities.Car;
import ir.dpi.cap.Entities.Driver;
import ir.dpi.cap.Entities.Trip;
import ir.dpi.cap.Entities.User;

import static android.content.ContentValues.TAG;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraIdleListener, AsyncResponse, PlaceSelectionListener, View.OnClickListener {
    String app_server_url = "http://62.193.13.135/test/";
    volatile int a = 0;
    String status = "";
    List<Driver> driverList = new ArrayList<Driver>();
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    Marker sourceMarker, destinationMarker;
    TextView txtSource, txtDest, txtCarNum,txtWaiting, txttimer;
    View view1, view2;
    LinearLayout llCarNum, llSource, llDest, llRequest;
    List<Car> carList = new ArrayList<Car>();
    List<Marker> carMarkerList = new ArrayList<Marker>();
    Button btnRequest,btnReject;
    DriverFragment driverFragment;
    long startTime = -1,tid=0;
    int index = 0,ct=0;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(35.5590784, 51.0934209), new LatLng(35.8345498, 51.6062163));
    private static final int REQUEST_SELECT_PLACE = 1;
    Menu myMenu;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        txtSource = (TextView) findViewById(R.id.txtSource);
        txtDest = (TextView) findViewById(R.id.txtDest);
        txtCarNum = (TextView) findViewById(R.id.txtCarNum);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        llCarNum = (LinearLayout) findViewById(R.id.llCarNum);
        llSource = (LinearLayout) findViewById(R.id.llSource);
        llDest = (LinearLayout) findViewById(R.id.llDest);
        llRequest = (LinearLayout) findViewById(R.id.llRequest);
        btnRequest = (Button) findViewById(R.id.btnRequest);
        driverFragment = (DriverFragment) getSupportFragmentManager().findFragmentById(R.id.fragDriver);
        txtWaiting=(TextView)findViewById(R.id.txtWaiting);
        btnReject=(Button)findViewById(R.id.btnReject) ;
        btnRequest.setOnClickListener(this);
        btnReject.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {
                case R.id.btnRequest:
                   CapRequest();

                    break;
                case R.id.btnReject:
                CapReject();
                    break;

            }
            } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void CapRequest()
        {
            if (carList.size() != 0) {

                Car nearestCar = carList.get(0);
                float[] results = new float[carList.size()];
                for (Car item : carList) {
                    Location.distanceBetween(item.getLatitude(), item.getLongitude(), sourceMarker.getPosition().latitude, sourceMarker.getPosition().longitude, results);
                }

                float min = results[0];
                for (int i = 0; i < results.length; i++) {
                    if (results[i] < min) {
                        min = results[i];

                        nearestCar = carList.get(i);
                    }
                }
                for (int i = 0; i < carList.size(); i++) {
                    ((Car) carList.get(i)).setDistance(results[i]);

                }
                Collections.sort(carList);
                String DestinationAdr = getAddress(destinationMarker.getPosition().latitude, destinationMarker.getPosition().longitude);
                String SourceAdr = getAddress(sourceMarker.getPosition().latitude, sourceMarker.getPosition().longitude);
                txtWaiting.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.VISIBLE);
                btnRequest.setVisibility(View.GONE);
                AddTrip(String.valueOf(Utility.getPreferences(this, "UserId")), String.valueOf(carList.get(0).getUserId()), SourceAdr, DestinationAdr, Utility.getPreferences(this, "PassengerName"), Utility.isStayStatus.NotStay.toString(), Utility.tripStatus.AddByPassenger.toString(), String.valueOf(sourceMarker.getPosition().latitude), String.valueOf(sourceMarker.getPosition().longitude), String.valueOf(destinationMarker.getPosition().latitude), String.valueOf(sourceMarker.getPosition().longitude));

            } else {

                Toast.makeText(this, "متاسفانه هیچ ماشینی در نزدیکی شما نیست.", Toast.LENGTH_LONG).show();
            }
        }
    private  void CapReject()
    {

        UpdateTrip(String.valueOf(tid),Utility.tripStatus.RejectByPassenger.toString(),"RejectByPassenger");

    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
//        requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); by zahra
//        requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); by zahra
        mLastLocation = location;
        if (sourceMarker != null) {
            sourceMarker.remove();
        }
        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("source");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.source_map_icon));
        sourceMarker = mMap.addMarker(markerOptions);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraIdleListener(this);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //stop location updates

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        setSourceAndCarNum();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onCameraMove() {
        if (destinationMarker == null)
            sourceMarker.setPosition(mMap.getCameraPosition().target);
        else
            destinationMarker.setPosition(mMap.getCameraPosition().target);
    }

    @Override
    public void onCameraIdle() {
        if (destinationMarker == null)
            setSourceAndCarNum();
        else
            txtDest.setText(getAddress(destinationMarker.getPosition().latitude, destinationMarker.getPosition().longitude));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        getAddress(marker.getPosition().latitude, marker.getPosition().longitude);
        LatLng latLng = new LatLng(marker.getPosition().latitude + 0.003, marker.getPosition().longitude + 0.003);
        if (marker.getTitle().equals("source")) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("dest");
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination_map_icon));
            destinationMarker = mMap.addMarker(markerOptions);
            mMap.setOnMarkerClickListener(this);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            view1.setVisibility(View.GONE);
            llCarNum.setVisibility(View.GONE);
            view2.setVisibility(View.VISIBLE);
            llDest.setVisibility(View.VISIBLE);

            txtSource.setText(getAddress(marker.getPosition().latitude, marker.getPosition().longitude));
            txtDest.setText(getAddress(marker.getPosition().latitude + 0.003, marker.getPosition().longitude + 0.003));
        } else if (marker.getTitle().equals("dest")) {
            view2.setVisibility(View.GONE);
            llSource.setVisibility(View.GONE);
            llDest.setVisibility(View.GONE);
            llRequest.setVisibility(View.VISIBLE);
            MenuItem item = myMenu.findItem(R.id.search);
            item.setVisible(false);
            mMap.getUiSettings().setScrollGesturesEnabled(false);
        }
        return true;
    }

    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String address = obj.getThoroughfare();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void processFinish(String output) {

     switch (status) {
            case "activeCarsNumber":
                setSourceAndCarNum_Process(output);
                break;
            case "AddTrip":
                if(output!="-1") {
                   tid=Long.parseLong(output);
                    sendNotication(carList.get(0),tid, "AddTrip");
                   // Toast.makeText(MapsActivity.this, output, Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MapsActivity.this, "بروز خطا در ثبت سفر", Toast.LENGTH_LONG).show();

                }
                break;
            case "GetTrip1":
                ProcessTrip(output,status);
                break;
            case "GetTrip2":
                ProcessTrip(output,status);
                break;
            case  "DontAcceptByAnyDrivers":
                Toast.makeText(this, " متاسفانه هیچ راننده ای درخواست شما را قبول نکرد.", Toast.LENGTH_LONG).show();
                btnRequest.setVisibility(View.VISIBLE);
                txtWaiting.setVisibility(View.GONE);
                btnReject.setVisibility(View.GONE);
                break;
         case "RejectByPassenger":
             Toast.makeText(this, " سفر شما را با موفقیت لغو گردید", Toast.LENGTH_LONG).show();
             Intent intent = new Intent(this, MapsActivity.class);
             //intent.putExtra("noTripMessage",getResources().getString(R.string.noTripMessage));
             startActivity(intent);
             break;

            default:
                Toast.makeText(this,"status="+status, Toast.LENGTH_LONG).show();
                break;



        }
    }

    private void ProcessTrip(String output,String stat) {
        try {
          //  index  = index+1;

            JSONObject jsonObject = new JSONObject(output);
            Trip trip= Trip.ToTrip2(jsonObject);

          if(stat=="GetTrip1") {
              if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.AcceptByDriver.toString())) {
                  driverFragment.setData(trip.getDriverName(), trip.getDriverMobile(), trip.getMachineModel() + " " + trip.getCarColor() + " " + trip.getNumberPlate());
                  driverFragment.getView().setVisibility(View.VISIBLE);
                  llRequest.setVisibility(View.GONE);
                  //GetTrip(String.valueOf(trip.getUserId()), "0", Utility.tripStatus.AcceptByDriver.toString(), String.valueOf(trip.getTripId()), "GetTrip2");
                  GetTrip( "0", String.valueOf(trip.getTripId()), "GetTrip2");

              }
//             else if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.RejectByDriver.toString()))
//              {
//
//                  driverFragment.getView().setVisibility(View.GONE);
//                  llRequest.setVisibility(View.VISIBLE);
//                  txtWaiting.setVisibility(View.GONE);
//                  btnReject.setVisibility(View.GONE);
//                  btnRequest.setVisibility(View.VISIBLE);
//                  Toast.makeText(MapsActivity.this, "متاسفانه سفر شما توسط راننده لغو گردید.لطفا دوباره درخواست  بدهید", Toast.LENGTH_LONG).show();
//
//
//              }
              else {


                  long endTime = System.currentTimeMillis() + 6000;
                  long seconds = (endTime - startTime) / 1000;
                  if (seconds > 40) {
                      index++;
                      if (index >= carList.size()) {
                          driverFragment.getView().setVisibility(View.GONE);
                          llRequest.setVisibility(View.VISIBLE);
                         UpdateTrip(String.valueOf(trip.getTripId()),Utility.tripStatus.DontAcceptByAnyDriver.toString(),"DontAcceptByAnyDrivers");
                      } else {

                          sendNotication(carList.get(index), trip.getTripId(), "AddTrip");
                      }
                  } else {

                      //GetTrip(String.valueOf(trip.getUserId()), String.valueOf(trip.getDriverId()), Utility.tripStatus.AcceptByDriver.toString(), String.valueOf(trip.getTripId()));
                      GetTrip( "0", String.valueOf(trip.getTripId()), "GetTrip1");
                  }
              }
          }
            else if(stat=="GetTrip2")
          {

              if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.RejectByDriver.toString()))
              {

                  driverFragment.getView().setVisibility(View.GONE);
                  llRequest.setVisibility(View.VISIBLE);
                  txtWaiting.setVisibility(View.GONE);
                  btnReject.setVisibility(View.GONE);
                  btnRequest.setVisibility(View.VISIBLE);
                  Toast.makeText(MapsActivity.this, "متاسفانه سفر شما توسط راننده لغو گردید.لطفا دوباره درخواست  بدهید", Toast.LENGTH_LONG).show();


              }

              else if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.AcceptByDriver.toString()))
              {
                  GetTrip( "0", String.valueOf(trip.getTripId()), "GetTrip2");

              }
              else if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.ArriveCap.toString()))
              {
                 if(ct==0) {
                     Toast.makeText(MapsActivity.this, "کپ شما رسید", Toast.LENGTH_LONG).show();
                     ct++;
                 }
                  GetTrip( "0", String.valueOf(trip.getTripId()), "GetTrip2");

              }
              else if (trip.getStatus() == Integer.parseInt(Utility.tripStatus.End.toString()))
              {
                  Toast.makeText(MapsActivity.this, "خاتمه سفر.امیدواریم سفر خوبی را با کپ تجربه کرده باشید.", Toast.LENGTH_LONG).show();
                 ct=0;
                  Intent intent = new Intent(this, MapsActivity.class);
                  startActivity(intent);

              }
          }

          }

        catch( Exception e  )

        {
          Toast.makeText(this, e.getMessage()+"خطا", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPlaceSelected(Place place) {
        LatLng latLng = place.getLatLng();
       /* Toast.makeText(this, "place.getAddress: " +String.valueOf(place.getLatLng()),Toast.LENGTH_LONG).show();*/
        if (destinationMarker == null) {
            sourceMarker.setPosition(latLng);
        } else {
            destinationMarker.setPosition(latLng);
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onError(Status status) {
        Log.e("LOG_TAG", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trips_log:
                break;
            case R.id.edit_info:
                Intent intentEditInfo = new Intent(this, EditInfoActivity.class);
                startActivity(intentEditInfo);
                break;
            case R.id.config:
                break;
            case R.id.about:
                break;
            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.menu_exit))
                        .setMessage(getResources().getString(R.string.menu_exit_message))
                        .setPositiveButton(getResources().getString(R.string.menu_positive_btn), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                moveTaskToBack(true);
                            }
                        }).setNegativeButton(getResources().getString(R.string.menu_negetive_btn), null)
                        .show();
                break;
            case R.id.search:
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(Place.TYPE_LOCALITY)
                            .setCountry("IR")
                            .build();
                    Intent intent = new PlaceAutocomplete.IntentBuilder
                            (PlaceAutocomplete.MODE_OVERLAY)
                            .setBoundsBias(new LatLngBounds(
                                    new LatLng(35.5590784, 51.0934209), new LatLng(35.8345498, 51.6062163)))
                            // .setFilter(typeFilter)
                            .build(MapsActivity.this);
                    startActivityForResult(intent, REQUEST_SELECT_PLACE);
                } catch (GooglePlayServicesRepairableException |
                        GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                this.onError(status);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendNotification(String id) {
        try {


            status = "sendNotificationToCar";
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
            String function = String.format("GetUserById/%s", id);
            function = function.replace(" ", "%20");
            asyncTaskCallWCF.execute(function);


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

//    private void sendNotification_Process(String output) {
//        try {
//            JSONObject jsonObject = new JSONObject(output);
//            final User driver = new User(Long.parseLong(jsonObject.getString("UserId")), jsonObject.getString("TokenId"), jsonObject.getString("UserName"));
//            final String token = driver.getTokenId();
//            Log.d(TAG, "Token" + token);
//            // Toast.makeText(MapsActivity.this,"tokenID="+token,Toast.LENGTH_LONG).show();
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            // Toast.makeText(MapsActivity.this,"بعد از ارسال نوتیفیکیشن",Toast.LENGTH_LONG).show();
//
//                            // DriverFragment driverFragment = (DriverFragment) getSupportFragmentManager().findFragmentById(R.id.fragDriver);
//                            //Toast.makeText(this, "getFirstName: " +driver.getFirstName(),Toast.LENGTH_LONG).show();
//                            //  driverFragment.setData("مستوره" + " " + "نوروزی","09125257599", "پراید " + " " + "سفید" + " " + "ایران 10 58ب452");
//                          //  DriverFragment driverFragment = (DriverFragment) getSupportFragmentManager().findFragmentById(R.id.fragDriver);
//                            driverFragment.setData("مستوره" + " " + "حسینی", "09125257599", "پراید " + " " + "سفید" + " " + "ایران 10 58ب452");
//                            driverFragment.getView().setVisibility(View.VISIBLE);
//
//
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("fcm_token", token);
//                    params.put("userName", driver.getUserName());
//                    params.put("version", "Passenger");
//                    return params;
//                }
//            };
//
//            MySingelton.getmInstance(MapsActivity.this).addToRequestque(stringRequest);
//            Utility.setPreferences(this, "sendnotify", "true");
////                a=1;
////                if(a==1) {
////                   // Toast.makeText(MapsActivity.this, "بعد از ارسال نوتیفیکیشن" + a, Toast.LENGTH_LONG).show();
////                    //getInfoDriver(nearestCar.getUserId().toString());
////                    DriverFragment driverFragment = (DriverFragment) getSupportFragmentManager().findFragmentById(R.id.fragDriver);
////                    driverFragment.getView().setVisibility(View.VISIBLE);
////                    getInfoDriver("4");
////                }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    private void sendNotication(final Car car, final Long tripId, String stat) {

        final String token = car.getTokenId();
        final String username = car.getUserName();
        final String passengerid=String.valueOf(Utility.getPreferences(this, "UserId"));
        Log.d(TAG, "Token" + token);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

//                        driverFragment.setData(car.getName(), car.getMobile(), car.getMachineModel() + " " + car.getCarColor() + " " + car.getNumberPlate());
//                        driverFragment.getView().setVisibility(View.VISIBLE);
                      //Utility.tripStatus.AcceptByDriver.toString()

                      //  if(startTime == -1) {
                            startTime = System.currentTimeMillis();

                       // }
                      //  Toast.makeText(MapsActivity.this, "onResponse"+startTime, Toast.LENGTH_LONG).show();

                        //GetTrip(passengerid,String.valueOf(car.getUserId()),Utility.tripStatus.AcceptByDriver.toString(),String.valueOf(tripId),"GetTrip1");
                        GetTrip(String.valueOf(car.getUserId()),String.valueOf(tripId),"GetTrip1");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fcm_token", token);
                params.put("userName", username);
                params.put("version", "Passenger");
                return params;
            }
        };

        MySingelton.getmInstance(MapsActivity.this).addToRequestque(stringRequest);
        //Utility.setPreferences(this, "sendnotify", "true");
//                a=1;
//                if(a==1) {
//                   // Toast.makeText(MapsActivity.this, "بعد از ارسال نوتیفیکیشن" + a, Toast.LENGTH_LONG).show();
//                    //getInfoDriver(nearestCar.getUserId().toString());
//                    DriverFragment driverFragment = (DriverFragment) getSupportFragmentManager().findFragmentById(R.id.fragDriver);
//                    driverFragment.getView().setVisibility(View.VISIBLE);
//                    getInfoDriver("4");
//                }


    }

    private void AddTrip(String userId, String driverId, String sourceAddress, String destinationAddress, String passangerNames, String isStay, String stat, String sourceLatitude, String sourceLongitude, String destinationLatitude, String destinationLongitude) {
        try {
            status = "AddTrip";
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
           // String s = destinationAddress.replace(" ", "%20");
            String des= URLEncoder.encode(destinationAddress, "utf-8");
            String source= URLEncoder.encode(sourceAddress, "utf-8");
            String function = String.format("AddTrip/%s/%s/%s/%s/%s/%s/%s/%s/%s/%s/%s", userId, driverId, source, des, passangerNames, isStay, stat,sourceLatitude,sourceLongitude,destinationLatitude,destinationLongitude);
            //Toast.makeText(MapsActivity.this,"des "+ destinationAddress+"source"+sourceAddress, Toast.LENGTH_LONG).show();
           Log.d(TAG,"addtrip:"+function);
            function = function.replace(" ", "%20");
            function = function.replace("+", "%20");
            asyncTaskCallWCF.execute(function);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void GetTrip(String driverId,String tripId,String stat)
    {
        try {

            new CountDownTimer(5000, 1000) {

                public void onTick(long millisUntilFinished) {

                }
                public void onFinish() { }
            }.start();
            //status = "GetTrip1";
            status=stat;
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
           // String function = String.format("GetTrip/%s/%s/%s", driverId,userId, tripstat);
            String function = String.format("GetTrip/%s/%s", tripId,driverId);
            function = function.replace(" ", "%20");
            asyncTaskCallWCF.execute(function);

        } catch (Exception e) {
            Log.d("LOG_TAG2", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateTrip(String tripId,String tripStat,String updateStat)
    {
           try
           {
               status =updateStat;
               //if(updateStat=="DontAcceptByAnyDrivers") {

                   AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
                   asyncTaskCallWCF.delegate = this;
                   String function = String.format("UpdateTrip/%s/%s", tripId, tripStat);
                   function = function.replace(" ", "%20");
                   asyncTaskCallWCF.execute(function);


        } catch (Exception e) {
            Log.d("LOG_TAG2", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void setSourceAndCarNum() {
        status = "activeCarsNumber";
        txtSource.setText(getAddress(sourceMarker.getPosition().latitude, sourceMarker.getPosition().longitude));
        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        // Toast.makeText(this, "latitude: " +sourceMarker.getPosition().latitude+"     longitude: " +sourceMarker.getPosition().longitude,Toast.LENGTH_LONG).show();
        String function = String.format("GetByLocation/%s/%s", sourceMarker.getPosition().latitude, sourceMarker.getPosition().longitude);
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }

    private void setSourceAndCarNum_Process(String output) {
        try {

            if (carMarkerList.size() > 0) {
                for (Marker marker : carMarkerList) {
                    marker.remove();
                }
                carMarkerList.clear();
            }

            JSONArray jsonArray = new JSONArray(output);
            carList = new ArrayList<Car>();
            carMarkerList = new ArrayList<Marker>();
            for (int i = 0; i < jsonArray.length(); i++) {
                //carList.add(Car.ToCar((JSONObject) jsonArray.get(i)));
                carList.add(Car.ToCar2((JSONObject) jsonArray.get(i)));

                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(carList.get(i).getLatitude(), carList.get(i).getLongitude());
                markerOptions.position(latLng);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.car_icon));
                Marker m = mMap.addMarker(markerOptions);
                carMarkerList.add(m);
            }
            txtCarNum.setText(String.valueOf(carList.size()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        AppIndex.AppIndexApi.start(client2, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client2, getIndexApiAction());
        client2.disconnect();
    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}