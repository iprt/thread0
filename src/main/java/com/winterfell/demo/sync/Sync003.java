package com.winterfell.demo.sync;

/**
 * @author winterfell
 */
public class Sync003 {

    private int count = 10;

    // 这一段等价于 Sync003 里面的 m() 方法
    private synchronized void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }
}
