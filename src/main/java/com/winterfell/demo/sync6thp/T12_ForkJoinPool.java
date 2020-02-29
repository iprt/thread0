package com.winterfell.demo.sync6thp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author winterfell
 */
public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 5000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println(Arrays.stream(nums).sum());
    }

    static class AddAction extends RecursiveAction {
        int start, end;

        public AddAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                System.out.println("from:" + start + " end:" + end);
            } else {
                int middle = start + (end - start) / 2;
                AddAction left = new AddAction(start, middle);
                AddAction right = new AddAction(middle, end);
                left.fork();
                right.fork();
            }
        }
    }

    // 归并的思想
    static class AddTask extends RecursiveTask<Long> {
        int start, end;

        public AddTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
//                System.out.println("from:" + start + " end:" + end);
                return sum;
            } else {
                int middle = start + (end - start) / 2;
                AddTask left = new AddTask(start, middle);
                AddTask right = new AddTask(middle, end);
                left.fork();
                right.fork();
                return left.compute() + right.compute();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();

        AddAction addAction = new AddAction(0, nums.length);
        fjp.execute(addAction);

        AddTask addTask = new AddTask(0, nums.length);
        fjp.execute(addTask);
        long result = addTask.join();
        System.out.println(result);

        System.in.read();
    }

}
