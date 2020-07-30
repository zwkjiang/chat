package com.example.core.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

import com.example.core.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class BaseViewModel extends ViewModel implements LifecycleObserver {

    protected Map<String,Repository> repositoryMap;

    protected LifecycleOwner owner;

    public BaseViewModel(LifecycleOwner owner) {
        this.owner = owner;
        repositoryMap = new HashMap<>();
    }

    public BaseViewModel() {
        repositoryMap = new HashMap<>();
    }

    protected void registerRepository(String key,Repository repository){
        if (repositoryMap.containsKey(key)){
            return;
        }else{
            repositoryMap.put(key,repository);
        }
    }

    protected void unRegisterRepository(String key){
        if (repositoryMap.containsKey(key)){
            repositoryMap.remove(key);
        }
    }

    protected <SubRepository extends Repository> SubRepository getRepository(String key){
        if (repositoryMap.containsKey(key)){
            return (SubRepository)repositoryMap.get(key);
        }
        return null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void disConnectOwner(){
        repositoryMap.clear();
        repositoryMap = null;
    }
}
