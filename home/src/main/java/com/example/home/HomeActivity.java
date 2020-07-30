package com.example.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.common.callback.RecyclerCallBack;
import com.example.core.view.BaseActivity;
import com.example.core.viewmodel.BaseViewModel;
import com.example.home.adapter.MesAdapter;
import com.example.home.adapter.PersonAdapter;
import com.example.storage.SharePreferenceUtils;
import com.example.wight.BNViewGroup;
import com.example.wight.OnViewClickListener;

import java.util.ArrayList;

@Route(path = RouterPath.HOMEACT)
public class HomeActivity extends BaseActivity {
    private DrawerLayout drawerHome;
    private TextView nameHome;
    private MapView mapHome;
    private RecyclerView recyclerHome;
    private MyLocationStyle myLocationStyle;
    private AMap aMap;
    private ArrayList<String> dataMsg;
    private MesAdapter mesAdapter;
    private BNViewGroup bntHome;
    private RecyclerView personHome;
    private PersonAdapter adapter;
    private ArrayList<String> personData;
    @Override
    protected void initEvent() {
        bntHome.setListener(new OnViewClickListener() {
            @Override
            public void onViewClick(View view, int position) {
                if (position==0){
                    RouterManager.getInstance().route(RouterPath.LINKMANACT);
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        intiPerson();
        nameHome.setText((String)SharePreferenceUtils.get(this,"name",""));
        if (mesAdapter==null){
            dataMsg = new ArrayList<>();
            dataMsg.add("");
            dataMsg.add("");
            dataMsg.add("");
            dataMsg.add("");
            mesAdapter = new MesAdapter(dataMsg);
            recyclerHome.setAdapter(mesAdapter);
            View inflate = LayoutInflater.from(this).inflate(R.layout.view_head, null, false);
            mesAdapter.addHeaderView(inflate);
        }
        mapHome.onCreate(savedInstanceState);
    }

    private void intiPerson() {
        if (adapter == null){
            personData = new ArrayList<>();
            personData.add("");
            personData.add("");
            personData.add("");
            personData.add("");
            personData.add("");
            adapter = new PersonAdapter(personData);
            adapter.setRecyclerCallBack(new RecyclerCallBack() {
                @Override
                public void getPosition(int position) {
                    popWindow();
                }
            });
            personHome.setAdapter(adapter);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        personHome = (RecyclerView) findViewById(R.id.person_home);
        bntHome = (BNViewGroup) findViewById(R.id.bnt_home);
        drawerHome = (DrawerLayout) findViewById(R.id.drawer_home);
        nameHome = (TextView) findViewById(R.id.name_home);
        recyclerHome = (RecyclerView) findViewById(R.id.recycler_home);
        mapHome = (MapView) findViewById(R.id.map_home);
        recyclerHome.setLayoutManager(new LinearLayoutManager(this));
        drawerHome.setScrimColor(android.R.color.transparent);
        personHome.setLayoutManager(new LinearLayoutManager(this));
        if (aMap==null){
            aMap = mapHome.getMap();
        }
        initShowLoad();
        initPermission();
    }

    private void initShowLoad() {
        if (myLocationStyle==null){
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.interval(2000);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            aMap.setMyLocationEnabled(true);
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
            },101);
        }
    }

    @Override
    protected void initBinding() {

    }

    @Override
    protected BaseViewModel createVM() {
        return null;
    }

    private void popWindow(){
        View view = LayoutInflater.from(this).inflate(R.layout.view_popwindow, null);
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setContentView(view);
        popupWindow.showAsDropDown(personHome,-550,-500);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapHome.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapHome.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapHome.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapHome.onSaveInstanceState(outState);
    }
}
