package ir.dpi.cap;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;


    Button btnSignUp, btnLogin;
    TextView txtViewEnLan,txtViewFaLan;
    String lang,country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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





    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSignUp:
                Intent intentSighUp = new Intent(this,SignUpActivity.class);
                startActivity(intentSighUp);
                break;
            case R.id.btnLogin:
                Intent intentLogin = new Intent(this,LoginActivity.class);
                startActivity(intentLogin);
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
