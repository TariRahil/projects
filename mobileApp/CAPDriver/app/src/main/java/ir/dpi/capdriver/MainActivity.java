package ir.dpi.capdriver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements  AsyncResponse,View.OnClickListener {
    Button button;
    String app_server_url = "http://62.193.13.135/test/";
    private BroadcastReceiver broadcastReceiver;
    Button btnSignUp, btnLogin;
    TextView txtViewEnLan,txtViewFaLan;
    String lang,country,processFlag;
    private static final int REQUEST_CODE_PERMISSION = 1;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;

//Update Location in Service
//    protected void onResume() {
//        super.onResume();
//        if(broadcastReceiver == null){
//            broadcastReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//
//                    //textView.append("\n" +intent.getExtras().get("coordinates"));
//                    Toast.makeText(context, "Location:  "+String.valueOf(intent.getExtras().get("coordinates")), Toast.LENGTH_LONG).show();
//
//                }
//            };
//        }
//        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //********** for Exit zahra***********
        if(getIntent().getExtras() != null
                && getIntent().getExtras().getBoolean("CloseApp", false)) {
            finish();
        }
        //***************************************
       else if (Utility.getPreferences(this,"UserId")!=null &&  Utility.getPreferences(this,"UserId").length()!=0 )
        {

            showTrip();


        }

        else
        {
            setContentView(R.layout.activity_main);
            btnSignUp = (Button) findViewById(R.id.btnSignUp);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            txtViewEnLan = (TextView) findViewById(R.id.txtViewEnLan);
            txtViewFaLan = (TextView) findViewById(R.id.txtViewFaLan);
            btnSignUp.setOnClickListener(this);
            btnLogin.setOnClickListener(this);
            txtViewEnLan.setOnClickListener(this);
            txtViewFaLan.setOnClickListener(this);
        }





    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }
    public void processFinish(String output)
    {
        if(processFlag=="showTrip")
        {
            //zahra ****/
            try {
                JSONObject jsonObject = new JSONObject(output);
              Long tripid= Long.parseLong(jsonObject.getString("TripId"));
                if(tripid==0)
                {
                    Intent intent = new Intent(this, WellcomePage.class);
                    startActivity(intent);

                }
                else
                {
                    Intent intent = new Intent(this, DriverNotify.class);
                    startActivity(intent);
                }

            }
            catch (Exception ex)
            {
                Toast.makeText(this, "Exeption for showTrip  ------->"+ex.getMessage() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, WellcomePage.class);
                startActivity(intent);

            }
            //**************



        }
        else if(processFlag=="NoTrip")
        {

            Intent intent = new Intent(this, WellcomePage.class);
            startActivity(intent);


        }
//        else {
//            Intent intent = new Intent(this, WellcomePage.class);
//            startActivity(intent);
//
//        }

    }
    public void showTrip(){

        processFlag = "showTrip";

        try
        {
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
            String function = String.format("GetTripsByDriverId/%s/%s", Utility.getPreferences(this, "UserId"), 0);
            function = function.replace(" ", "%20");
            asyncTaskCallWCF.execute(function);
        }
        catch (Exception ex)
        {
            processFlag ="NoTrip";
            Toast.makeText(this, "ExeptionSHOW------->"+ex.getMessage() , Toast.LENGTH_LONG).show();

        }
    }




    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnLogin:
//                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
//                startService(i);
                //*********** for premission by zahra
                if (Build.VERSION.SDK_INT >= 23) {

                    if (checkSelfPermission(mPermission) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{mPermission,
                                },
                                REQUEST_CODE_PERMISSION);

                        return;
                    }



                }
                //****************************************
                   Intent intentLogin = new Intent(this,LoginActivity.class);
                startActivity(intentLogin);
                break;
            case  R.id.btnSignUp:
                Intent intentsignUp = new Intent(this,SignUpActivity.class);
                startActivity(intentsignUp);
                break;

            case R.id.txtViewEnLan:
                lang=getResources().getString(R.string.lang_en);
                country=getResources().getString(R.string.country_us);
                onConfigurationChanged(new Configuration());
                break;
            case R.id.txtViewFaLan:
                lang=getResources().getString(R.string.lang_fa);
                country=getResources().getString(R.string.country_ir );
                onConfigurationChanged(new Configuration());
                break;
            default:
                break;
        }

        Utility.setPreferences(this,getResources().getString(R.string.lang),lang);
        Utility.setPreferences(this,getResources().getString(R.string.country),country);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        if (lang != null){
            Utility.languageHelper(lang,country,newConfig,getBaseContext().getResources());
            recreate();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getResources().getString(R.string.lang),lang);
        outState.putString(getResources().getString(R.string.country),country);
    }

    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }





}
