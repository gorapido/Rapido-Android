package rapido.gorapido.co.rapido;

import android.app.Fragment;

/**
 * Created by Michael on 6/3/2015.
 */
public class SignUpActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SignUpFragment();
    }
}