package com.example.f1_shop.Admin.TabsContent;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*

    For reference page look at TestActivity and UsersFragment


 */

public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

   public PageAdapter(FragmentManager fm, int numOfTabs){

       super(fm);
       this.numOfTabs = numOfTabs;
   }

    
    @Override
    public Fragment getItem(int position) {

       switch(position){
           case 0:
               return new TeamsFragment();
           case 1:
               return  new ItemsFragment();
           case 2:
               return new UsersFragment();
           default:
               return null;
       }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
