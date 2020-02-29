package com.winterfell.demo.sync3.test019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题
 * 实现一个容器，提供两个方法， add size
 * 写两个线程，线程1 添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 通知机制
 * 利用 CountDownLatch/CyclicBarrier/Semaphore
 * <p>
 * 类似计算机操作系统中的信号量
 *
 * @author winterfell
 */
public class MyContainer5 {

    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {

        MyContainer5 container = new MyContainer5();


        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("t2启动");
            if (container.size() != 5) {
                try {
                    countDownLatch.await();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("t2结束");
        }, "t2").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {

            System.out.println("t1启动");

            for (int i = 0; i < 10; i++) {
                container.add(new Object());
                System.out.println("add " + i);

                if (container.size() == 5) {
                    countDownLatch.countDown();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

    }
}