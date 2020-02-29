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
 * <p>
 * 必须要 必须要 必须要 手动释放锁
 * 使用 synchronized 锁定的时候 如果抛出异常 锁会自动释放 但是 ReentrantLock 不会 !!!
 * <p>
 * 在使用 ReentrantLock 可以进行 尝试锁定 "tryLock" 这样无法锁定，或者在指定的时间内无法锁定，线程可以决定是否继续等待
 * <p>
 * ReentrantLock还可以调用 lockInterruptibly 方法，可以对线程的 interrupted 方法做出响应
 * 在一个线程等待锁的过程中，可以被打断
 *
 * @author winterfell
 */
public class ReentrantLock4 {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end ...");
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            } finally {
                lock.unlock();
            }
        }, "t1");

        t1.start();

        Thread t2 = new Thread(() -> {

            try {
                lock.lock();
                // lock.lockInterruptibly(); // 可以对interrupt() 方法做出响应
                System.out.println("t2 start ");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            } finally {
                lock.unlock();
            }

        }, "t2");
        t2.start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.interrupt(); // 打断线程2的等待
        // 如果使用    lock.lock(); 是无法打断线程的

    }
}
