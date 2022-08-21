package ir.dpi.capdriver;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Noroozi on 5/22/2017.
 */

public class AsyncTaskCallWCF extends AsyncTask<String,Void, String> {

    public AsyncResponse delegate = null;
    private String serverURL="http://62.193.13.135/TaxAppWS/TaxAppService.svc/";

    @Override
    protected String doInBackground(String... params) {
        // download json string from API
        StringBuffer response = null;
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(serverURL + params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            response = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.e("e",e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return "OOO";
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            delegate.processFinish(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
