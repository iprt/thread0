package com.winterfell.demo.sync3.test019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题
 * 实现一个容器，提供两个方法， add size
 * 写两个线程，线程1 添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * list添加volatile之后 t2能够接收到通知，但是t2死循环会浪费CPU资源，如果不用死循环，该怎么做
 * <p>
 * 这里使用 wait 和 notify 做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是 运用这种方法 必须保证 t2 先执行 也就是让t2 首先监听才可以
 * <p>
 * 关注一下  下面的代码 注释部分
 * <p>
 * 1. t2线程 先启动 锁定 lock
 * 2. t2 lock.wait 释放 lock 对象的锁 程序进入wait状态
 * 3. t1 线程再启动 得到lock的锁
 * 4. t2 执行到size==5的时候  notify 一下 通知 t1 执行 但是 t1执行不了
 * 因为t1的代码再同步块里面 需要拿到锁 notify不释放锁
 * 5. t2 自己再 wait一下 释放锁 等待t1执行结束
 * 6. t1 执行结束 再 notify一下通知 t2 可以执行
 * 这时候 t1 的代码执行完了，锁自然而然释放了
 * 7. t2 的 wait 又获取到了锁 继续执行
 * <p>
 * 中间绕了几下
 * <p>
 *  t2 start has lock
 *  t2 wait  lose lock
 *  t1 notify ("notify t2 to go on ..") t2 doesn't have lock and cannot go on..., but t1 has lock
 *  t1 wait  t1 lose lock and wait , t2 get lock and go on
 *  t2 notify and finish
 *      notify : notify t1 to go on ,but t1 doesn't have lock
 *      finish : t2 finished and lock was free ,and then t1 can get lock
 *  t1 get lock and go on  ...
 *
 *
 * @author winterfell
 */
public class MyContainer2 {

    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {

        MyContainer2 container = new MyContainer2();

        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2启动");
                if (container.size() != 5) {
                    try {
                        // 释放锁 线程等待
                        // 只需要等待一次
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2结束");


                // 下面的代码 通知t1继续执行
                /*
                lock.notify();
                */
            }
        }, "t2").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {

            System.out.println("t1启动");

            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    container.add(new Object());
                    System.out.println("add " + i);

                    if (container.size() == 5) {
                        // 通知 wait的线程继续执行
                        // 为什么 上面的线程不会结束
                        // ！！！ notify不会释放锁
                        lock.notify();


                        // 下面的代码 就是再释放锁
                        /*
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        */
                    }

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();

    }


    // 使用对象的wait和notify必须要先锁定当前对象

    // wait和notify 是调用被锁定对象的方法


}

/* output
t2启动
t1启动
add 0
add 1
add 2
add 3
add 4
add 5
add 6
add 7
add 8
add 9
t2结束

为什么 add 5 这里 t1线程没有继续执行
因为 notify 不会释放锁

wait继续运行的时候还是需要得到锁的
不幸的是 notify并不会释放锁，所以扎心了

 */
