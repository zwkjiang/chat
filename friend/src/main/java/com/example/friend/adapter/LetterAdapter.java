package com.example.friend.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.friend.R;

import java.util.List;

public class LetterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LetterAdapter(@Nullable List<String> data) {
        super(R.layout.view_letter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.zi_letter,item);
    }
}
