package com.winterfell.demo.sync3.test021;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 面试题： 写一个固定容量同步容器，拥有put和get方法，以及getCount（） 方法
 * 能够支持 2个 生产者线程 以及 10 个消费者线程的阻塞调用
 * <p>
 * 使用 wait /notify /notifyAll 来实现
 * <p>
 * synchronized wait notify notifyAll 这几个一起使用
 *
 * @author winterfell
 */
public class MyContainer1<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        while (lists.size() == MAX) {  // 想想为什么用while而不是if
            try {
                this.wait(); // 等待消费者消费
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;

        this.notifyAll(); // 通知消费者线程进行消费
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait(); // 等待生产者生产
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t = lists.removeFirst();
        count--;
        this.notifyAll(); // 通知生产者进行生产
        return t;
    }

    public synchronized int getCount() {
        return count;
    }

    public static void main(String[] args) {


        MyContainer1<String> myContainer1 = new MyContainer1<>();


        // 10个生产者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myContainer1.put(UUID.randomUUID().toString());
                }
            }, "thread-" + i).start();
        }

        // 2个消费者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println("consumer: " + Thread.currentThread().getName() + " " + myContainer1.get());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-100" + i).start();
        }

    }


}

// 为什么用 while 而不是if

// 如果用if
// 假设 生产者线程A 添加元素的时候 元素已经满了 执行wait
// 当 消费者线程 消费 元素的时候 通知生产者可以继续生产
//      生产者线程A 可以继续向下执行
//      但是有个问题，如果 【另外一个生产者线程】在 【前面一瞬间】 添加了一个值
//      这时候容器内是满的，再添加的话 ，就是有问题的
// 所以要用while 相当于 双重检查 的作用

//  多线线程的情况下要思考，多种线程同时操作的问题，不能只思考单一线程的问题
