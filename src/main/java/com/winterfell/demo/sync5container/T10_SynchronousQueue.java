package com.winterfell.demo.sync5container;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue是特殊的transferQueue
 * 容量为0
 * 生产者生产(put)的任何内容，消费者必须立刻消费
 *
 * @author winterfell
 */
public class T10_SynchronousQueue {

    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<String> strs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                for(;;){
                    System.out.println(strs.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // put 等待消费者消费 里面用的transfer
        strs.put("aaa");

        strs.put("bbb");

        System.out.println(strs.size());
    }


}
