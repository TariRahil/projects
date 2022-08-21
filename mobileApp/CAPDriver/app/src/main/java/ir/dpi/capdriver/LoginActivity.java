package ir.dpi.capdriver;


import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import ir.dpi.capdriver.Entities.User;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,AsyncResponse
{
    EditText txtEmail,txtPassword;
    Button btnLogin;
    String app_server_url = "http://62.193.13.135/test/";

    String email;
    String password;
    String flag;
    GPSTracker gpsTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (isInternetOn())
        {
            email = txtEmail.getText().toString().trim();
            password = txtPassword.getText().toString().trim();
            if (email == null || email.isEmpty() || password == null || password.isEmpty())
            {
                new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getResources().getString(R.string.activity_login_noInternet_title))
                        .setMessage(getResources().getString(R.string.activity_login_noInternet_message))
                        .setPositiveButton(getResources().getString(R.string.activity_login_noInternet_positive_btn), null).show();
            }
            else
            {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);

                final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");

                StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("Response", response.toString());
                    }
                }, new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Response", error.toString());
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("fcm_token", token);
                        params.put("userName", email);
                        params.put("passWord", password);
                        params.put("version", "Driver");

                        return params;
                    }
                };
                MySingelton.getmInstance(LoginActivity.this).addToRequestque(stringRequest);


                getUserId();
                //Toast.makeText(this,"USERID==" + Utility.getPreferences(this, "UserId"), Toast.LENGTH_LONG).show();
           /* if (Utility.getPreferences(this, "UserId")!=null)
            {
                flag = "TripIsAvalabe";
                AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
                asyncTaskCallWCF.delegate = this;
                String function = String.format("IsAvalableTrip/%s", Utility.getPreferences(this, "UserId"));
                function = function.replace(" ", "%20");
                asyncTaskCallWCF.execute(function);
            }*/


            }
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.activity_signup_alert1_title))
                    .setMessage(getResources().getString(R.string.activity_signup_alert1_message))
                    .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn), null)
                    .show();
        }
    }

    /*private void getCarStatus()
    {
        flag = "getCarStatus";
        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        String function = String.format("GetByDriverId/%s",Utility.getPreferences(this,"UserId"));
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }*/

    public void getUserId()
    {
        flag = "getUserId";
        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        String function = String.format("GetUser/%s/%s/%s/%s", email, password, "3", "null");
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }

    @Override
    public void processFinish(String output)
    {

        try
        {
            if (output=="" || output== null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_login_alert2_title))
                        .setMessage(getResources().getString(R.string.activity_login_alert2_message))
                        .setPositiveButton(getResources().getString(R.string.activity_login_alert_positive_btn),null).show();
            }
            else if (flag.equals("getUserId"))
                {
                    JSONObject jsonObject = new JSONObject(output);
                    String userId=String.valueOf(jsonObject.getLong("UserId"));
                    Utility.setPreferences(this, "UserId",userId );

                    GPSTracker  gps = new GPSTracker(LoginActivity.this);
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    // \n is for new line
                    //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + String.valueOf(latitude) + "\nLong: " + String.valueOf(longitude), Toast.LENGTH_LONG).show();

                       updateLocation(userId,latitude,longitude);
                    //***********commnted by zahra ***************
//                    Intent intent = new Intent(this, WellcomePage.class);
//                    startActivity(intent);
                    //******************
                   // getCarStatus();

                }
            else if(flag.equals("updateLocation"))
        {
            Intent intent = new Intent(this, WellcomePage.class);
            startActivity(intent);
        }
           /* else if (flag.equals("getCarStatus"))
            {
                JSONObject jsonObject = new JSONObject(output);
                Utility.setPreferences(this, "CarStatus", String.valueOf(jsonObject.getInt("Status")));
                if (jsonObject.getInt("Status") == 1)
                {
                    Intent intent = new Intent(this, DriverNotify.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(this, WellcomePage.class);
                    startActivity(intent);
                }
            }*/
           /* else if (flag.equals("TripIsAvalabe"))
                {
                    *//*Toast.makeText(this,"OUTPUT=>"+output, Toast.LENGTH_LONG).show();
                    Toast.makeText(this,"PROCESSFINISH_TripIsAvalabe", Toast.LENGTH_LONG).show();*//*

                    //JSONObject jsonObject = new JSONObject(output);
                    if (Boolean.parseBoolean(output))//trip not available for this driver
                    {
                        Intent intent = new Intent(this, WellcomePage.class);
                        startActivity(intent);
                    }
                    else if (!Boolean.parseBoolean(output))
                    {
                        Intent intent = new Intent(this, DriverNotify.class);
                       startActivity(intent);
                    }
                }*/
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    // **********for location by zahra************
    public void updateLocation(String driverId, double latitude, double longitude)
    {
        //UpdateCarLocation(string driverId, string latitude, string longitude)
        flag = "updateLocation";
//        if (Build.VERSION.SDK_INT >= 23) {
//
//            if (checkSelfPermission(mPermission) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(LoginActivity.this,
//                        new String[]{mPermission,
//                        },
//                        REQUEST_CODE_PERMISSION);
//                return;
//            }
//        }
//        GPSTracker  gps = new GPSTracker(LoginActivity.this);
//        // check if GPS enabled
//        //if(gps.canGetLocation()){
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//            // \n is for new line
//           Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + String.valueOf(latitude) + "\nLong: " + String.valueOf(longitude), Toast.LENGTH_LONG).show();
       // }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
         //   gps.showSettingsAlert();
      //  }

        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;

        String function = String.format("UpdateCarLocation/%s/%s/%s", driverId,latitude ,longitude);

        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
   }

    public final boolean isInternetOn() {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                // Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                // Toast.makeText(this, activeNetwork.getTypeName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;

    }
}
