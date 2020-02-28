package com.winterfell.demo.sync;

/**
 * synchronized 关键字
 * 对 某个对象 加锁
 * <p>
 * synchronized 是互斥锁
 *
 * @author winterfell
 */
public class Sync001 {

    private int count = 0;

    // 这段程序表示 这个锁是自己 new 出来的
    private Object o = new Object();

    public void m() {

        synchronized (o) { // 任何线程要执行一下的代码，必须拿到 o 的锁

            // o 是堆内存对象的引用
            // 真正申请锁的时候 是从堆内存对象里面申请的
            // 锁的信息都是保存在堆内存里面的

            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }

    }

}
