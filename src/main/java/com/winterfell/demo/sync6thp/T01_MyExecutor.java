package com.winterfell.demo.sync6thp;

import java.util.concurrent.Executor;

/**
 * Executor定义一个接口，一种规范，比较普通
 *
 * @author winterfell
 */
public class T01_MyExecutor implements Executor {

    public static void main(String[] args) {

        new T01_MyExecutor().execute(() -> System.out.println("hello world"));

    }

    /**
     * 实现接口自己实现
     *
     * @param command
     */
    @Override
    public void execute(Runnable command) {
        // new Thread(command).start();
        command.run();
    }
}
