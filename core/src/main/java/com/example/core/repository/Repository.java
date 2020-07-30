package com.example.core.repository;

import androidx.lifecycle.LifecycleOwner;

import com.example.core.model.IModel;

public abstract class Repository<T extends IModel> {
    public LifecycleOwner owner;
    public T mModel;

    public Repository() {
        mModel = createModel();
    }

    public Repository(LifecycleOwner owner) {
        this.owner = owner;
    }



    protected abstract T createModel();


    protected T getmModel(){
        return mModel;
    }
}