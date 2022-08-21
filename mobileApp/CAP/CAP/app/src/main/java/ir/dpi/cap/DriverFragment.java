package ir.dpi.cap;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ir.dpi.cap.Entities.Driver;


public class DriverFragment extends Fragment   {

    TextView txtDrivername,txtDriverPlateNumber;
    Button btnCallDriver;
    String CellphoneNumber="192";
    ImageView imgDriver;
    //  String Src;
    private  Bitmap bitmap;
    public DriverFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_layout, container, false);
        try {



            txtDrivername = (TextView) v.findViewById(R.id.txtDriverName);
            txtDriverPlateNumber = (TextView) v.findViewById(R.id.txtDriverPlateNumber);
            btnCallDriver = (Button) v.findViewById(R.id.btnCallDriver);
            imgDriver = (ImageView) v.findViewById(R.id.imgDriver);
            btnCallDriver.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {

                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + CellphoneNumber));
//          if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                  Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//              return;
//          }
                    startActivity(callIntent);
                }
            });
        }
        catch (Exception ex)
        {
            //Toast.makeText(getActivity(),ex.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("tag","error");
        }
     return v;


    }
    public  void setData(String name, String number,String plateNumber)
    {
     try{
         //Toast.makeText(getActivity(),"driver:"+name +number, Toast.LENGTH_LONG).show();
        txtDrivername.setText(name);
        CellphoneNumber=number;
        txtDriverPlateNumber.setText(plateNumber);
//         bitmap=getbitmapFromURL("http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png");
//         imgDriver.setImageBitmap(bitmap);

     }
     catch (Exception e) {
        Toast.makeText(getActivity(),"Exception setData", Toast.LENGTH_LONG).show();

     }

    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
//    public Bitmap getbitmapFromURL(String src) {
//       try {
//           Log.i("img2","hello");
//        URL url = new URL(src);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setDoInput(true);
//        connection.connect();
//        InputStream input = connection.getInputStream();
//        Bitmap myBitmap = BitmapFactory.decodeStream(input);
//
//        return myBitmap;
//       }
//       catch (Exception e) {
//          Log.e("img",e.getLocalizedMessage());
//           //Toast.makeText(getActivity(),"hi", Toast.LENGTH_SHORT).show();
//        e.printStackTrace();
//        return null;
//    }
//
//
//
//
//    }

   // @Override
//    public void processFinish(String output) throws JSONException {
//        try {
//
//           JSONArray jsonArray = new JSONArray(output);
//            driverList = new ArrayList<Driver>();
//           for (int i = 0; i < jsonArray.length(); i++) {
//                driverList.add(Driver.ToDriver((JSONObject) jsonArray.get(i)));
//
//            }
//
//          Driver driver=  driverList.get(0);
//            Toast.makeText(getActivity(), driver.getFirstName(), Toast.LENGTH_SHORT).show();
//            setData(driver.getFirstName()+" "+driver.getLastName(),driver.getCellPhoneNumber(),driver.getMachineModel()+" "+driver.getCarColor()+" "+driver.getNumberPlate());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//u
//    }
//    public void getInfo(String id) {
//        try {
//
//            AsyncTaskCallWCF asyncTaskCallWCF = new AsyncTaskCallWCF();
//            asyncTaskCallWCF.delegate =this;
//            String function = String.format("GetDriverById/%s", id);
//            asyncTaskCallWCF.execute(function);
//        }
//        catch (Exception e) {
//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
}
