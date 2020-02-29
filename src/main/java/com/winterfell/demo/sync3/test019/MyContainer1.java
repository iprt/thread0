package com.winterfell.demo.sync3.test019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题
 * 实现一个容器，提供两个方法， add size
 * 写两个线程，线程1 添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 分析下面这个程序是否能完成功能
 *
 * @author winterfell
 */
public class MyContainer1 {

    /*
     * 程序问题分析
     * 1. lists 不具有可见性 所以 线程 2 可能获取不到 size的值
     * 2. 为了保证可见性 加一个 volatile 是否还有问题呢
     * 问题
     *
     * 1.
     * while(true) 浪费 cpu资源
     * 2.
     * 而且不保证原子性 就
     * 是 size=5 的时候 线程2 开始停止 size 变成 6了
     *
     * 通知的机制
     * 线程1 size 等于 5 的时候通知 线程2
     *
     */


    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {

        MyContainer1 myContainer1 = new MyContainer1();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                myContainer1.add(new Object());
                System.out.println("add " + i);
            }

        }, "t1").start();


        new Thread(() -> {
            while (true) {
                if (myContainer1.size() == 5) {
                    break;
                }
            }
            System.out.println("t2结束");
        }, "t2").start();

    }


}
