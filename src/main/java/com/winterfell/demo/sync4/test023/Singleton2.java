package com.winterfell.demo.sync4.test023;

/**
 * 静态内部类 也能保证线程安全
 *
 * @author winterfell
 */
public class Singleton2 {

    private static Singleton2 instance = null;

    private Singleton2() {
    }

    private static class Inner {
        static Singleton2 s = new Singleton2();
    }

    public static Singleton2 getInstance() {
        return Inner.s;
    }

}
