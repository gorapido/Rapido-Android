package co.gorapido.rapido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Michael on 6/3/2015.
 */
public class SignUpActivity extends FragmentActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.sign_in_frame);

        if(fragment == null){
            fragment = new SignUpFragment();
            fm.beginTransaction().add(R.id.sign_in_frame, fragment).commit();
        }
    }
}