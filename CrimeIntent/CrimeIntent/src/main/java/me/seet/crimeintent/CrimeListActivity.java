package me.seet.crimeintent;

import android.support.v4.app.Fragment;

/**
 * Created by darren on 4/2/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
