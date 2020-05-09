package com.bp.yufeng.atomic;

import java.util.concurrent.atomic.AtomicInteger;


public class AtomicIntegerIncreament implements Runnable {

    private static final AtomicInteger atomicInteger = new AtomicInteger();

    public void incrementAtomic() {
        atomicInteger.getAndIncrement();
    }

    private static volatile int basicCount = 0;

    public void incrementBasic() {
        basicCount++;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerIncreament r = new AtomicIntegerIncreament();
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("atomic result：" + atomicInteger.get());
        System.out.println("standard result：" + basicCount);
    }

    @Override
    public void run() {
        for (int i = 0; i < 20000; i++) {
            incrementAtomic();
            incrementBasic();
        }
    }
}
