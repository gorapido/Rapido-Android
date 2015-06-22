package co.gorapido.rapido;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

/**
 * Created by Michael on 6/15/2015.
 */
public class HireProfileFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static int numOfTabs;
    private static CharSequence tabTitles[];
    public HireProfileFragmentPagerAdapter(FragmentManager fragementManager, CharSequence tabTitles[], int numOfTabs){
        super(fragementManager);
        this.numOfTabs = numOfTabs;
        this.tabTitles = tabTitles;
    }
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            //return new HireFragment();
            return new SchedulingFragment();
        }else{
            return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
