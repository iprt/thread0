package com.winterfell.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁
 * 也就是锁 synchronized 获得的锁是可重入的
 * 这里是继承的中有可能发生的情形
 *
 * @author winterfell
 */
public class Test010 {

    synchronized void m() {

        System.out.println("m start ..");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("m end ...");
    }

    public static void main(String[] args) {

    }

}

class Test010Child extends Test010 {

    /**
     * 重入锁的另外一种情形
     * <p>
     * 子类重入父类的锁
     */
    @Override
    synchronized void m() {
        System.out.println("mm start ...");
        super.m();
        System.out.println("mm end ...");
    }
}
