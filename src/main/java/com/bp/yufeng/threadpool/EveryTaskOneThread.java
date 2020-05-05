package com.bp.yufeng.threadpool;

import java.util.stream.IntStream;

public class EveryTaskOneThread {

    public static void main(String[] args) {

        IntStream.range(0, 100000).forEach(i -> {
            new Thread(() -> {
                System.out.println("executing task");
            }).start();
        });
    }
}
