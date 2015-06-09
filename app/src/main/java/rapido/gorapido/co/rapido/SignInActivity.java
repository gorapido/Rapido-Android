package rapido.gorapido.co.rapido;

import android.app.Fragment;


public class SignInActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        if(!ParseHelper.connected) {
            ParseHelper.initializeConnection(this);
        }
        if(!ParseHelper.isCurrentUser()) {
            return new SignInFragment();
        }else{
            return new ProfileFragment();
        }
    }
}
