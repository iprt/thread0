package com.winterfell.demo.sync2;

/**
 * 不要以字符串常量作为锁定对象
 * <p>
 * 在下面的例子中，m1和m2其实锁的是同一个对象
 * <p>
 * 不建议用字符串常量作为锁的对象
 *
 * @author winterfell
 */
public class Test018 {

    String s1 = "hello";

    String s2 = "hello";

    void m1() {
        synchronized (s1) {

        }
    }


    void m2() {
        synchronized (s2) {

        }
    }


    // 上面两个方法加的是同一把锁
}
