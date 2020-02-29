package com.winterfell.demo.sync5container;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写时复制容器
 * 多线程环境下，写的时候效率低，读的时候效率高
 * 适合写少读多
 * <p>
 * 实际中的例子：
 * 事件监听器
 *
 * @author winterfell
 */
public class T02_CopyOnWriteList {

    public static void main(String[] args) {

        List<String> list =
                // new ArrayList<>();
                // new Vector<>();
                new CopyOnWriteArrayList<>();


        Random r = new Random();
        Thread[] ths = new Thread[100];
        for (int i = 0; i < ths.length; i++) {
            ths[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    list.add("a" + r.nextInt(100000));
                }
            });
        }

        runAndComputeTime(ths);

        System.out.println("list size = " + list.size());

    }

    static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();
        Arrays.asList(ths).forEach(th -> th.start());
        Arrays.asList(ths).forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }


}

// CopyOnWriteArrayList 容器在写的时候加锁，
// 将原来的复制一份 后面再添加

// jvm? stop and copy

