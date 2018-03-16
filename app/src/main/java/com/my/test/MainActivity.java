package com.my.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kw.rxbus.RxBus;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


//import rx.Subscriber;
//import rx.subjects.PublishSubject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String TAG=this.getClass().getSimpleName();
    TextView tv1,tv2,tv3,tv11,tv22,tv33,tv_clear;
    int i1;
    int i2;
    int i3;
    //    private PublishRelay<Test> relay;
    private PublishSubject<Integer> objectPublishSubject1;
    private Subject<Object> objectSubject;

//    private PublishRelay<Integer> relay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1= (TextView) findViewById(R.id.tv1);
        tv2= (TextView) findViewById(R.id.tv2);
        tv3= (TextView) findViewById(R.id.tv3);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        tv11= (TextView) findViewById(R.id.tv11);
        tv22= (TextView) findViewById(R.id.tv22);
        tv33= (TextView) findViewById(R.id.tv33);
        tv_clear= (TextView) findViewById(R.id.tv_clear);


        initRxbus();


    }

    private void initRxbus() {
        RxBus.getInstance();


        objectSubject = PublishSubject.create().toSerialized();
        objectSubject.onNext(new Test());
        objectSubject.subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG+"===","=onSubscribe==");
                Log.i(TAG+"===","11==="+Thread.currentThread().getName());
            }
            @Override
            public void onNext(@NonNull Object integer) {
                Log.i(TAG+"===","22==="+Thread.currentThread().getName());
                Test a= (Test) integer;
                Log.i(TAG+"===","==="+a.flag);
                if(a.flag==1){
                    try {
//                        throw new NullPointerException("null");
                    }catch (Exception e){
                        onError(e);
                    }
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG+"===","=onError=="+e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
       /* objectSubject.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object integer) throws Exception {
                Test a= (Test) integer;
                Log.i(TAG+"===","==="+a.flag);
                if(a.flag==1){
                    try {
                        throw new NullPointerException("null");
                    }catch (Exception e){
                        onError(e);
                    }
                }
            }
        });*/
//        objectSubject.onNext(new Test());
    }

    public void test(){
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:
                objectSubject.onNext(new Test(1));
//                objectPublishSubject1.onNext(4);
//                objectPublishSubject.onNext(6);
//                objectPublishSubject.onNext(new Test(1));
//                RxBus.getInstance().post(new Test(1));
//                RxBus.getInstance().post(new Test2());
            break;
            case R.id.tv2:
//                RxBus.getInstance().post(new Test());
//                RxBus.getInstance().post(new Test2());
//                objectPublishSubject.onNext(7);
//                objectPublishSubject.onNext(new Test());
//                objectPublishSubject.onNext(new Test2());
            break;
            case R.id.tv3:
//                objectPublishSubject.onNext(11);
//                objectPublishSubject.onNext(22);
            break;
            case R.id.tv_clear:

            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        event1.unsubscribe();
//        event2.unsubscribe();
    }
}
