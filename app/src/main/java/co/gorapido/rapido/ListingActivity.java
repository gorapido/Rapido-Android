package co.gorapido.rapido;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * Created by rapido on 6/16/15.
 */
public class ListingActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_frame);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fm = getFragmentManager();
        CompanyListFragment fragment = new CompanyListFragment();
        Intent i = getIntent();
        String currentCategory = i.getStringExtra(CompanyListFragment.CATEGORY);
        Bundle args = new Bundle();
        args.putString(CompanyListFragment.CATEGORY, currentCategory);
        fragment.setArguments(args);
        fm.beginTransaction().replace(R.id.frame_layout_list, fragment).commit();
    }
}
