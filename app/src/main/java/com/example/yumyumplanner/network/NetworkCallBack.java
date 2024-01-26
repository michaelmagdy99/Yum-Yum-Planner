package com.example.yumyumplanner.network;

public interface NetworkCallBack<T> {
    void onSuccessResult(T result);
    void onFailureResult(String message);
}
