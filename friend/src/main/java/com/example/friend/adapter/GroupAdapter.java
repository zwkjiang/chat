package com.example.friend.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.friend.view.fragment.GoodFragment;
import com.example.friend.view.fragment.GroupChatFragment;
import com.example.friend.view.fragment.GroupingFragment;

public class GroupAdapter extends FragmentPagerAdapter {
    public GroupAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new GoodFragment();

        }else if (position==1){
            return new GroupingFragment();
        }else {
            return new GroupChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
