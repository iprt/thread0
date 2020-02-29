package com.winterfell.demo.sync6thp;

import java.util.concurrent.*;

/**
 * @author winterfell
 */
public class T03_Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<String> callable = ()->{
            return "hello world";
        };

        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<String> stringFuture = service.submit(callable);

        System.out.println(stringFuture.get());

        service.shutdown();

    }

}
