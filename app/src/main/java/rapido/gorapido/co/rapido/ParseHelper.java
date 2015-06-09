package rapido.gorapido.co.rapido;

import android.app.Activity;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by User0 on 6/3/2015.
 */
public class ParseHelper {
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String AVATAR = "avatar";
    public static boolean connected = false;
    public static boolean isLoggedIn;
    public static boolean canSignUp;
    public static Activity context;
    public static ParseUser currentUser;
    public static void initializeConnection(Activity context) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "cJa6opkW95Jqat6TYQ92UHCqimkZwm8Wdb40kxAY", "mpdNC6SshAPwaY8DvDRQp1v8YxKIjAn1C3W8Nz07");
        connected = true;
    }
    public static boolean isCurrentUser(){
        currentUser = ParseUser.getCurrentUser();
        if(currentUser != null){
            return true;
        }else{
            return false;
        }
    }
    public static String getStringFromCurrentUser(String key){
        if(!isCurrentUser()){
            return null;
        }
        return currentUser.get(key).toString();
    }
    public static void setStringForCurrentUser(String key, String value){
        if(!isCurrentUser()){
            return;
        }
        currentUser.put(key, value);
    }
    public static String getEmailFromCurrentUser(){
        if(!isCurrentUser()){
            return null;
        }
        return currentUser.getEmail();
    }
    public static void setEmailForCurrentUser(String email){
        if(!isCurrentUser()){
            return;
        }
        currentUser.setEmail(email);
    }
    public static void saveCurrentUser(){
        currentUser.saveInBackground();
    }
    public static void logout(){
        ParseUser.logOut();
    }
    public static Image getImageFromCurrentUser(String key) throws Exception{
        try{
            return (Image)currentUser.get(key);
        }catch (Exception e){
            throw new Exception("Error getting image.");
        }
    }
    public static boolean loginUser(String email, String password){
        ParseUser user = new ParseUser();
        isLoggedIn = true;
        user.setEmail(email);
        user.setUsername(email);
        user.setPassword(password);
        try{
            ParseUser.logIn(email, password);
        } catch (ParseException e) {
            isLoggedIn = false;
            Log.d("loginUser", e.getMessage());
        }
        return isLoggedIn;
    }
    public static boolean signUpUser(String firstName, String lastName, String email, String password, Activity context){
        ParseHelper.context = context;
        ParseUser user = new ParseUser();
        canSignUp = false;
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.put(FIRST_NAME, firstName);
        user.put(LAST_NAME, lastName);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    canSignUp = true;
                } else {
                    Toast.makeText(ParseHelper.context, e.getMessage(), Toast.LENGTH_LONG).show();
                    canSignUp = false;
                }
            }
        });
        return canSignUp;
    }
}

