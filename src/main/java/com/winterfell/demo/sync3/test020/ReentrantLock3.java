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
 *
 * @author winterfell
 */
public class ReentrantLock3 {

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

    /**
     * 使用 tryLock 尝试锁定，不管锁定与否，方法都将继续执行
     * 可以根据 tryLock的返回值来判定是否锁定
     * 也可以指定 tryLock的时间，由于tryLock(time) 抛出异常，所以要注意unlock的处理 放在finally中
     */
    void m2() {

        /*
        boolean locked = lock.tryLock();
        System.out.println("m2 locked = " + locked);
        if(locked){
            // ...
            lock.unlock();
        }
         */

        boolean locked = false;
        try {
            // tryLock 拿到没拿到锁都继续执行
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 locked = " + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

        ReentrantLock3 r = new ReentrantLock3();

        new Thread(r::m1, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(r::m2, "t2").start();
    }

}
