package com.winterfell.demo.sync6thp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author winterfell
 */
public class T08_CachedPool {


    public static void main(String[] args) throws InterruptedException {

        // 默认超过60秒不使用 销毁
        ExecutorService service = Executors.newCachedThreadPool();

        System.out.println(service);

        for (int i = 0; i < 2; i++) {
            service.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }


        System.out.println(service);

        TimeUnit.SECONDS.sleep(3);

        System.out.println(service);

        TimeUnit.SECONDS.sleep(80);

        System.out.println(service);
    }

}
