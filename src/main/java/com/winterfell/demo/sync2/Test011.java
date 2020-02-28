package com.winterfell.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 程序在执行过程中，如果出现异常 ，默认情况下锁会被释放
 * <p>
 * 所以在处理并发的过程中 有异常要多加小心，不然可能会发生不一致的情况
 * <p>
 * 比如 在一个web app处理过程中，多个servlet线程共同访问同一个资源，这是如果异常处理不合适，
 * 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据
 * 因此要非常小心处理同步业务逻辑中的异常
 *
 * @author winterfell
 */
public class Test011 {

    int count = 0;

    synchronized void m() {

        System.out.println(Thread.currentThread().getName() + " start");

        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + " count = " + count);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (count == 5) {
                //  此处抛出异常，锁将被释放 如果不想释放 锁 try catch 处理
                int i = 1 / 0; // 此处抛出异常，锁将被释放，可以在这里进行catch 然后让循环继续
            }

        }

    }

    public static void main(String[] args) {

        Test011 test = new Test011();

        Runnable r = () -> {
            test.m();
        };


        new Thread(r, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // 如果m方法中异常被处理抛出了，t2线程永远拿不到锁
        new Thread(r, "t2").start();


    }


}
