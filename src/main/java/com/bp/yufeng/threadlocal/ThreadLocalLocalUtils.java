package com.bp.yufeng.threadlocal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ThreadLocalLocalUtils {

    static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));

    public String date(int seconds) {
        Date date = new Date(1000 * seconds);
        return dateFormatThreadLocal.get().format(date);
    }


    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(10);
        IntStream.range(0, 1000).forEach(i -> {
            service.submit(() -> {
                System.out.println(new ThreadLocalLocalUtils().date(i));});
        });

        service.shutdown();
    }
}
