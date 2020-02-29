package com.winterfell.demo.sync6thp;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 工作窃取，本质是由 ForkJoin实现的
 *
 * @author winterfell
 */
public class T11_WorkStealingPool {

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();
        System.out.println(Runtime.getRuntime().availableProcessors());

        service.execute(new Work(1000)); // daemon

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            service.execute(new Work(2000));
        }

        // 由于产生的是守护（后台）线程，主线程不阻塞的话，看不到输出
        System.in.read();

    }

    static class Work implements Runnable {
        int time;

        public Work(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }


}
