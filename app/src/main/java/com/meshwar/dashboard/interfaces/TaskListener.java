package com.meshwar.dashboard.interfaces;

public interface TaskListener<T> {
    void onComplete(T item);
}
