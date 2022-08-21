package ir.dpi.cap;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pcd iran on 09/02/2017.
 */

public class MySingelton {
    private static MySingelton mInstance;
    private static Context mCtx;
    private RequestQueue requestQueue;

    private MySingelton(Context context)
    {
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingelton getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new MySingelton(context);
        }
        return mInstance;
    }

    public<T> void addToRequestque(Request<T> request)
    {
        getRequestQueue().add(request);
    }
}
