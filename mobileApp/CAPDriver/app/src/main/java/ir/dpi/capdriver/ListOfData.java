package ir.dpi.capdriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;

public class ListOfData extends AppCompatActivity implements AsyncResponse
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_data);

        AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
        asyncTaskCallWCF.delegate = this;
        String id = null;
        String function = String.format("GetTripsListByDriverId/%s",Utility.getPreferences(this,"UserId"));
        function = function.replace(" ", "%20");
        asyncTaskCallWCF.execute(function);
    }

    @Override
    public void processFinish(String output) throws JSONException
    {

    }
}
