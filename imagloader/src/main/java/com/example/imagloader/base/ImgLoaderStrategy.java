package com.example.imagloader.base;

import android.content.Context;

import com.example.imagloader.setting.ImageSetting;

public interface ImgLoaderStrategy<Setting extends ImageSetting> {
    void loadImage(Context context,Setting setting);
}
