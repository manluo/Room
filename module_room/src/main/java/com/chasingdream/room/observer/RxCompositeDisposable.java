package com.chasingdream.room.observer;

import android.util.Log;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author nyl
 * @des
 * @date 2020/6/8
 */
public class RxCompositeDisposable {
    //tag
    private static final String TAG = RxCompositeDisposable.class.getSimpleName();
    //去掉订阅 防止内存泄漏
    private final CompositeDisposable compositeDisposable;

    private RxCompositeDisposable() {
        compositeDisposable = new CompositeDisposable();
    }

    //对象
    public static RxCompositeDisposable getInstance() {
        return new RxCompositeDisposable();
    }

    /**
     * 处理Rx订阅者
     *
     * @return
     */
    private CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    /**
     * 添加到生命周期控制
     *
     * @param flowable 可观察的回调 不必要使用他
     * @param consumer
     * @param <T>
     */
    public <T> void addDisposable(Flowable<T> flowable, Consumer<T> consumer) {
        compositeDisposable.add(flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer));
    }

    /**
     * 添加到生命周期
     * @param single
     * @param singleObserver
     * @param <T>
     */
    public <T> void addDisposable(Single<T> single, RxSingleObserver<T> singleObserver) {
        single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSingleSubscribe(singleObserver));
    }

    /**
     * 添加到生命周期控制
     *
     * @param completable
     * @param action
     * @param <T>
     */
    public <T> void addDisposable(Completable completable, Action action) {
        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action));

    }

    /**
     * 获取 single标识符 回调 成功 以及 失败 以免crash发生
     *
     * @param singleObserver
     * @param <T>
     * @return
     */
    private <T> SingleObserver getSingleSubscribe(final RxSingleObserver<T> singleObserver) {
        return new SingleObserver<T>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.w(TAG, "===>onSubscribe");
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(T t) {
                Log.w(TAG, "===>onSuccess");
                if (singleObserver!=null){
                    singleObserver.onSuccess(t);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "===>onError");
                if (singleObserver!=null){
                    singleObserver.onError(e);
                }
            }
        };
    }

    /**
     * 清空内存
     */
    public void clear() {
        compositeDisposable.clear();
    }
}
