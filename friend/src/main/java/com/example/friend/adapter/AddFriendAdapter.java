package com.example.friend.adapter;

import android.net.Uri;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.friend.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class AddFriendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AddFriendAdapter( @Nullable List<String> data) {
        super(R.layout.view_addfriend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        SimpleDraweeView view = helper.getView(R.id.head_addFriend);
        Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
        view.setImageURI(parse);
    }
}
