package com.winterfell.demo.sync5container;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 使用 LinkedBlockingDeque 实现的生产消费
 * 无界队列
 *
 * @author winterfell
 */
public class T05_LinkedBlockingQueue {

    static BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    static Random r = new Random();

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    // put 如果满了 就会等待
                    queue.put("a" + i);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (; ; ) {
                    try {
                        //  put 如果满了 就会等待
                        System.out.println(Thread.currentThread().getName() + " take " + queue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "消费者" + i).start();
        }
    }

}
