package ru.nsu.leorita;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        var obsTest = Observable.create(emitter -> {
            while(true) {
                Thread.sleep(1000);
                emitter.onNext(1);
            }
        });

        obsTest.subscribe(
                value -> {
                    System.out.println(value);
                }
        );



        Thread.sleep(20000);

    }
}