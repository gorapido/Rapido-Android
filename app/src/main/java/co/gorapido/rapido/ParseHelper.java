package co.gorapido.rapido;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michael on 6/3/2015.
 */
public class ParseHelper {
    public static String FIRST_NAME = "firstName";
    public static String LAST_NAME = "lastName";
    public static String AVATAR = "avatar";
    public static String COMPANY_TABLE = "Company";
    public static String COMPANY_CATEGORY = "category";
    public static String COMPANY_LOGO = "logo";
    public static String COMPANY_NAME = "name";
    public static String COMPANY_PHONE = "phone";
    public static String COMPANY_SITE = "site";
    public static String COMPANY_SUMMARY = "summary";
    public static String TAG = "ParseHelper";
    public static List<Company> companyList;
    public static boolean connected = false;
    public static boolean isLoggedIn;
    public static Activity context;
    public static ListView listView;
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
    public static void getCompanies(String category, Activity context, ListView lv){
        ParseHelper.listView = lv;
        ParseHelper.context = context;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(COMPANY_TABLE);
        query.whereEqualTo(COMPANY_CATEGORY, category);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> companies, ParseException e) {
                if (e == null) {
                    if(companies == null){
                        Log.d(TAG, "search is null");
                    }else{
                        Log.d(TAG, "search is not null");
                    }
                    ParseHelper.companyList = new ArrayList<>();
                    CompanyListAdapter listAdapter = new CompanyListAdapter(ParseHelper.companyList, ParseHelper.context);
                    ParseHelper.listView.setAdapter(listAdapter);
                    for(int i = 0; i < companies.size(); i++){
                        String name = companies.get(i).get(COMPANY_NAME).toString();
                        Log.d(TAG, "Name: " + name);
                        String category = companies.get(i).get(COMPANY_CATEGORY).toString();
                        Log.d(TAG, "Category: " + category);
                        String phone = companies.get(i).get(COMPANY_PHONE).toString();
                        Log.d(TAG, "Phone: " + phone);
                        String site = companies.get(i).get(COMPANY_SITE).toString();
                        Log.d(TAG, "Site: " + site);
                        String summary = companies.get(i).get(COMPANY_SUMMARY).toString();
                        Log.d(TAG, "Summary: " + summary);
                        Company aCompany = new Company(name, category, phone, site, summary);
                        ParseFile logoFile = companies.get(i).getParseFile(COMPANY_LOGO);
                        logoFile.getDataInBackground(new GetCompanyCallback(aCompany, listAdapter));
                    }
                } else {
                    Toast.makeText(ParseHelper.context, "Error loading companies", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    Bitmap original = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap altered = original;
                    if(original == null){
                        return;
                    }
                    if(original.getWidth() != width){
                        altered = Bitmap.createScaledBitmap(original, width, height, true);
                    }
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

