package com.example.friend.adapter;

import android.net.Uri;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.callback.RecyclerCallBack;
import com.example.friend.R;
import com.example.net.entity.UserEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class PersonAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    private RecyclerCallBack recyclerCallBack;

    public void setRecyclerCallBack(RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public PersonAdapter(@Nullable List<UserEntity> data) {
        super(R.layout.view_showfriend, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, UserEntity item) {
        SimpleDraweeView view = helper.getView(R.id.icon_show);
        Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
        view.setImageURI(parse);
        helper.setText(R.id.name_show,item.getUsername());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerCallBack!=null){
                    recyclerCallBack.getPosition(helper.getLayoutPosition());
                }
            }
        });
    }
}
