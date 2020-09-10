package com.shrinkcom.expensemanagementapp.adaptor;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shrinkcom.expensemanagementapp.fragment.AllListFragment;
import com.shrinkcom.expensemanagementapp.fragment.CarListFragment;
import com.shrinkcom.expensemanagementapp.fragment.ExpenseFragment;
import com.shrinkcom.expensemanagementapp.fragment.PaymentsFragment;

public class ViewPagerAdaptor extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public ViewPagerAdaptor(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ExpenseFragment expenseFragment = new ExpenseFragment();
                return expenseFragment;
            case 1:
                PaymentsFragment paymentsFragment = new PaymentsFragment();
                return paymentsFragment;
            case 2:
                CarListFragment carListFragment = new CarListFragment();
                return carListFragment;
            case 3:
                AllListFragment allListFragment = new AllListFragment();
                return allListFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
