package com.winterfell.demo.sync6thp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author winterfell
 */
public class T07_ParallelComputing {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();

        getPrime(1, 200000);

        long end = System.currentTimeMillis();
        System.out.println(end - start);

        ExecutorService es = Executors.newFixedThreadPool(5);

        Future<List<Integer>> f1 = es.submit(new MyTask(1, 80000));
        Future<List<Integer>> f2 = es.submit(new MyTask(80001, 130000));
        Future<List<Integer>> f3 = es.submit(new MyTask(130001, 170000));
        Future<List<Integer>> f4 = es.submit(new MyTask(170001, 200000));

        start = System.currentTimeMillis();

        f1.get();
        f2.get();
        f3.get();
        f4.get();

        end = System.currentTimeMillis();
        System.out.println(end - start);

        es.shutdown();

    }


    static class MyTask implements Callable<List<Integer>> {

        int startPos, endPos;

        public MyTask(int startPos, int endPos) {
            this.startPos = startPos;
            this.endPos = endPos;
        }

        @Override
        public List<Integer> call() throws Exception {
            return getPrime(startPos, endPos);
        }


    }

    static boolean isPrime(int num) {
        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> getPrime(int startPos, int endPos) {
        List<Integer> result = new ArrayList<>();
        for (int i = startPos; i < endPos; i++) {
            if (isPrime(i)) result.add(i);
        }
        return result;
    }

}
