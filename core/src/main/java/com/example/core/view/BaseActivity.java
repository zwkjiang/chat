package com.example.core.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.core.viewmodel.BaseViewModel;

public abstract class BaseActivity<Binding extends ViewDataBinding,VM extends BaseViewModel> extends AppCompatActivity {

    protected Binding binding;
    protected VM vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        vm = createVM();
        initBinding();
        initView();
        initData(savedInstanceState);
        initEvent();
    }

    protected abstract void initEvent();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initBinding();

    protected abstract VM createVM();

    protected abstract int getLayoutId();
}
