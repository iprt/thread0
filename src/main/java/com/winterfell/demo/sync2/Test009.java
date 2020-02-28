package com.winterfell.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁
 * <p>
 * 也就是说 synchronized 获得的锁是 可重入的
 *
 * @author winterfell
 */
public class Test009 {

    synchronized void m1() {
        System.out.println("m1 start ");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 可重入

        // 调用 m2() 的时候发现
        // 需要申请锁
        // 申请的锁 和 当前的锁是同一把锁
        // 仍然会得到当前的锁 所以说 synchronized 是可重入的锁
        this.m2();

    }

    synchronized void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }

}
