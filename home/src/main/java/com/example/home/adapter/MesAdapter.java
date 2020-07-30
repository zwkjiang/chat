package com.example.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.home.R;

import java.util.List;

public class MesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MesAdapter( @Nullable List<String> data) {
        super(R.layout.view_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
