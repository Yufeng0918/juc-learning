package com.bp.yufeng.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LockUpgrading {

    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock(
            false);
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

    private static void readUpgrading() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get read lock");
            Thread.sleep(1000);
            System.out.println("lock upgrade");
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + "get write lock");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + "release lock");
            readLock.unlock();
        }
    }

    private static void writeDowngrading() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " get write lock");
            Thread.sleep(1000);
            readLock.lock();
            System.out.println("lock downgrade");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " release lock");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("downgrade");
        Thread thread1 = new Thread(() -> writeDowngrading(), "Thread1");
        thread1.start();
        thread1.join();


//        System.out.println("upgrade");
//        Thread thread2 = new Thread(() -> readUpgrading(), "Thread2");
//        thread2.start();
    }
}
