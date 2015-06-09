package rapido.gorapido.co.rapido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Michael on 6/3/2015.
 */
public abstract class SingleFragmentActivity extends FragmentActivity {
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);

        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_frame);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.content_frame, fragment).commit();
        }
    }
}
