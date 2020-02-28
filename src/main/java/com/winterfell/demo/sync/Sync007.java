package com.winterfell.demo.sync;

/**
 * 同步方法和非同步方法是否可以同时调用
 *
 * @author winterfell
 */
public class Sync007 {


    public synchronized void m1() {

        System.out.println(Thread.currentThread().getName() + " m1 start ...");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end ...");
    }


    public void m2() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " m2 ...");

    }

    public static void main(String[] args) {
        Sync007 test = new Sync007();

        new Thread(() -> test.m1(), "t1").start();

        // 问题： 程序运行时候，对象的锁被获取了，m2方法能不能运行
        new Thread(() -> test.m2(), "t2").start();

    }


    /*
     * 在同步方法执行的过程中
     *
     * 非同步方法是可以执行的 （因为非同步的方法不需要申请锁）
     *
     * 关键要看锁了什么东西
     *
     */

}
