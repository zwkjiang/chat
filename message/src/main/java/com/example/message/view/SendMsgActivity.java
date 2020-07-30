package com.example.message.view;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.arouter.RouterManager;
import com.example.arouter.RouterPath;
import com.example.common.MessageType;
import com.example.core.view.BaseActivity;
import com.example.core.viewmodel.BaseViewModel;
import com.example.message.R;
import com.example.message.adapter.PersonMsgAdapter;
import com.example.message.camera.CameraActivity;
import com.example.message.databinding.ActivitySendmesBinding;
import com.example.message.entity.MessageEntity;
import com.example.message.entity.MsgEntity;
import com.example.message.spl.MsgSql;
import com.example.message.viewmodel.SendMsgViewModel;
import com.example.net.entity.RequestEntity;
import com.example.net.entity.UserEntity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

@Route(path = RouterPath.SENDACT)
public class SendMsgActivity extends BaseActivity<ActivitySendmesBinding,SendMsgViewModel> implements OnClickListener {
    private ImageView leftIcon;
    private TextView titleText;
    private ImageView right2Icon;
    private ImageView rightIcon;
    private RecyclerView recyclerSendMes;
    private EditText textSendMes;
    private Button sendSendMes;
    private ImageView voiceSend;
    private ImageView pictureSend;
    private ImageView cameraSend;
    private ImageView locationSend;
    private ImageView faceSend;
    private ImageView moreSend;
    private MsgSql msgSql;
    private SQLiteDatabase write;
    private MessageEntity messageEntity;
    private ArrayList<MsgEntity> listM;
    private PersonMsgAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Uri uri;
    private String path;
    @Autowired( name = "userEntity")
    public UserEntity userEntity;
    @Override
    protected void initEvent() {
        leftIcon.setOnClickListener(this);
        pictureSend.setOnClickListener(this);
        cameraSend.setOnClickListener(this);
        right2Icon.setOnClickListener(this);
        sendSendMes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(textSendMes.getText().toString().trim(), MessageType.TEXT);
            }
        });
    }

    private void addSql(int type,int typeUser) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fromname","121212");
        contentValues.put("toname",userEntity.getUsername());
        contentValues.put("time",getTime());
        contentValues.put("message",textSendMes.getText().toString().trim());
        contentValues.put("type",type);
        contentValues.put("typeuser",typeUser);
        write.insert("msg",null,contentValues);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTool();
        initSql();
        initAdp();
        initThread();
        initMeg();
    }


    private void initAdp() {
        if (adapter==null){
            listM = new ArrayList<>();
            adapter = new PersonMsgAdapter(listM);
            recyclerSendMes.setAdapter(adapter);
        }
    }

    private void initSql() {
        if (msgSql==null){
            msgSql = new MsgSql(this);
            write = msgSql.getWritableDatabase();
        }
    }

    private void initMeg() {
        if (messageEntity==null){
            messageEntity = new MessageEntity();
        }
        messageEntity.setFromuser("121212");
        messageEntity.setTouser(userEntity.getUsername());
    }

    private void initTool() {
        leftIcon.setImageResource(R.drawable.fanhui);
        titleText.setText(userEntity.getUsername());
        right2Icon.setImageResource(R.mipmap.call);
        rightIcon.setImageResource(R.mipmap.menu);
    }
    private void sendMessage(final String meg, final int type){
        String time = getTime();
        messageEntity.setMsgtime(time);
        messageEntity.setMsg(meg);
        LiveData<RequestEntity<Boolean>> isSend = vm.sendMessage(messageEntity);
        isSend.observe(SendMsgActivity.this, new Observer<RequestEntity<Boolean>>() {
            @Override
            public void onChanged(RequestEntity<Boolean> booleanRequestEntity) {
                if (booleanRequestEntity!=null&&booleanRequestEntity.getData()){
                    addSql(type,0);
                    addAdp(meg,type);
                }
            }
        });
    }

    private void addAdp(String meg,int type) {
        MsgEntity msgEntity = new MsgEntity("121212", getTime(), meg, userEntity.getUsername(), 0, type);
        listM.add(msgEntity);
        adapter.notifyDataSetChanged();
        layoutManager.scrollToPositionWithOffset(listM.size()-1,-100);
    }

    @Override
    protected void initView() {
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        titleText = (TextView) findViewById(R.id.title_text);
        right2Icon = (ImageView) findViewById(R.id.right2_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        recyclerSendMes = (RecyclerView) findViewById(R.id.recycler_sendMes);
        textSendMes = (EditText) findViewById(R.id.text_sendMes);
        sendSendMes = (Button) findViewById(R.id.send_sendMes);
        voiceSend = (ImageView) findViewById(R.id.voice_send);
        pictureSend = (ImageView) findViewById(R.id.picture_send);
        cameraSend = (ImageView) findViewById(R.id.camera_send);
        locationSend = (ImageView) findViewById(R.id.location_send);
        faceSend = (ImageView) findViewById(R.id.face_send);
        moreSend = (ImageView) findViewById(R.id.more_send);
        layoutManager = new LinearLayoutManager(this);
        recyclerSendMes.setLayoutManager(layoutManager);
        ARouter.getInstance().inject(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private String getTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+O8"));
        String data = dateFormat.format(new Date());
        return data;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==R.id.picture_send){
            openPicture();
        }else if (id==R.id.camera_send){
            toCamera();
//            startActivityForResult(new Intent(SendMsgActivity.this, CameraActivity.class),103);
        }else if (id==R.id.right2_icon){
            openCall();
        }else if (id==R.id.left_icon){
            RouterManager.getInstance().route(RouterPath.MEGACT);
        }
    }

    private void toCamera() {
        path = getExternalCacheDir().getAbsolutePath()+ File.separator+"222.png";
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                向意图对象当中，传入指定的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
        startActivityForResult(intent,100);
    }
    private void openCall() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+"110"));
        startActivity(intent);
    }

    private void openPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent,101);
    }

    private void initThread() {
    }
    @Override
    protected void initBinding() {
        binding.setSend(this);
        binding.setVm(vm);
    }

    @Override
    protected SendMsgViewModel createVM() {
        return new SendMsgViewModel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sendmes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&requestCode==101){
            if (data!=null){
                Uri dataPhoto = data.getData();
                addAdp(dataPhoto.toString(),MessageType.IMG);
            }
        }
        if (resultCode==RESULT_OK&&requestCode==100){
            addAdp(path.toString(),MessageType.IMG);
        }
    }
}
