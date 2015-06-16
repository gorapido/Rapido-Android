package co.gorapido.rapido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Michael on 6/3/2015.
 */
public class HomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    private SlidingTabLayout tabs;
    private CharSequence tabTitles[] = {"Hire", "Profile"};
    private int numOfTabs = 2;
    HireProfileFragmentPagerAdapter fragmentAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_frame);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentAdapter = new HireProfileFragmentPagerAdapter(getFragmentManager(),tabTitles, numOfTabs);
        viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(fragmentAdapter);
        tabs = (SlidingTabLayout)findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.pressed_blue);
            }
        });

        tabs.setViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_logout:
                ParseHelper.logout();
                Intent i = new Intent(this, SignInActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
