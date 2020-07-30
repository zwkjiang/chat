package com.example.friend.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.core.view.BaseFragment;
import com.example.friend.R;
import com.example.friend.adapter.PersonAdapter;
import com.example.friend.databinding.FragmentSeekfriendBinding;
import com.example.friend.viewmodel.SeekFriendViewModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

public class SeekFriendFragment extends BaseFragment<FragmentSeekfriendBinding, SeekFriendViewModel> {
    private EditText seekNameSeekFriend;
    private LinearLayout addSeekFriend;
    private LinearLayout saoSeekFriend;
    private RecyclerView recyclerSeekFriend;
    private PersonAdapter personAdapter;
    private ArrayList<UserEntity> listU;
    @SuppressLint("HandlerLeak")
    private Handler handler  = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                personAdapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void initEvent() {
        saoSeekFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        addSeekFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterManager.getInstance().route(RouterPath.PHONEACT);
            }
        });
        seekNameSeekFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                seekFriend();
            }
        });
    }

    private void seekFriend() {
        String trim = seekNameSeekFriend.getText().toString().trim();
        listU.clear();
        if (!trim.equals("")){
            LiveData<RequestEntity<List<UserEntity>>> request = vm.seekFriend(trim);
            request.observe(getActivity(), new Observer<RequestEntity<List<UserEntity>>>() {
                @Override
                public void onChanged(RequestEntity<List<UserEntity>> listRequestEntity) {
                    if (listRequestEntity!=null&&listRequestEntity.getData()!=null){
                        listU.addAll(listRequestEntity.getData());
                        handler.sendEmptyMessage(1);
                    }
                }
            });
        }else{
            listU.clear();
            handler.sendEmptyMessage(1);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (personAdapter==null){
            listU = new ArrayList<>();
            personAdapter = new PersonAdapter(listU);
            recyclerSeekFriend.setAdapter(personAdapter);
        }
    }

    @Override
    protected void initView(View view) {
        seekNameSeekFriend = (EditText)view.findViewById(R.id.seekName_seekFriend);
        addSeekFriend = (LinearLayout)view.findViewById(R.id.add_seekFriend);
        saoSeekFriend = (LinearLayout)view.findViewById(R.id.sao_seekFriend);
        recyclerSeekFriend = (RecyclerView)view.findViewById(R.id.recycler_seekFriend);
        recyclerSeekFriend.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initBinding() {

    }

    @Override
    protected SeekFriendViewModel createVM() {
        return new SeekFriendViewModel();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_seekfriend;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100){
            if (null!=data){
                Bundle extras = data.getExtras();
                if (extras==null){
                    return;
                }if (extras.getInt(CodeUtils.RESULT_TYPE)==CodeUtils.RESULT_SUCCESS){
                    String string = extras.getString(CodeUtils.RESULT_STRING);
                    LiveData<RequestEntity<Boolean>> oo = vm.addFriend("fb2f89d8adc1484ab8b75932627e434a");
                    oo.observe(this, new Observer<RequestEntity<Boolean>>() {
                        @Override
                        public void onChanged(RequestEntity<Boolean> booleanRequestEntity) {
                            if (booleanRequestEntity.getData()!=null&&booleanRequestEntity.getData().booleanValue()){
                                Intent intent = new Intent("shuaxin.friend");
                                getActivity().sendBroadcast(intent);
                            }
                        }
                    });
                }
            }
        }
    }
}
