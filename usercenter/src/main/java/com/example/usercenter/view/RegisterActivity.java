package com.example.usercenter.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baweigame.xmpplibrary.XmppManager;
import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.common.utils.MsgUtils;
import com.example.core.view.BaseActivity;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;
import com.example.usercenter.R;
import com.example.usercenter.databinding.ActivityRegisterBinding;
import com.example.usercenter.viewmodel.RegisterViewModel;
@Route(path = RouterPath.REGISTERACT)
public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> {
    private EditText nameRegister;
    private EditText passwordRegister;
    private Button goRegister;
    private TextView loginRegister;
    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        nameRegister = (EditText) findViewById(R.id.name_register);
        passwordRegister = (EditText) findViewById(R.id.password_register);
        goRegister = (Button) findViewById(R.id.go_register);
        loginRegister = (TextView) findViewById(R.id.login_register);

    }

    @Override
    protected void initBinding() {
        binding.setViewmodel(vm);
        binding.setRegister(this);
    }

    @Override
    protected RegisterViewModel createVM() {
        return new RegisterViewModel();
    }
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.go_register) {
            register();
        } else if (id == R.id.login_register) {
            RouterManager.getInstance().route(RouterPath.LOGINACT);
//            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        }
    }

    private void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                XmppManager.getInstance().getXmppUserManager().createAccount(nameRegister.getText().toString().trim(),passwordRegister.getText().toString().trim());
            }
        }).start();
        LiveData<RequestEntity<UserEntity>> register = vm.register();
        register.observe(this, new Observer<RequestEntity<UserEntity>>() {
            @Override
            public void onChanged(RequestEntity<UserEntity> userEntityRequestEntity) {
                if (userEntityRequestEntity.getData().getUsername().equals(nameRegister.getText().toString().trim())){
                    MsgUtils.getInstance().show(RegisterActivity.this,"注册成功");
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }
}
