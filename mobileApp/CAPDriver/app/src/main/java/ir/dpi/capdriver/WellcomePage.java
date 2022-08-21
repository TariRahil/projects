package ir.dpi.capdriver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Cache;

public class WellcomePage extends AppCompatActivity  implements View.OnClickListener,AsyncResponse
{

    TextView driverNameTxt,noTripMessageTxt;
    Button btnActivePassive;
    Menu myMenu;
    String processFinishFlag;
    int carStatus ;
    long carId;
    int tripStatus = -1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome_page);
        driverNameTxt = (TextView) findViewById(R.id.driverNameTxt);
        noTripMessageTxt = (TextView) findViewById(R.id.noTripMessageTxt);
        btnActivePassive = (Button) findViewById(R.id.btnActivePassive);
        btnActivePassive.setOnClickListener(this);
        getDriver();
    }

    private void setActivePassive()
    {
        if (carStatus == 1)
        {
            btnActivePassive.setText(R.string.activity_wellcome_Page_inActive);
        }
        else if (carStatus == 0)
        {
            btnActivePassive.setText(R.string.activity_wellcome_Page_Active);
        }
    }

    public void updateCar(int stat)
    {
        try
        {
            processFinishFlag = "updateCar";
            carStatus = stat;
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
            String function = String.format("UpdateCar/%s/%s", carId, carStatus);
            function = function.replace(" ", "%20");
            asyncTaskCallWCF.execute(function);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, "updateCARRRR == "+ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void getDriver()
    {
        processFinishFlag = "getDriver";
        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        String function=String.format("GetUserByIdAndStatus/%s/%s",Utility.getPreferences(this,"UserId"),0);
        function=function.replace(" " ,"%20");
        asyncTaskCallWCF.execute(function);
    }

    @Override
    public void onClick(View v)
    {
        if(btnActivePassive.getText().equals(getResources().getString(R.string.activity_wellcome_Page_inActive)))//غیرفعال کن)
            {
                updateCar(0);
            }
        else if(btnActivePassive.getText().equals(getResources().getString(R.string.activity_wellcome_Page_Active)))
            {
                updateCar(1);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        myMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.trips_log:
                break;

           /* case R.id.edit_info:
                Intent intentEditInfo = new Intent(this,EditInfoActivity.class);
                startActivity(intentEditInfo);
                break;*/
            case R.id.config:
                break;

            case R.id.about:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_wellcome_about_cap_driver_title))
                        .setMessage(getResources().getString(R.string.activity_wellcome_about_cap_driver_message))
                        .setPositiveButton(getResources().getString(R.string.activity_wellcome_about_cap_driver_btn), null)
                        .show();
                break;

            case R.id.exit:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.menu_exit))
                        .setMessage(getResources().getString(R.string.menu_exit_message))
                        .setPositiveButton(getResources().getString(R.string.menu_positive_btn), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {

                                updateCar(0);
                                AppExit();
                            }
                        }).setNegativeButton(getResources().getString(R.string.menu_negetive_btn), null)
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

   //*********** update by zahra for exit**************
    public void AppExit()
    {
      PreferenceManager.getDefaultSharedPreferences(this).edit().clear().commit();this.finish();
       Intent CloseInt = new Intent(getApplicationContext(), MainActivity.class);
        CloseInt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        CloseInt.putExtra("CloseApp", true);
        startActivity(CloseInt);
    }

    @Override
    public void processFinish(String output) throws JSONException
    {
        Bundle extras = getIntent().getExtras();
        if (processFinishFlag.equals("getDriver"))
        {
            try
            {
                JSONObject jsonObject = new JSONObject(output);
                driverNameTxt.setText(jsonObject.getString("FirstName") + " " + jsonObject.getString("LastName"));
                carStatus = (jsonObject.getInt("CarStatus"));
                carId =  (jsonObject.getLong("CarId"));
                if (jsonObject.getLong("TripId")!= 0)
                {
                tripStatus = (jsonObject.getInt("TripStatus"));
                }
                if (extras != null)
                {
                    noTripMessageTxt.setText(extras.getString("noTripMessage"));
                    noTripMessageTxt.setText(extras.getString("rejectTripByPassenger"));
                }
                setActivePassive();
                processFinishFlag = "";
            }
            catch (Exception ex)
            {
                Toast.makeText(this, "ex=====>" + ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
            else if (processFinishFlag.equals("updateCar"))
        {
            setActivePassive();
        }
    }

    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }

}
