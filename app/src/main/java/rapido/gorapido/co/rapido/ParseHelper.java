package rapido.gorapido.co.rapido;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michael on 6/3/2015.
 */
public class ParseHelper {
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String AVATAR = "avatar";
    public static boolean connected = false;
    public static boolean isLoggedIn;
    public static Activity context;
    public static ParseUser currentUser;
    public static String avatarString;
    public static void initializeConnection(Activity context) {
        // Enable Local Datastore.
        Parse.enableLocalDatastore(context);
        Parse.initialize(context, "cJa6opkW95Jqat6TYQ92UHCqimkZwm8Wdb40kxAY", "mpdNC6SshAPwaY8DvDRQp1v8YxKIjAn1C3W8Nz07");
        connected = true;
    }
    public static boolean isCurrentUser(){
        currentUser = ParseUser.getCurrentUser();
        return currentUser != null;
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
    public static void setPasswordForCurrentUser(String password){
        if(!isCurrentUser()){
            return;
        }
        currentUser.setPassword(password);
    }
    public static void createAvatar(Bitmap img){
        if(!isCurrentUser()){
            return;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();
        ParseFile parseFile = new ParseFile("avatar.jpg", data);
        currentUser.put(AVATAR, parseFile);
        saveCurrentUser();
    }
    public static void retrieveAvatar(final ImageButton imageButton, final int width, final int height){
        avatarString = null;
        if(!isCurrentUser()) {
            return;
        }
        ParseFile parseFile = currentUser.getParseFile(AVATAR);
        if(parseFile == null) {
            return;
        }
        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                if(e == null) {
//                    float ratiow = -1, ratioh = -1;
                    Bitmap original = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap altered = original;
                    if(original == null){
                        return;
                    }
                    if(original.getWidth() != width){
//                        ratiow = (float)width/original.getWidth();
                        altered = Bitmap.createScaledBitmap(original, width, height, true);
                    }
//                    if(original.getHeight() != height){
//                        ratioh = (float)height/original.getHeight();
//                    }
//                    BitmapFactory.Options factory = new BitmapFactory.Options();
//                    if(ratiow >= ratioh && ratiow != -1){
//                        factory.inSampleSize = (int)(ratiow);
//                    }else if(ratioh != -1){
//                        factory.inSampleSize = (int)(ratioh);
//                    }
//                    if(ratiow != -1 || ratioh != -1){
//                        altered = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, factory);
//                    }
                    imageButton.setImageBitmap(altered);
                }else{
                    Log.e("ParseHelper", e.getMessage());
                }
            }
        });
    }
    public static void saveCurrentUser(){
        currentUser.saveInBackground();
    }
    public static void logout(){
        ParseUser.logOut();
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
    public static void signUpUser(String firstName, String lastName, String email, String password, Activity context){
        ParseHelper.context = context;
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.put(FIRST_NAME, firstName);
        user.put(LAST_NAME, lastName);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent i = new Intent(ParseHelper.context, HomeActivity.class);
                    ParseHelper.context.startActivity(i);
                } else {
                    Toast.makeText(ParseHelper.context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}

