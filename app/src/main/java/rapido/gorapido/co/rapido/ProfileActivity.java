package rapido.gorapido.co.rapido;

import android.app.Fragment;

/**
 * Created by Michael on 6/3/2015.
 */
public class ProfileActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ProfileFragment();
    }
}
