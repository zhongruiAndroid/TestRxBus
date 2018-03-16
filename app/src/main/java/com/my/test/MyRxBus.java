package com.my.test;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Administrator on 2017/6/26.
 */

public class MyRxBus {
    private static volatile MyRxBus myRxBus;
    private final Subject<Object> publishSubject;
    private final Subject<Object> replaySubject;

    private MyRxBus() {
        publishSubject = PublishSubject.create().toSerialized();
        replaySubject = ReplaySubject.create().toSerialized();

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Exception {

            }
        }, BackpressureStrategy.LATEST);

    }

    public static MyRxBus getInstance() {
        if (myRxBus == null) {
            synchronized (MyRxBus.class) {
                if (myRxBus == null) {
                    myRxBus = new MyRxBus();
                }
            }
        }
        return myRxBus;
    }

    // 发送一个新的事件
    public void post(Object event) {
        publishSubject.onNext(event);
    }

    // 发送一个新的事件
    public void postRePlay(Object event) {
        replaySubject.onNext(event);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toFlowable(Class<T> eventType) {
        return publishSubject.ofType(eventType);
    }
    public <T> Disposable getEvent(Class<T> eventType, MyObserver observer) {
        MyRxBus.getInstance().toFlowable(eventType).safeSubscribe(observer);
        return observer.getDisposable();
    }

    public <T> Disposable getEvent(Class<T> eventType,  Consumer<T> consumer) {
        return MyRxBus.getInstance().toFlowable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }
    public <T> Disposable getEvent(Class<T> eventType,  Consumer<T> onNext,Consumer onError) {
        return MyRxBus.getInstance().toFlowable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError);
    }
    public <T> Disposable getEvent(Class<T> eventType, Consumer<T> onNext, Consumer onError, Action onComplete) {
        return MyRxBus.getInstance().toFlowable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError,onComplete);
    }
    public <T> Disposable getEvent(Class<T> eventType, Consumer<T> onNext, Consumer onError, Action onComplete,Consumer  disposable) {
        return MyRxBus.getInstance().toFlowable(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError,onComplete,disposable);
    }



    ////////////////////////////////ForRePlay////////////////////////////////////////////
    public <T> Observable<T> toObservableForRePlay(Class<T> eventType) {
        return replaySubject.ofType(eventType);
    }

    public <T> Disposable getEventForRePlay(Class<T> eventType, final MyObserver observer) {
        MyRxBus.getInstance().toObservableForRePlay(eventType).observeOn(AndroidSchedulers.mainThread()).safeSubscribe(observer);
        return observer.getDisposable();
    }



    public <T> Disposable getEventForRePlay(Class<T> eventType,  Consumer<T> consumer) {
        return MyRxBus.getInstance().toObservableForRePlay(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer);
    }
    public <T> Disposable getEventForRePlay(Class<T> eventType,  Consumer<T> onNext,Consumer onError) {
        return MyRxBus.getInstance().toObservableForRePlay(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError);
    }
    public <T> Disposable getEventForRePlay(Class<T> eventType, Consumer<T> onNext, Consumer onError, Action onComplete) {
        return MyRxBus.getInstance().toObservableForRePlay(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError,onComplete);
    }
    public <T> Disposable getEventForRePlay(Class<T> eventType, Consumer<T> onNext, Consumer onError, Action onComplete,Consumer  disposable) {
        return MyRxBus.getInstance().toObservableForRePlay(eventType).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext,onError,onComplete,disposable);
    }



    public void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    public void disposeForRePlay(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
    /* private final Subject<Object, Object> bus;
    public MyRxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
        mStickyEventMap = new ConcurrentHashMap<>();
    }
    public static MyRxBus getInstance() {
        if (rxBusInstance == null) {
            synchronized (MyRxBus.class) {
                if (rxBusInstance == null) {
                    rxBusInstance = new MyRxBus();
                }
            }
        }
        return rxBusInstance;
    }
    public void post(Object event) {
        bus.onNext(event);
    }
    public  <T> Observable<T> getEvent (Class<T> eventType) {
        return toFlowable(eventType);
    }
    public  <T> Observable<T> toFlowable (Class<T> eventType) {
        return bus.ofType(eventType);
    }
    public  <T> Subscription getEvent(Class<T> eventType, MySubscriber sub){
        return MyRxBus.getInstance().toFlowable(eventType).subscribe(sub);
    }

    *//***************************************支持Sticky************************************************//*
    private final Map<Class<?>, Object> mStickyEventMap;
    public void postSticky(Object event) {
        synchronized (mStickyEventMap) {
            mStickyEventMap.put(event.getClass(), event);
        }
        post(event);
    }
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = bus.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);
            if (event != null) {
                return observable.mergeWith(Observable.create(new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        subscriber.onNext(eventType.cast(event));
                    }
                }));
            } else {
                return observable;
            }
        }
    }
    *//**根据eventType获取Sticky事件*//*
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }
    *//** 移除指定eventType的Sticky事件*//*
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }
    *//*** 移除所有的Sticky事件*//*
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
    }*/
    /***************************************支持Sticky************************************************/
}
