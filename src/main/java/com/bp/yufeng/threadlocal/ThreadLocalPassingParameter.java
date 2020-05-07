package com.bp.yufeng.threadlocal;

import lombok.AllArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class ThreadLocalPassingParameter {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Service1 service1 = new Service1();
        IntStream.range(0, 1000).forEach(i -> {
            service.submit(() -> service1.process());
        });

        service.shutdown();
    }
}


class Service1 {

    public void process() {
        User user = new User("SG");
        UserContextHolder.holder.set(user);
        new Service2().process();
    }
}

class Service2 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("service2 handle: " + user);
        new Service3().process();
    }
}


class Service3 {

    public void process() {
        User user = UserContextHolder.holder.get();
        System.out.println("service3 handle: " + user);
    }
}


class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

@AllArgsConstructor
class User {
    private String name;
}