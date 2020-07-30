package com.example.message.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.arouter.RouterPath;
import com.example.core.view.BaseActivity;
import com.example.core.viewmodel.BaseViewModel;
import com.example.message.R;
import com.example.message.adapter.MsgAdapter;
import com.example.message.entity.MsgEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

@Route(path = RouterPath.MEGACT)
public class MsgActivity extends BaseActivity {
    private EditText seekMsg;
    private RecyclerView recyclerMsg;
    private ImageView leftIcon;
    private TextView titleText;
    private ImageView rightIcon;
    private MsgAdapter adapter;
    private ArrayList<MsgEntity> list;


    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTool();
        initAdp();
    }

    private void initAdp() {
        if (adapter == null){
            list = new ArrayList<>();
            MsgEntity msgEntity = new MsgEntity();
            msgEntity.setToName("232323");
            msgEntity.setTime(getTime());
            list.add(msgEntity);
            adapter = new MsgAdapter(list);
            recyclerMsg.setAdapter(adapter);
        }
    }

    private void initTool() {
        leftIcon.setImageResource(R.drawable.fanhui);
        titleText.setText("消息");
        rightIcon.setImageResource(R.mipmap.add);
    }
    private String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+O8"));
        String data = dateFormat.format(new Date());
        return data;
    }
    @Override
    protected void initView() {
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        titleText = (TextView) findViewById(R.id.title_text);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        seekMsg = (EditText) findViewById(R.id.seek_msg);
        recyclerMsg = (RecyclerView) findViewById(R.id.recycler_msg);
        recyclerMsg.setLayoutManager(new LinearLayoutManager(this));
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
        return R.layout.activity_msg;
    }
}
