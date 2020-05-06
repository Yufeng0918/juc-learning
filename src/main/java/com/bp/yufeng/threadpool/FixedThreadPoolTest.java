package com.bp.yufeng.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class FixedThreadPoolTest {

    public static void main(String[] args) {
       ExecutorService service = Executors.newFixedThreadPool(4);
       IntStream.range(0, 10000).forEach(i -> service.execute(() -> System.out.println(Thread.currentThread().getName() +  " executing task")));
       service.shutdown();
    }
}
