package com.bp.yufeng.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SelfSpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<>();


    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {
            System.out.println("spinning");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }

    public static void main(String[] args) {

        SelfSpinLock spinLock = new SelfSpinLock();
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + " try to get self spin lock");
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + "get self spin lock already");

            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                spinLock.unlock();
            }
        };


        new Thread(r).start();
        new Thread(r).start();
    }
}
