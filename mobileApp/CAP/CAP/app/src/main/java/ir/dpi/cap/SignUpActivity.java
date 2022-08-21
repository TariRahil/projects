package ir.dpi.cap;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import ir.dpi.cap.Entities.ResultFormat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener,AsyncResponse {

    EditText txtName,txtEmail,txtPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String fullName = txtName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if(fullName == null || fullName.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty()){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(getResources().getString(R.string.activity_signup_alert1_title))
                    .setMessage(getResources().getString(R.string.activity_signup_alert1_message))
                    .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn), null).show();
        }
        else{
            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
            asyncTaskCallWCF.delegate = this;
            String function=String.format("AddUser/%s/%s/%s/%s/%s/%s/%s/%s",fullName,fullName,email,password,"officeName","1234567","admin","2");
            function=function.replace(" " ,"%20");
            asyncTaskCallWCF.execute(function);
        }
    }

    @Override
    public void processFinish(String output){
        try {
            JSONObject jsonObject = new JSONObject(output);
            ResultFormat rf=new ResultFormat(jsonObject.getBoolean("Result"), jsonObject.getString("Message"));
            if(rf.getResult())
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_signup_alert3_title))
                        .setMessage(getResources().getString(R.string.activity_signup_alert3_message))
                        .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SignUpActivity.this,MapsActivity.class);
                                startActivity(intent);
                            }
                        }).show();
            }
            else {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_signup_alert2_title))
                        .setMessage(getResources().getString(R.string.activity_signup_alert2_message))
                        .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn),null).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
