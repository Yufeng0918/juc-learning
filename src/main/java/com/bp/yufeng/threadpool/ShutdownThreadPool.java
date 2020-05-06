package com.bp.yufeng.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ShutdownThreadPool {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        IntStream.range(0, 10).forEach(i -> service.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() +  " executing task");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        TimeUnit.SECONDS.sleep(1);
//        service.shutdown();
//        System.out.println(service.isShutdown());
//        service.submit(() -> System.out.println("submit after shutdown"));
//
//        TimeUnit.SECONDS.sleep(10);
//        System.out.println(service.isTerminated());

        List<Runnable> runnableList = service.shutdownNow();
        System.out.println("after terminate");
        runnableList.forEach(r -> r.run());
    }
}
