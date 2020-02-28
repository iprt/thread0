package com.winterfell.demo.sync2;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile 不能保证原子性
 *
 * @author winterfell
 */
public class Test013 {

    volatile int count = 0;

    void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {

        Test013 test = new Test013();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(test::m, "thread-" + i));
        }

        threads.forEach(Thread::start);

        threads.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(test.count);

    }


}
