package co.gorapido.rapido;

import android.content.Context;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONObject;

/**
 * Created by rapido on 6/17/15.
 */
public class FacebookHelper {
    private static boolean isInitialized = false;
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String PARAMETERS = "first_name,last_name,email";
    public static void initializeFacebook(Context context){
        if(!isInitialized) {
            FacebookSdk.sdkInitialize(context);
            isInitialized = true;
        }
    }
}
