package com.chasingdream.room.observer;

/**
 * @author nyl
 * @des
 * @date 2020/6/8
 */
public interface RxSingleObserver<T> {

    /**
     * 上传成功
     *
     * @param t
     */
    void onSuccess(T t);

    /**
     * 上传失败
     *
     * @param e
     */
    void onError(Throwable e);

}
