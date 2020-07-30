package com.example.usercenter.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.baweigame.xmpplibrary.XmppManager;
import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.common.utils.MsgUtils;
import com.example.core.view.BaseActivity;
import com.example.storage.SharePreferenceUtils;
import com.example.usercenter.R;
import com.example.usercenter.databinding.ActivityLoginBinding;
import com.example.usercenter.viewmodel.LoginViewModel;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

@Route(path = RouterPath.LOGINACT)
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {
    private EditText nameLogin;
    private EditText passwordLogin;
    private Button goLogin;
    private TextView registerLogin;
    private CheckBox rememberLogin;
    private ImageView qqLogin;
    private ImageView weixinLogin;
    private ImageView weiboLogin;
    @Override
    protected void initEvent() {
        rememberLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    SharePreferenceUtils.put(LoginActivity.this,"check",true);
                    SharePreferenceUtils.put(LoginActivity.this,"name",nameLogin.getText().toString().trim());
                    SharePreferenceUtils.put(LoginActivity.this,"pwd",passwordLogin.getText().toString().trim());
                }else{
                    SharePreferenceUtils.remove(LoginActivity.this,"name");
                    SharePreferenceUtils.remove(LoginActivity.this,"pwd");
                    SharePreferenceUtils.remove(LoginActivity.this,"check");
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        boolean re = (boolean) SharePreferenceUtils.get(this, "check", false);
        if (re){
            MsgUtils.getInstance().show(this,"Ok");
            String name = (String) SharePreferenceUtils.get(this, "name", "");
            String password = (String) SharePreferenceUtils.get(this, "pwd", "");
            vm.loginEntity.setUsername(name);
            vm.loginEntity.setPwd(password);
            rememberLogin.setChecked(true);
        }
    }

    @Override
    protected void initView() {
        nameLogin = (EditText) findViewById(R.id.name_login);
        passwordLogin = (EditText) findViewById(R.id.password_login);
        goLogin = (Button) findViewById(R.id.go_login);
        registerLogin = (TextView) findViewById(R.id.register_login);
        rememberLogin = (CheckBox) findViewById(R.id.remember_login);
        qqLogin = (ImageView) findViewById(R.id.qq_login);
        weixinLogin = (ImageView) findViewById(R.id.weixin_login);
        weiboLogin = (ImageView) findViewById(R.id.weibo_login);

    }

    @Override
    protected void initBinding() {
        binding.setLogin(this);
        binding.setVm(vm);
    }

    @Override
    protected LoginViewModel createVM() {
        return new LoginViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.register_login) {//
            //  ARouter.getInstance().build(RouterPath.REGISTERACT).navigation();
                RouterManager.getInstance().route(RouterPath.REGISTERACT);
//            startActivity(new Intent(this, RegisterActivity.class));
        } else if (id == R.id.go_login) {
            login();
        }else if(id==R.id.qq_login){
            Platform plat = ShareSDK.getPlatform(QQ.NAME);
//移除授权状态和本地缓存，下次授权会重新授权
            plat.removeAccount(true);
//SSO授权，传false默认是客户端授权
            plat.SSOSetting(false);
//授权回调监听，监听oncomplete，onerror，oncancel三种状态
            plat.setPlatformActionListener(platformActionListener);
//抖音登录适配安卓9.0
//ShareSDK.setActivity(MainActivity.this);
//要数据不要功能，主要体现在不会重复出现授权界面
            plat.showUser(null);
        }else if(id==R.id.weixin_login){
            Platform plat = ShareSDK.getPlatform(Wechat.NAME);
//移除授权状态和本地缓存，下次授权会重新授权
            plat.removeAccount(true);
//SSO授权，传false默认是客户端授权
            plat.SSOSetting(false);
//授权回调监听，监听oncomplete，onerror，oncancel三种状态
            plat.setPlatformActionListener(platformActionListener);
//抖音登录适配安卓9.0
//ShareSDK.setActivity(MainActivity.this);
//要数据不要功能，主要体现在不会重复出现授权界面
            plat.showUser(null);
        }else if(id==R.id.weibo_login){
            Platform plat = ShareSDK.getPlatform(SinaWeibo.NAME);
//移除授权状态和本地缓存，下次授权会重新授权
            plat.removeAccount(true);
//SSO授权，传false默认是客户端授权
            plat.SSOSetting(false);
//授权回调监听，监听oncomplete，onerror，oncancel三种状态
            plat.setPlatformActionListener(platformActionListener);
//抖音登录适配安卓9.0
//ShareSDK.setActivity(MainActivity.this);
//要数据不要功能，主要体现在不会重复出现授权界面
            plat.showUser(null);
        }
    }

    @SuppressLint("CheckResult")
    private void login() {
        Observable.just(vm.login(),loginIm())
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        return 404;
                    }
                }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    RouterManager.getInstance().route(RouterPath.HOMEACT);
            }
        });
//        LiveData<RequestEntity<UserEntity>> login = vm.login();
//        login.observe(this, new Observer<RequestEntity<UserEntity>>() {
//            @Override
//            public void onChanged(RequestEntity<UserEntity> userEntityRequestEntity) {
//                if (userEntityRequestEntity!=null&&userEntityRequestEntity.getData()!=null){
//                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                    RouterManager.getInstance().route(RouterPath.HOMEACT);
//                }
//            }
//        });
    }
    private Observable loginIm(){
        Observable<Integer> integerObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        XmppManager.getInstance().getXmppUserManager().login(nameLogin.getText().toString(), passwordLogin.getText().toString().trim());
                    }
                }).start();
            }
        });
        return integerObservable;
    }
    private PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    };
}
