package com.winterfell.demo.sync3.test020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 用于替代 synchronized
 * 本例用于m1锁定this，只有m1执行完毕的时候 m2才可以执行
 * 复习  synchronized 最原始的语义
 * <p>
 * 使用 ReentrantLock 完成同样的语义
 *
 * 必须要 必须要 必须要 手动释放锁
 * 使用 synchronized 锁定的时候 如果抛出异常 锁会自动释放 但是 ReentrantLock 不会 !!!
 *
 * @author winterfell
 */
public class ReentrantLock2 {

    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println("m1 " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void m2() {
        lock.lock();
        System.out.println("m2 ...");
        lock.unlock();
    }

    public static void main(String[] args) {

        ReentrantLock2 r = new ReentrantLock2();

        new Thread(r::m1, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r::m2, "t2").start();
    }

}
