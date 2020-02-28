package com.winterfell.demo.sync2;

import java.util.concurrent.TimeUnit;

/**
 * 可见性
 *
 * @author winterfell
 */
public class Test012 {


    // 对比一下有无 volatile的情况下，整个程序运行结果的区别
    /*volatile*/ boolean running = true;

    void m() {
        System.out.println("m start");
        while (running) {

        }

        System.out.println("m end");
    }

    public static void main(String[] args) {

        Test012 test = new Test012();

        new Thread(test::m, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test.running = false;
    }

}
