package com.winterfell.demo.sync4.test024;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有n张火车票 每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * <p>
 * 分析下面程序可能会出什么问题
 * 重复销售？ 超量销售？
 *
 * @author winterfell
 */
public class TicketSeller2 {

    // 对比1用的是 List
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号" + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {

                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("销售了 - " + tickets.remove(0));
                }
            }).start();
        }

    }

    // 虽然 remove是原子性的
    // size也是原子性的
    // 回到之前，(原子性 + 原子性) 不保证 原子性
}
