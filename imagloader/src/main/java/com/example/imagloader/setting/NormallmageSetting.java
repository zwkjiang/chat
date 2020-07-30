package com.example.imagloader.setting;

import android.widget.ImageView;

public class NormallmageSetting extends ImageSetting {
    private int imgRadius;
    public int getImgRadius(){
        return imgRadius;
    }


    public void setImgRadius(int imgRadius) {
        this.imgRadius = imgRadius;
    }

    public NormallmageSetting(Builder builder) {
        this.mView = builder.imageView;
        this.mPlaceholder = builder.placeHolder;
        this.imgRadius = builder.imgRadius;
        this.mUrl = builder.imgUrl;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static final class Builder{
        private String imgUrl;
        private ImageView imageView;
        private int placeHolder;
        private int imgRadius;
        private Builder(){}
        public Builder url(String _url){
            imgUrl =_url;
            return this;
        }
        public Builder imageView(ImageView _imageView){
            imageView = _imageView;
            return this;
        }

        public Builder placeHolder(int _placeHolder){
            placeHolder=_placeHolder;
            return this;
        }
        public Builder imageRadius(int _radius){
            imgRadius =_radius;
            return this;
        }
        public NormallmageSetting build(){
            return new NormallmageSetting(this);
        }
    }
}
