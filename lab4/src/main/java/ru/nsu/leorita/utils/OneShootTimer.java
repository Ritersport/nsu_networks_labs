package ru.nsu.leorita.utils;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OneShootTimer {

    private final long delay;
    private final Runnable task;

    private boolean cancelled = false;
    private Disposable disposable;

    public OneShootTimer(long delay, Runnable task) {
        this.delay = delay;
        this.task = task;
    }

    public void start() {
        if (cancelled) {
            throw new IllegalStateException("Timer cancelled");
        }
        disposable = Observable.create(emitter -> {
            Thread.sleep(delay);
            task.run();
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }

    public void cancel() {
        if (disposable != null) {
            cancelled = true;
            disposable.dispose();
        }
    }
}
