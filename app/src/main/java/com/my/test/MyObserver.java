package com.my.test;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/15.
 */

public abstract class MyObserver<T> implements Observer<T> {
    private Disposable disposable;

    public Disposable getDisposable() {
        return disposable;
    }

    public abstract void onMyNext(T obj);
    public void onMySubscribe(Disposable disposable){
    };
    public void onMyCompleted(){
    };
    public void onMyError(Throwable e){
    };
    @Override
    public void onSubscribe(@NonNull Disposable disposable) {
        this.disposable=disposable;
        onMySubscribe(disposable);
    }
    @Override
    public void onNext(@NonNull T obj) {
        onMyNext(obj);
    }
    @Override
    public void onError(@NonNull Throwable e) {
        onMyError(e);
    }
    @Override
    public void onComplete() {
        onMyCompleted();
    }
}
