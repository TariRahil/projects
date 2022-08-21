package ir.dpi.capdriver;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;

public class DriverNotify extends AppCompatActivity implements AsyncResponse,View.OnClickListener
{
    Button btnAccept,btnNoAccept,btnDirection,btnEndOfTrip,btnCancel,btnPassengerArrived, btnCall;
    TextView timerTxt,destinationTxt,sourceTxt , passengerNameTxt;
    ProgressBar mProgressBar;
    LinearLayout afterAcceptionBtns,acceptionBtns,afterAcceptionBtns1,progressBar;

    int carStatus,tripStatus, i=0;
    Long passengerMobile;
    float sourceLat,sourceLon,destinationLat,destinationLon;
    String flag = null;
    private boolean btnAcceptClicked,btnPassengerArrivedFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_notify);

        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnNoAccept = (Button) findViewById(R.id.btnNoAccept);
        btnEndOfTrip = (Button) findViewById(R.id.btnEndOfTrip);
        btnDirection = (Button) findViewById(R.id.btnDirection);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnPassengerArrived = (Button) findViewById(R.id.btnPassengerArrived);
        btnCall = (Button) findViewById(R.id.btnCall);

        sourceTxt=(TextView) findViewById(R.id.sourceTxt);
        destinationTxt=(TextView) findViewById(R.id.destinationTxt);
        timerTxt = (TextView) findViewById(R.id.timerTxt);
        passengerNameTxt = (TextView) findViewById(R.id.passengerNameTxt);

        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);

        acceptionBtns = (LinearLayout) findViewById(R.id.acceptionBtns);
        afterAcceptionBtns = (LinearLayout) findViewById(R.id.afterAcceptionBtns);
        afterAcceptionBtns1 = (LinearLayout) findViewById(R.id.afterAcceptionBtns1);
        progressBar = (LinearLayout) findViewById(R.id.progressBar);

        afterAcceptionBtns.setVisibility(View.INVISIBLE);
        afterAcceptionBtns1.setVisibility(View.INVISIBLE);
        btnEndOfTrip.setVisibility(View.INVISIBLE);

        btnNoAccept.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnDirection.setOnClickListener(this);
        btnEndOfTrip.setOnClickListener(this);
        btnPassengerArrived.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnCall.setOnClickListener(this);

        showTrip();
    }

    public void counter()
    {
        new CountDownTimer(25000, 1000) {
            public void onTick(long millisUntilFinished) {
                i++;
                mProgressBar.setProgress((int)i*25/(25000/1000));
                timerTxt.setText((""+millisUntilFinished / 1000));
                if (btnAcceptClicked){
                    cancel();
                }
                if(((millisUntilFinished/1000)==1)&& (!btnAcceptClicked))
                {
                    onFinish();
                }
            }
            public void onFinish()
            {
                i++;
                mProgressBar.setProgress(25);
                timerTxt.setText("done!");
                btnNoAccept.performClick();
            }
        }.start();
    }

    public void showTrip(){
        counter();
        flag = "showTrip";
        tripStatus = 0;
        try
        {
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
            String function = String.format("GetTripsByDriverId/%s/%s", Utility.getPreferences(this, "UserId"), tripStatus);
            function = function.replace(" ", "%20");
            asyncTaskCallWCF.execute(function);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "ExeptionSHOW------->"+ex.getMessage() , Toast.LENGTH_LONG).show();

        }
    }

    public void getTrip(){
       // counter();
        flag = "getTrip";
        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        String function = String.format("GetTripById/%s",Utility.getPreferences(this,"TripId"));
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }

    public  void update()
    {
        btnAcceptClicked = true;
        tripStatus = 1;
        flag = "btnAccept";
        AsyncTaskCallWCF asyncTaskCallWCFBtnAccept = new AsyncTaskCallWCF();
        asyncTaskCallWCFBtnAccept.delegate = this;
        String functionBtnAccept = String.format("UpdateTrip/%s/%s",   Utility.getPreferences(this,"TripId"), tripStatus);
        functionBtnAccept = functionBtnAccept.replace(" ", "%20");
        asyncTaskCallWCFBtnAccept.execute(functionBtnAccept);

        acceptionBtns.setVisibility(View.INVISIBLE);
        afterAcceptionBtns.setVisibility(View.VISIBLE);
        afterAcceptionBtns1.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnAccept:
                getTrip();
                break;

            case R.id.btnDirection:
                String uri = "http://maps.google.com/maps?saddr=" +sourceLat+","+sourceLon+"&daddr="+destinationLat+","+destinationLon;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

                break;

            case R.id.btnEndOfTrip:
                tripStatus = 4;
                flag = "btnEndOfTrip";
                AsyncTaskCallWCF asyncTaskCallWCFbtnEndOfTrip = new AsyncTaskCallWCF();
                asyncTaskCallWCFbtnEndOfTrip.delegate = this;
                String functionbtnEndOfTrip = String.format("UpdateTrip/%s/%s",   Utility.getPreferences(this,"TripId"), tripStatus);
                functionbtnEndOfTrip = functionbtnEndOfTrip.replace(" ", "%20");
                asyncTaskCallWCFbtnEndOfTrip.execute(functionbtnEndOfTrip);
                break;

            case R.id.btnNoAccept:
                tripStatus = 5;
                flag = "btnNoAccept";
                AsyncTaskCallWCF asyncTaskCallWCFBtnNoAccept = new AsyncTaskCallWCF();
                asyncTaskCallWCFBtnNoAccept.delegate = this;
                String functionBtnNoAccept = String.format("UpdateTrip/%s/%s",   Utility.getPreferences(this,"TripId"), tripStatus);
                functionBtnNoAccept = functionBtnNoAccept.replace(" ", "%20");
                asyncTaskCallWCFBtnNoAccept.execute(functionBtnNoAccept);

                break;

            case R.id.btnCancel:
                if (btnPassengerArrivedFlag)
                {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_driver_after_start_trip_title))
                            .setMessage(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_driver_after_start_trip_message))
                            .setPositiveButton(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_driver_after_start_trip_possitive_btn), null)
                            .show();
                }
                else
                {
                    tripStatus = 2;
                    flag = "btnCancel";
                    AsyncTaskCallWCF asyncTaskCallWCFbtnCancel = new AsyncTaskCallWCF();
                    asyncTaskCallWCFbtnCancel.delegate = this;
                    String functionbtnCancel = String.format("UpdateTrip/%s/%s",   Utility.getPreferences(this,"TripId"), tripStatus);
                    functionbtnCancel = functionbtnCancel.replace(" ", "%20");
                    asyncTaskCallWCFbtnCancel.execute(functionbtnCancel);
                }

                break;

            case R.id.btnPassengerArrived:
                btnPassengerArrivedFlag = true;
                tripStatus = 6;
                flag = "btnPassengerArrived";
                btnEndOfTrip.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.INVISIBLE);

                AsyncTaskCallWCF asyncTaskCallWCFbtnPassengerArrived = new AsyncTaskCallWCF();
                asyncTaskCallWCFbtnPassengerArrived.delegate = this;
                String functionbtnPassengerArrived = String.format("UpdateTrip/%s/%s",   Utility.getPreferences(this,"TripId"), tripStatus);
                functionbtnPassengerArrived = functionbtnPassengerArrived.replace(" ", "%20");
                asyncTaskCallWCFbtnPassengerArrived.execute(functionbtnPassengerArrived);

                break;

            case R.id.btnCall:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + passengerMobile));
                startActivity(callIntent);

                break;
        }
    }

    @Override
    public void processFinish(String output)
    {
        //Toast.makeText(this, "output------->"+output , Toast.LENGTH_LONG).show();

        try
        {
            /*JSONObject jsonObject = null;
            if (output.equals(true))
            {

            }
            else
            {
                 jsonObject = new JSONObject(output);
            }*/
            if (flag.equals("showTrip"))
            {
               // JSONObject jsonObject = new JSONObject(output);
              //
                 if (output==""||output==null)
              //  if (jsonObject.getLong("TripId")==0)
                {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getResources().getString(R.string.activity_driver_notify_no_trip_title))
                            .setMessage(getResources().getString(R.string.activity_driver_notify_no_trip_message))
                            .setPositiveButton(getResources().getString(R.string.activity_driver_notify_no_trip_possitive_btn), new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    goToWellcomePage();
                                }
                            }).show();
                }
                else
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(output);
                        sourceTxt.setText(jsonObject.getString("SourceAddress"));
                        destinationTxt.setText(jsonObject.getString("DestinationAddress"));
                        passengerNameTxt.setText(jsonObject.getString("PassengerName"));
                        passengerMobile = Long.valueOf(jsonObject.getLong("PassengerMobile"));
                        Utility.setPreferences(this, "TripId", String.valueOf(jsonObject.getLong("TripId")));
                        //getTrip();
                        sourceLat = BigDecimal.valueOf(jsonObject.getDouble("SourceLatitude")).floatValue();
                        sourceLon = BigDecimal.valueOf(jsonObject.getDouble("SourceLongitude")).floatValue();
                        destinationLat = BigDecimal.valueOf(jsonObject.getDouble("DestinationLatitude")).floatValue();
                        destinationLon = BigDecimal.valueOf(jsonObject.getDouble("DestinationLongitude")).floatValue();
                    }
                    catch (Exception ex)
                    {
                        //Toast.makeText(this, "ExeptionSHOWprocces------->"+ex.getMessage() , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(this, WellcomePage.class);
                        startActivity(intent);

                    }

                }
            }
            else if (flag.equals("getCarIdByDriverId"))
            {
                JSONObject jsonObject = new JSONObject(output);
                Utility.setPreferences(this,"CarId", String.valueOf(jsonObject.getLong("CarId")));
            }
            else if (flag.equals("btnAccept"))
            {
                getTrip();
            }
            else if (flag.equals("btnNoAccept"))
            {
                Intent intent = new Intent(this, WellcomePage.class);
                startActivity(intent);
            }
            else if (flag.equals("btnEndOfTrip"))
            {
                Intent intent = new Intent(this, WellcomePage.class);
                startActivity(intent);
            }
            else if (flag.equals("btnCancel"))
            {
                Intent intent = new Intent(this, WellcomePage.class);
                startActivity(intent);
            }
          /*  else if (flag.equals("btnPassengerArrived"))
            {
                Toast.makeText(this, "btnPassengerArrived"+output , Toast.LENGTH_LONG).show();
            }*/
            else if (flag.equals("getTrip"))
            {
                JSONObject jsonObject = new JSONObject(output);
                if (jsonObject.getInt("Status")==0)
                {
                    update();
                }
                /*if (jsonObject.getInt("Status")==1)
                {
                    getTrip();
                }*/
                else if (jsonObject.getInt("Status")==3)
                {
                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_passenger_title))
                            .setMessage(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_passenger_message))
                            .setPositiveButton(getResources().getString(R.string.activity_driver_notify_cancel_trip_by_passenger_possitive_btn), new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    goToWellcomePage();
                                }
                            }).show();
                }

            }
        }
        catch(JSONException e)
        {
            Toast.makeText(this, "EXCEPTION==>" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void goToWellcomePage()
    {
        Intent intent = new Intent(this, WellcomePage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
