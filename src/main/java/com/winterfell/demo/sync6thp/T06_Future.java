package com.winterfell.demo.sync6thp;

import java.util.concurrent.*;

/**
 * @author winterfell
 */
public class T06_Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(1);
            return 1000;
        });

        new Thread(task).start();

        System.out.println(task.get()); // 阻塞

        System.out.println("-------------------------------");

        ExecutorService es = Executors.newFixedThreadPool(5);
        Future<Integer> f = es.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 9999;
        });

        System.out.println(f.isDone());

        System.out.println(f.get());

        System.out.println(f.isDone());

    }
}
