package com.winterfell.demo.sync5container;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 有界阻塞队列
 *
 * @author winterfell
 */
public class T06_ArrayBlockingQueue {

    static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            queue.put("a" + r.nextInt(1000));
        }

        // 会报异常
//        queue.add("aaa");

        // 不会报异常 但是加不进去
        boolean offerSuccess = queue.offer("aaa");
        System.out.println("offer function " + offerSuccess);

        // 满了会阻塞
//        queue.put("aaa");
        System.out.println(queue);
    }

}
