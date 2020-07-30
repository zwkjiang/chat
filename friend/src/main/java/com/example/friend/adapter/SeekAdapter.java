package com.example.friend.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.friend.view.fragment.SeekFriendFragment;
import com.example.friend.view.fragment.SeekGroupFragment;

public class SeekAdapter extends FragmentPagerAdapter {
    public SeekAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
           return new SeekFriendFragment();
        }else{
            return new SeekGroupFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
