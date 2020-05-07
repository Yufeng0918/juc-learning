package com.bp.yufeng.threadlocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalNPE {

    ThreadLocal<Long> longThreadLocal = new ThreadLocal<>();

    public void set() {
        longThreadLocal.set(Thread.currentThread().getId());
    }

    public Long get() {
        return longThreadLocal.get();
    }

    public static void main(String[] args) {

        ThreadLocalNPE threadLocalNPE = new ThreadLocalNPE();
        System.out.println(threadLocalNPE.get());

        Thread thread1 = new Thread(() -> {
            threadLocalNPE.set();
            System.out.println(threadLocalNPE.get());
        });
        thread1.start();

        Map<String, String> map = new HashMap<>();
        map.put(null, "ABC");
        System.out.println(map.get(null));
    }
}


