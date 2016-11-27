package cz.barush.medicaltag;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cz.barush.medicaltag.fragments.GetTagsFragment;
import cz.barush.medicaltag.fragments.MyTagsFragment;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter
{
    List<Fragment> fragments;

    public PagerAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        if(fragments.get(position) != null)return fragments.get(position);
        else return new MyTagsFragment();
    }

    @Override
    public int getCount()
    {
        return fragments.size();
    }
}
