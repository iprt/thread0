package com.winterfell.demo.sync3.test020;

import java.util.concurrent.TimeUnit;

/**
 * ReentrantLock 用于替代 synchronized
 * 本例用于m1锁定this，只有m1执行完毕的时候 m2才可以执行
 * 复习  synchronized 最原始的语义
 *
 * @author winterfell
 */
public class ReentrantLock1 {

    synchronized void m1() {

        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m1 " + i);
        }

    }

    synchronized void m2() {
        System.out.println("m2 ...");
    }


    public static void main(String[] args) {

        ReentrantLock1 r = new ReentrantLock1();

        new Thread(r::m1, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r::m2, "t2").start();

    }

}
