package co.gorapido.rapido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


public class SignInActivity extends AppCompatActivity {
    Toolbar toolbar;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_frame);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(!ParseHelper.connected) {
            ParseHelper.initializeConnection(this);
        }
        if(!ParseHelper.isCurrentUser()) {
            FragmentManager fm = getFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.sign_in_frame);
            if (fragment == null) {
                fragment = new SignInFragment();
                fm.beginTransaction().add(R.id.sign_in_frame, fragment).commit();
            }
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}