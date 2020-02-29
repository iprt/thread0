package com.winterfell.demo.sync3.test022;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * <p>
 * ThreadLocal的变量只和当前线程有关
 * <p>
 * 运行下面的程序理解
 *
 * @author winterfell
 */
public class ThreadLocal2 {

    //   volatile static Person person = new Person();
    static ThreadLocal<Person> person = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("person's name :" + person.get().name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            person.set(new Person());
        }).start();
    }

    static class Person {
        String name = "zhangsan";
    }

}

// ThreadLocal 可能会导致内存泄漏
