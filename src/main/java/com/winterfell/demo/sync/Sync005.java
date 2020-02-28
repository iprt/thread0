package com.winterfell.demo.sync;

/**
 * 分析一下这个程序的输出
 *
 * @author winterfell
 */
public class Sync005 implements Runnable {

    private int count = 10;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void main(String[] args) {
        Sync005 test = new Sync005();
        for (int i = 0; i < 5; i++) {
            new Thread(test, "THREAD" + i).start();
        }
    }
}
