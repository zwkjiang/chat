package com.example.imagloader.impl;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.imagloader.base.ImgLoaderStrategy;
import com.example.imagloader.setting.NormallmageSetting;

public class GlidStrategy implements ImgLoaderStrategy<NormallmageSetting> {
    @Override
    public void loadImage(Context context, NormallmageSetting setting) {
        RequestOptions requestOptions = null;
        if(setting.getImgRadius()>0){
            requestOptions = RequestOptions.bitmapTransform(new RoundedCorners(setting.getImgRadius()));
        }else {
            requestOptions = new RequestOptions();
        }
        if (setting.getmPlaceholder()>0){
            requestOptions.placeholder(setting.getmPlaceholder());
            requestOptions.error(setting.getmPlaceholder());
        }
        Glide.with(context).load(setting.getmUrl()).apply(requestOptions).into(setting.getmView());

    }
}
