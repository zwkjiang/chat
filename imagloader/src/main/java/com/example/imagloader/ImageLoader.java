package com.example.imagloader;

import android.content.Context;

import com.example.imagloader.base.ImgLoaderStrategy;
import com.example.imagloader.impl.GlidStrategy;
import com.example.imagloader.setting.ImageSetting;

public class ImageLoader {

    private ImgLoaderStrategy imgLoaderStrategy;
    private volatile static ImageLoader instance=null;
    private ImageLoader(){
        imgLoaderStrategy = new GlidStrategy();
    }
    public static ImageLoader getInstance(){
        if(instance==null){
            synchronized (ImageLoader.class){
                if (instance==null){
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }
    public void loadImage(Context context, ImageSetting setting){
        if (imgLoaderStrategy==null){
            throw new NullPointerException("imgLoaderStrategy is null");
        }
        if(context==null){
            throw new IllegalArgumentException("setting is null");
        }
        imgLoaderStrategy.loadImage(context,setting);
    }
    public void initStrategy(ImgLoaderStrategy _imgLoaderStrategy){
        this.imgLoaderStrategy = _imgLoaderStrategy;
    }
    public ImgLoaderStrategy getImgLoaderStrategy(){
        return this.imgLoaderStrategy;
    }
}
