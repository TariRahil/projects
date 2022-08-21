package ir.dpi.cap;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;

import ir.dpi.cap.Entities.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,AsyncResponse {

    EditText txtEmail,txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isInternetOn()) {

            String email = txtEmail.getText().toString().trim();
            String password = txtPassword.getText().toString().trim();

            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_signup_alert1_title))
                        .setMessage(getResources().getString(R.string.activity_signup_alert1_message))
                        .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn), null).show();
            } else {
                AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
                asyncTaskCallWCF.delegate = this;

                String function = String.format("GetUser/%s/%s/%s/%s", email, password, "2", "null");
                function = function.replace(" ", "%20");
                asyncTaskCallWCF.execute(function);
            }
       }
        else
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.activity_login_alertNoInternet_title))
                    .setMessage(getResources().getString(R.string.activity_login_alertNoInternet_message))
                    .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn), null)
                    .show();
        }
    }

    @Override
    public void processFinish(String output){

        try {
            if (output=="" || output== null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_login_alert2_title))
                        .setMessage(getResources().getString(R.string.activity_login_alert2_message))
                        .setPositiveButton(getResources().getString(R.string.activity_login_alert_positive_btn),null).show();
            }
            else
            {
                    JSONObject jsonObject = new JSONObject(output);
                User userNP=new User(jsonObject.getString("UserName"), jsonObject.getString("Password"), jsonObject.getLong("UserId"));
                Intent intent = new Intent(this, MapsActivity.class);
                //intent.putExtra("userId", userNP.getUserId());
                //Toast.makeText(this, String.valueOf(userNP.getUserId()),Toast.LENGTH_LONG).show();

                Utility.setPreferences(this,"UserId", String.valueOf(userNP.getUserId()));
                Utility.setPreferences(this,"PassengerName", jsonObject.getString("FirstName")+" "+jsonObject.getString("LastName"));
                startActivity(intent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
