package com.winterfell.demo.sync;

/**
 * synchronized 如果用在静态方法上，相当与锁定当前对象的 Class对象
 *
 * @author winterfell
 */
public class Sync004 {

    private static int count = 10;

    public synchronized static void m() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm() {
        synchronized (Sync004.class) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    // m() 和 mm() 方法等价

}
