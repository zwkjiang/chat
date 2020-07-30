package com.example.message.adapter;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.MessageType;
import com.example.message.entity.MsgEntity;
import com.example.message.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class PersonMsgAdapter extends BaseMultiItemQuickAdapter<MsgEntity, BaseViewHolder> {

    public PersonMsgAdapter(List<MsgEntity> data) {
        super(data);
        addItemType(0,R.layout.view_right);
        addItemType(1,R.layout.view_left);
    }

    @Override
    protected void convert(BaseViewHolder helper, MsgEntity item) {
        int type = item.getType();
        if (type==0){
            SimpleDraweeView view = helper.getView(R.id.tou_right);
            Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
            view.setImageURI(parse);
            ImageView view1 = helper.getView(R.id.tu_right);
            TextView textView = helper.getView(R.id.meg_right);
            if (item.getMegType()== MessageType.TEXT){
                view1.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                helper.setText(R.id.meg_right,item.getMessage());
            }else if (item.getMegType()==MessageType.IMG){
                textView.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(item.getMessage()).into(view1);
            }
        }else{
            SimpleDraweeView view = helper.getView(R.id.tou_left);
            Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
            view.setImageURI(parse);
            if (item.getMegType()== MessageType.TEXT){
                helper.setText(R.id.meg_left,item.getMessage());
            }else if (item.getMegType()==MessageType.IMG){
                TextView textView = helper.getView(R.id.meg_left);
                textView.setVisibility(View.GONE);
                ImageView view1 = helper.getView(R.id.tu_left);
                Glide.with(mContext).load(item.getMessage()).into(view1);
            }
        }
    }
}
