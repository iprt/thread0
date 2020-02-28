package com.winterfell.demo.sync2;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile volatile
 * 对比上一个程序 可以使用 synchronized 解决这个问题
 * <p>
 * synchronized 既保证可见性 也保证原子性
 * volatile 保证了可见性 不保证原子性
 *
 * @author winterfell
 */
public class Test014 {

    /*volatile*/ int count = 0;

    synchronized void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {

        Test014 test = new Test014();

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(test::m, "thread-" + i));
        }

        threads.forEach(Thread::start);

        threads.forEach(o -> {
            try {
                // 让 主线程 等待 子线程 结束之后才能继续运行
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(test.count);

    }


}
