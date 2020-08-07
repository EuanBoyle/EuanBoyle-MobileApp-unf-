package uk.ac.stir.cs.dissertation;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 @Author: 2534758 - Euan Boyle

 PagerAdapter returns the fragment depending on what tab is currently selected.
 */
class PagerAdapter extends FragmentStatePagerAdapter {

    private final int mNumOfTabs;
    public PagerAdapter(FragmentManager fragmentManager, int numOfTabs) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mNumOfTabs = numOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new InstructionsFragment();
            case 1: return new IngredientsFragment();
            default: return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}