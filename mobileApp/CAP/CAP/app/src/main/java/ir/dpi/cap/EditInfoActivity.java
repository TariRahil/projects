package ir.dpi.cap;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.dpi.cap.Entities.Car;
import ir.dpi.cap.Entities.User;

public class EditInfoActivity extends AppCompatActivity implements AsyncResponse,View.OnClickListener {

    TextView txtName,txtPhone,txtMobile,txtEmail,txtAddress,txtGender;
    Button btnSave;
    User user ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        txtName = (TextView) findViewById(R.id.txtName);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtMobile = (TextView) findViewById(R.id.txtMobile);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtGender = (TextView) findViewById(R.id.txtGender);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        String userId = Utility.getPreferences(this,"UserId");


        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        //Toast.makeText(this,userId,Toast.LENGTH_LONG).show();
        String function=String.format("GetUserById/%s",userId);
        function=function.replace(" " ,"%20");
        asyncTaskCallWCF.execute(function);
       // Toast.makeText(this,Utility.getPreferences(this,"UserName"),Toast.LENGTH_LONG).show();

        //txtName.setText();
    }

    @Override
    public void processFinish(String output)  {
        //Toast.makeText(this,output,Toast.LENGTH_LONG).show();
        try{

            if (output=="" || output== null)
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_login_alert2_title))
                        .setMessage(getResources().getString(R.string.activity_login_alert2_message))
                        .setPositiveButton(getResources().getString(R.string.activity_login_alert_positive_btn),null).show();
            }
            else if (Boolean.parseBoolean(output)){
              // JSONObject jsonObject = new JSONObject(output);
               // Toast.makeText(this,"456",Toast.LENGTH_LONG).show();

                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getResources().getString(R.string.activity_signup_alert3_title))
                        .setMessage(getResources().getString(R.string.activity_edit_info_update_ok))
                        .setPositiveButton(getResources().getString(R.string.activity_signup_alert_positive_btn),new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(EditInfoActivity.this,MapsActivity.class);
                                startActivity(intent);
                            }
                        }).show();

            }
            else
            {
                JSONObject jsonObject = new JSONObject(output);

                user = (User.ToUser(jsonObject));



                txtName.setText(user.getFirstName()+" "+user.getLastName());
                txtPhone.setText(user.getTelephone().toString());
                if (!(user.getMobile().equals("null"))){
                   /* Toast.makeText(this,user.getMobile(),Toast.LENGTH_LONG).show();
                    txtMobile.setHint("test");*/
                    txtMobile.setText(user.getMobile().toString());
                }


                txtEmail.setText(user.getUserName());

                if (!(user.getAddress().equals("null"))){
                   /* Toast.makeText(this,user.getMobile(),Toast.LENGTH_LONG).show();*/
                    txtAddress.setText(user.getAddress().toString());
                }


                txtGender.setText(user.getGender());


            }
        } catch (JSONException e) {
            //Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.txtGender:


                final String[] values = new String[]{getResources().getString(R.string.activity_edit_info_gender_female),
                        getResources().getString(R.string.activity_edit_info_gender_male),
                        getResources().getString(R.string.activity_edit_info_gender_unknown)};

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, values);

                txtGender.setInputType(InputType.TYPE_NULL); //To hide the softkeyboard

                        new AlertDialog.Builder(EditInfoActivity.this)
                                .setTitle(getResources().getString(R.string.activity_edit_info_gender_spinner_title))
                                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        txtGender.setText(values[which].toString());
                                        dialog.dismiss();
                                    }
                                }).create().show();

                break;
            case R.id.btnSave:
                String userId = Utility.getPreferences(this,"UserId");

                String fullName = txtName.getText().toString().trim()== "" ? "" : txtName.getText().toString().trim();
                //Toast.makeText(this,fullName,Toast.LENGTH_LONG).show();

                String[] fName = fullName.split(" ");
                String firstName;
                String lastName;
                if (fName.length==0){
                    firstName=" ";
                    lastName=" ";
                }
                else if (fName.length<2) {
                     firstName = fName[0];
                    lastName = " ";
                }else {
                     firstName = fName[0];
                     lastName = fName[1];
                }
                String phone = txtPhone.getText().toString().trim().equals(null) ? " " : txtPhone.getText().toString().trim() ;
                /*if (txtPhone.getText().toString().trim().equals(null)){
                    phone = " ";
                }
                else{
                    phone = txtPhone.getText().toString().trim();
                }*/
                String mobile = txtMobile.getText().toString().trim()== "" ? " " : txtMobile.getText().toString().trim();
                String email = txtEmail.getText().toString().trim()== "" ? " " : txtEmail.getText().toString().trim();
                String address = txtAddress.getText().toString().trim()== "" ?" " : txtAddress.getText().toString().trim() ;
                //String gender = txtGender.getText().toString().trim();
                int gender;

                if (txtGender.getText().toString().trim().equals(getResources().getString(R.string.activity_edit_info_gender_male))){
                    gender = 0;
                }else if (txtGender.getText().toString().trim().equals(getResources().getString(R.string.activity_edit_info_gender_female))){
                    gender = 1;
                }else{
                    gender = 2;
                }

                AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
                asyncTaskCallWCF.delegate = this;
                String function=String.format("UpdateUser/%s/%s/%s/%s/%s/%s/%s/%s",userId,firstName,lastName,phone,mobile,email,address,gender);

                function=function.replace(" " ,"%20");
                asyncTaskCallWCF.execute(function);

                break;

            default:
                break;
        }
    }
    @Override
    public void onBackPressed()
    {

        // super.onBackPressed(); // Comment this super call to avoid calling finish() or fragmentmanager's backstack pop operation.
    }
}
