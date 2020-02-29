package com.winterfell.demo.sync5container;

import java.util.concurrent.LinkedTransferQueue;

/**
 * TransferQueue 一般是消费者先启动
 * 生产者生产的时候，不直接丢进队列
 * 先看有没有消费者要药费
 * 如果有，直接给消费者消费
 * 如果找不到消费者，会阻塞
 * <p>
 * 用transfer 提供了更加精确的控制
 * 生产者一旦产生东西，消费者立马消费
 * <p>
 * 更高的并发
 *
 * @author winterfell
 */
public class T08_TransferQueue {

    public static void main(String[] args) throws InterruptedException {

        LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        // 如果找不到消费者会阻塞
        strs.transfer("aaa");

        /*
        new Thread(() -> {
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
         */

    }
}
