package com.winterfell.demo.sync3.test021;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试题： 写一个固定容量同步容器，拥有put和get方法，以及getCount（） 方法
 * 能够支持 2个 生产者线程 以及 10 个消费者线程的阻塞调用
 * <p>
 * 使用 wait /notify /notifyAll来实现
 * <p>
 * 使用 Lock 和 Condition来实现
 *
 * @author winterfell
 */
public class MyContainer2<T> {
    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;

    private ReentrantLock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                producer.await();
            }

            lists.add(t);
            ++count;
            consumer.signalAll();  // 通知消费者线程进行消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.await();
            }
            t = lists.removeFirst();
            count--;

            producer.signalAll(); // 通知生产者进行生产
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {

        MyContainer2<String> myContainer2 = new MyContainer2<>();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    myContainer2.put(UUID.randomUUID().toString());
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true) {
                    System.out.println(myContainer2.get());
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