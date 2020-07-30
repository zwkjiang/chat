package com.example.friend.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.common.callback.RecyclerCallBack;
import com.example.common.utils.LogUtils;
import com.example.core.view.BaseFragment;
import com.example.core.viewmodel.BaseViewModel;
import com.example.friend.R;
import com.example.friend.adapter.LetterAdapter;
import com.example.friend.adapter.PersonAdapter;
import com.example.friend.databinding.FragmentGoodBinding;
import com.example.friend.viewmodel.GoodViewModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodFragment extends BaseFragment<FragmentGoodBinding, GoodViewModel> {
    private RecyclerView recyclerGood;
    private RecyclerView letterGood;
    private LetterAdapter letterAdapter;
    private ArrayList<String> listL;
    private PersonAdapter personAdapter;
    private ArrayList<UserEntity> listU;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initLetter();
        initPerson();
    }

    public void initPerson() {
        if (personAdapter==null){
            listU = new ArrayList<>();
            personAdapter = new PersonAdapter(listU);
            personAdapter.setRecyclerCallBack(new RecyclerCallBack() {
                @Override
                public void getPosition(int position) {
                    Map<String, UserEntity> map = new HashMap<>();
                    map.put("userEntity",listU.get(position));
                    Log.d("zwk",listU.get(position).toString());
                    RouterManager.getInstance().route(RouterPath.SENDACT,(Map)map);
                }
            });
            recyclerGood.setAdapter(personAdapter);
        }
        LiveData<RequestEntity<List<UserEntity>>> friend = vm.getFriend();
        friend.observe(this, new Observer<RequestEntity<List<UserEntity>>>() {
            @Override
            public void onChanged(RequestEntity<List<UserEntity>> listRequestEntity) {
                if (listRequestEntity!=null&&listRequestEntity.getData()!=null){
                    List<UserEntity> data = listRequestEntity.getData();
                    listU.clear();
                    listU.addAll(data);
                    personAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initLetter() {
        if (listL==null){
            listL= new ArrayList<>();
            for (int i = 65;i<=90;i++){
                listL.add(""+(char)i);
            }
        }
        if (letterAdapter==null){
            letterAdapter = new LetterAdapter(listL);
            letterGood.setAdapter(letterAdapter);
        }
    }

    @Override
    protected void initView(View view) {
        recyclerGood = (RecyclerView) view. findViewById(R.id.recycler_good);
        letterGood = (RecyclerView) view.findViewById(R.id.letter_good);
        recyclerGood.setLayoutManager(new LinearLayoutManager(getContext()));
        letterGood.setLayoutManager(new LinearLayoutManager(getContext()));
        initBroad();
    }

    private void initBroad() {
        MyBroadCase myBroadCase = new MyBroadCase();
        IntentFilter filter = new IntentFilter();
        filter.addAction("shuaxin.friend");
        getContext().registerReceiver(myBroadCase,filter);
    }

    @Override
    protected void initBinding() {

    }

    @Override
    protected GoodViewModel createVM() {
        return new GoodViewModel();
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_good;
    }
    public class MyBroadCase extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            initPerson();
        }
    }
}
