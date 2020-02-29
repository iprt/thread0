package com.winterfell.demo.sync6thp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author winterfell
 */
public class T05_ThreadPool {

    public static void main(String[] args) {

        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            });
        }

        System.out.println(service);

        // 关闭不代表线程池里面的线程中断
        service.shutdown();

        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("-----------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);

    }
// java.util.concurrent.ThreadPoolExecutor@4563e9ab[Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]

// [Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]

// [状态，池大小，活动线程个数，队列任务个数，完成任务个数]

}
