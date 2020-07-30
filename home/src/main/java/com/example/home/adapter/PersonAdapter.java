package com.example.home.adapter;

import android.net.Uri;
import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.callback.RecyclerCallBack;
import com.example.home.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class PersonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private RecyclerCallBack recyclerCallBack;

    public void setRecyclerCallBack(RecyclerCallBack recyclerCallBack) {
        this.recyclerCallBack = recyclerCallBack;
    }

    public PersonAdapter(@Nullable List<String> data) {
        super(R.layout.view_person, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        SimpleDraweeView view = helper.getView(R.id.tou_person);
        Uri parse = Uri.parse("http://i1.shaodiyejin.com/uploads/tu/201804/9999/6a36ac5da5.jpg");
        view.setImageURI(parse);
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
