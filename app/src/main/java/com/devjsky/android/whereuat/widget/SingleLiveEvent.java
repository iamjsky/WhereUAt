package com.devjsky.android.whereuat.widget;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName            Event
 * Created by JSky on   2022-02-16
 * <p>
 * Description
 */
public class SingleLiveEvent<T> extends MutableLiveData<T> {

    private AtomicBoolean pending = new AtomicBoolean(false);
    @MainThread
    public void observeSingleEvent(LifecycleOwner owner, final Observer<T> observer) {

        if (hasActiveObservers()) {
            Log.w("로그", "Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, new Observer<T>() {
            @Override
            public void onChanged(@Nullable T t) {
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(t);
                }
            }
        });
    }

    @MainThread
    public void setValue(@Nullable T t) {
        pending.set(true);
        super.setValue(t);
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    public void call() {
        setValue(null);
    }




}
