package com.winterfell.demo.sync3.test020;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 可以指定为公平锁
 *
 * @author winterfell
 */
public class ReentrantLock5 extends Thread {

    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁 " + i);
            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) {

        ReentrantLock5 r = new ReentrantLock5();

        Thread t1 = new Thread(r, "t1");
        Thread t2 = new Thread(r, "t2");

        t1.start();
        t2.start();


    }

}
