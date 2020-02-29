package com.winterfell.demo.sync5container;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author winterfell
 */
public class T01_ConcurrentMap {

    public static void main(String[] args) {

        // Map<String, String> map = new ConcurrentHashMap<>();
        // Map<String, String> map = new ConcurrentSkipListMap<>(); // 高并发并且排序的

        Map<String, String> map = new Hashtable<>();

        Random r = new Random();
        Thread[] ths = new Thread[100];
        CountDownLatch countDownLatch = new CountDownLatch(ths.length);

        long start = System.currentTimeMillis();

        for (int i = 0; i < ths.length; i++) {

            ths[i] = new Thread(() -> {

                for (int j = 0; j < 1000; j++) {
                    map.put("a" + r.nextInt(100000), "a" + r.nextInt(100000));
                }
                countDownLatch.countDown();
            });
        }

        Arrays.asList(ths).forEach(o -> o.start());

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

    // ConcurrentHashMap 1.8抛弃了分段锁 使用 CAS

}
