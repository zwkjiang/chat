package com.example.friend.view.act;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.arouter.RouterPath;
import com.example.core.view.BaseActivity;
import com.example.core.viewmodel.BaseViewModel;
import com.example.friend.R;
import com.example.friend.adapter.SeekAdapter;
import com.google.android.material.tabs.TabLayout;

@Route(path = RouterPath.ADDFRIENDACT)
public class SeekFriendActivity extends BaseActivity {
    private ImageView leftIcon;
    private TextView titleText;
    private ImageView rightIcon;
    private TabLayout tabAddFriend;
    private SeekAdapter seekAdapter;
    private ViewPager pagerAddFriend;
    @Override
    protected void initEvent() {
        pagerAddFriend.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabAddFriend.setScrollPosition(position,positionOffset,false);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabAddFriend.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerAddFriend.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        addTool();
        addTab();
        addPager();
    }

    private void addTool() {
        titleText.setText("添加");
        leftIcon.setImageResource(R.drawable.fanhui);
    }

    private void addTab() {
        tabAddFriend.addTab(tabAddFriend.newTab().setText("找人"));
        tabAddFriend.addTab(tabAddFriend.newTab().setText("找群"));
    }

    private void addPager() {
        if (seekAdapter==null){
            seekAdapter = new SeekAdapter(getSupportFragmentManager());
            pagerAddFriend.setAdapter(seekAdapter);
        }
    }

    @Override
    protected void initView() {
        pagerAddFriend = (ViewPager) findViewById(R.id.pager_addFriend);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        titleText = (TextView) findViewById(R.id.title_text);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        tabAddFriend = (TabLayout) findViewById(R.id.tab_addFriend);
    }

    @Override
    protected void initBinding() {

    }

    @Override
    protected BaseViewModel createVM() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addfriend;
    }
}
