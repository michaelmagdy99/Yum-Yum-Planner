package com.example.yumyumplanner.remote.api;

public interface NetworkCallBack<T> {
    void onSuccessResult(T result);
    void onFailureResult(String message);
}
