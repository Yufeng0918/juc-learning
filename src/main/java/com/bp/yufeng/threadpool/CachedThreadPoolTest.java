package com.bp.yufeng.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CachedThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        IntStream.range(0, 10000).forEach(i -> service.execute(() -> System.out.println(Thread.currentThread().getName() +  " executing task")));
        service.shutdown();
    }
}
