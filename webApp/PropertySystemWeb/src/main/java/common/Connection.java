package common;

import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    public static String serviceConnection(String apiMethod,String type) throws  IOException {
        URL url = new URL("http://10.10.2.100:8080/PropertySystemService-1.0/"+apiMethod);
        System.out.println("URLCONNECTION="+url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setRequestProperty("Accept" , MediaType.APPLICATION_JSON);

        httpURLConnection.connect();
        StringBuffer response = null;

        response = new StringBuffer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
        //return bufferedReader.toString();
    }
}
