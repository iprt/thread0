package com.winterfell.demo.sync2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 解决同样的问题保证原子性的问题 可以使用 AtomXXX
 * <p>
 * AtomXXX 本身方法是原子性的 但是不能保证多少方法连续调用是原子性的
 * （原子性的方法 + 原子性的方法 ） 不构成 一套完整的原子性的方法 （见缝插针）
 *
 * @author winterfell
 */
public class Test015 {

    AtomicInteger integer = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++) {
            integer.incrementAndGet(); // (count ++) 是不具备原子性的 所以之前volatile的方式是有问题的
        }
    }

    public static void main(String[] args) {

        Test015 test = new Test015();

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

        System.out.println(test.integer.get());

    }


}
