package com.bp.yufeng.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;


public class LongAdderCounter {

    public static void main(String[] args) {

        LongAdder counter = new LongAdder();
        ExecutorService service = Executors.newFixedThreadPool(20);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            service.submit(new Task(counter));
        }
        service.shutdown();

        while (!service.isTerminated()) { }
        long end = System.currentTimeMillis();
        System.out.println(counter.sum());
        System.out.println("LongAdder：" + (end - start));
    }

    private static class Task implements Runnable {

        private LongAdder counter;
        public Task(LongAdder counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        }
    }
}
