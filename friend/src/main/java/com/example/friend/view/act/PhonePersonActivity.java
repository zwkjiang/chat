package com.example.friend.view.act;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.arouter.RouterPath;
import com.example.core.view.BaseActivity;
import com.example.friend.R;
import com.example.friend.adapter.PhoneAdapter;
import com.example.friend.databinding.ActivityPhonepersonBinding;
import com.example.friend.viewmodel.PhonePersonViewModel;

import java.util.ArrayList;
@Route(path = RouterPath.PHONEACT)
public class PhonePersonActivity extends BaseActivity<ActivityPhonepersonBinding, PhonePersonViewModel> {
    private ImageView leftIcon;
    private TextView titleText;
    private ImageView rightIcon;
    private RecyclerView recyclerPhone;
    private PhoneAdapter phoneAdapter;
    private ArrayList<String> listS;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTool();
        initPhone();
        initRecycler();
    }

    private void initTool() {
        leftIcon.setImageResource(R.drawable.fanhui);
        titleText.setText("手机联系人");
    }

    private void initRecycler() {
        if (phoneAdapter==null){
            phoneAdapter = new PhoneAdapter(listS);
            recyclerPhone.setAdapter(phoneAdapter);
        }
    }

    private void initPhone() {
        if (listS==null){
            listS = new ArrayList<>();
        }
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] str = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        Cursor query = resolver.query(uri, str, null, null, null, null);
        while(query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(str[0]));
            listS.add(name);
        }
        query.close();
    }

    @Override
    protected void initView() {
        recyclerPhone = (RecyclerView) findViewById(R.id.recycler_phone);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        titleText = (TextView) findViewById(R.id.title_text);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        recyclerPhone.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initBinding() {

    }

    @Override
    protected PhonePersonViewModel createVM() {
        return new PhonePersonViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phoneperson;
    }
}
