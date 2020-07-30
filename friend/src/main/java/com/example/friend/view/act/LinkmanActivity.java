package com.example.friend.view.act;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.core.view.BaseActivity;
import com.example.core.viewmodel.BaseViewModel;
import com.example.friend.R;
import com.example.friend.adapter.AddFriendAdapter;
import com.example.friend.adapter.GroupAdapter;
import com.google.android.material.tabs.TabLayout;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.ArrayList;
@Route(path = RouterPath.LINKMANACT)
public class LinkmanActivity extends BaseActivity {
    private RecyclerView recyclerLinkman;
    private TabLayout tableLinkman;
    private ImageView leftIcon;
    private TextView titleText;
    private ImageView rightIcon;
    private ViewPager pagerLinkman;
    private GroupAdapter groupAdapter;
    private AddFriendAdapter addFriendAdapter;
    @Override
    protected void initEvent() {
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.getInstance().route(RouterPath.ADDFRIENDACT);
            }
        });
        pagerLinkman.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tableLinkman.setScrollPosition(position,positionOffset,false);
            }

            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tableLinkman.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerLinkman.setCurrentItem(tab.getPosition());
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
        addTileBar();
        addTab();
        addPager();
        addFriend();
    }

    private void addFriend() {
        ArrayList<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        if (addFriendAdapter == null){
            addFriendAdapter = new AddFriendAdapter(list);
            recyclerLinkman.setAdapter(addFriendAdapter);
        }
    }

    private void addPager() {
        if (groupAdapter==null){
            groupAdapter = new GroupAdapter(getSupportFragmentManager());
            pagerLinkman.setAdapter(groupAdapter);
        }
    }

    private void addTileBar() {
        titleText.setText("联系人");
        leftIcon.setImageResource(R.drawable.fanhui);
        rightIcon.setImageResource(R.mipmap.person);
    }

    private void addTab() {
        tableLinkman.addTab(tableLinkman.newTab().setText("好友"));
        tableLinkman.addTab(tableLinkman.newTab().setText("分组"));
        tableLinkman.addTab(tableLinkman.newTab().setText("群组"));
    }

    @Override
    protected void initView() {
        pagerLinkman = (ViewPager) findViewById(R.id.pager_linkman);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        titleText = (TextView) findViewById(R.id.title_text);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        recyclerLinkman = (RecyclerView) findViewById(R.id.recycler_linkman);
        tableLinkman = (TabLayout) findViewById(R.id.table_linkman);
        recyclerLinkman.setLayoutManager(new LinearLayoutManager(this));
        initPermission();
        ZXingLibrary.initDisplayOpinion(this);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.CAMERA},101);
        }
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
        return R.layout.activity_linkman;
    }
}
