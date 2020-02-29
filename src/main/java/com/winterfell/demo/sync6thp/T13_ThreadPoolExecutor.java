package com.winterfell.demo.sync6thp;

/**
 * @author winterfell
 */
public class T13_ThreadPoolExecutor {

    public static void main(String[] args) {

    }

}
// ThreadPoolExecutor 七大参数
/*
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)

corePoolSize：线程池中的常驻核心线程数

maximumPoolSize:线程池能够容纳同时执行的最大线程数，此值必须大于等于1

keepAliveTime:多余的空闲线程的存活时间。当前线程池数量超过corePoolSize时，当空闲时间到达keepAliveTime值时，多余空闲线程会被销毁直到剩下corePoolSize个线程为止

unit：keepAliveTime的单位

workQueue：任务队列，被提交但尚未被执行的任务

threadFactory：表示生成线程池中工作线程的线程工厂，用于创建线程一般默用即可

handler：拒绝策略，表示当dui队列满了并且工作线程大于等于线程池的最大线程数（maximumPoolSize）
 */
