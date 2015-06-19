package co.gorapido.rapido;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.parse.ParseFacebookUtils;

import java.util.Arrays;


public class SignInActivity extends AppCompatActivity {
    Toolbar toolbar;
    Fragment fragment;
    Activity current = this;
    public static CallbackManager callbackManager;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(!ParseHelper.connected) {
            ParseHelper.initializeConnection(this);
        }
        FacebookHelper.initializeFacebook(getApplicationContext());
        setContentView(R.layout.sign_in_frame);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT).show();
//                ParseHelper.facebookLogin(Arrays.asList("public_profile", "email"), current);
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//
//            }
//        });
        if(!ParseHelper.isCurrentUser()) {
            FragmentManager fm = getFragmentManager();
            fragment = fm.findFragmentById(R.id.sign_in_frame);
            if (fragment == null) {
                fragment = new SignInFragment();
                fm.beginTransaction().add(R.id.sign_in_frame, fragment).commit();
            }
        }else{
            startHome();
        }
    }
    public void startHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        fragment.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }
}