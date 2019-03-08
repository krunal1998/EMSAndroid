package com.example.ems.Helper;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.ems.BuildConfig;
import com.example.ems.Fragments.FragmentContent;
import com.example.ems.Interface.NavigationManager;
import com.example.ems.MainActivity;
import com.example.ems.R;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getInstance(MainActivity mainActivity)
    {
        if(mInstance==null)
            mInstance=new FragmentNavigationManager();
        mInstance.configure(mainActivity);
        return  mInstance;
    }
    private void configure(MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;
        mFragmentManager= this.mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {
        showFragment(FragmentContent.newInstance(title),false);
    }

    private void showFragment(Fragment fragmentContent, boolean b) {
        FragmentManager fm= mFragmentManager;
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.container,fragmentContent);
        ft.addToBackStack(null);
        if(b|| !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();

    }

}
