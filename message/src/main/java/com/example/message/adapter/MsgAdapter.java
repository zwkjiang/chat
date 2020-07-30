package com.example.message.adapter;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.message.R;
import com.example.message.entity.MsgEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class MsgAdapter extends BaseQuickAdapter<MsgEntity, BaseViewHolder> {
    public MsgAdapter(@Nullable List<MsgEntity> data) {
        super(R.layout.view_person, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgEntity item) {
        SimpleDraweeView view = helper.getView(R.id.tou_person);
        Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
        view.setImageURI(parse);
        helper.setText(R.id.name_person,item.getToName());
        helper.setText(R.id.time_person,item.getTime());
        helper.setText(R.id.number_person,"4");
        helper.setText(R.id.msg_person,"qwer");
    }
}
