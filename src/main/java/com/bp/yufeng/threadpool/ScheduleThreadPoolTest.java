package com.bp.yufeng.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest {

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
        service.schedule(() -> System.out.println("executing task"), 5, TimeUnit.SECONDS);
        service.shutdown();
    }
}
