package ir.dpi.cap;

import org.json.JSONException;

/**
 * Created by Noroozi on 5/22/2017.
 */

public interface AsyncResponse {
    void processFinish(String output) throws JSONException;
}
