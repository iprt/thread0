package com.winterfell.demo.sync6thp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author winterfell
 */
public class T10_ScheduledPool {


    public static void main(String[] args) {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(4);

        es.scheduleAtFixedRate(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, 1, 500, TimeUnit.MILLISECONDS);


    }
}
