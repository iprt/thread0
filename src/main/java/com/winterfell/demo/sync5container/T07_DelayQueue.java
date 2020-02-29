package com.winterfell.demo.sync5container;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue 是无界的
 * <p>
 * 加进去的每个元素可以理解成任务
 * 这个任务消费者什么时候可以拿出来
 * 每个元素自己记载着我还有多长时间可以从队列中被消费者拿走
 * <p>
 * 这个队列是排好序的
 * 等待时间最长的排在最前面
 * <p>
 * 可以用来做定时任务
 *
 * @author winterfell
 */
public class T07_DelayQueue {

    static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    static class MyTask implements Delayed {

        String taskName;

        long runningTime;

        public MyTask(String taskName, long runningTime) {
            this.taskName = taskName;
            this.runningTime = runningTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 0;
            } else {
                return 0;
            }
        }

        @Override
        public String toString() {
            return taskName + " " + runningTime;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long now = System.currentTimeMillis();

        MyTask myTask1 = new MyTask("task1", now + 1000);
        MyTask myTask2 = new MyTask("task2", now + 1500);
        MyTask myTask3 = new MyTask("task3", now + 2500);
        MyTask myTask4 = new MyTask("task4", now + 3000);
        MyTask myTask5 = new MyTask("task5", now + 500);

        tasks.put(myTask1);
        tasks.put(myTask2);
        tasks.put(myTask3);
        tasks.put(myTask4);
        tasks.put(myTask5);


        System.out.println(tasks);

        for (int i = 0; i < 5; i++) {
            System.out.println(tasks.take());
        }
    }

}
