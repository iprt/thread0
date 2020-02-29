package com.winterfell.demo.sync4.test024;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author winterfell
 */
public class TicketSeller4 {

    // cas
    static Queue<String> tickets = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("票编号 " + i);
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String s = tickets.poll();
                    if (s == null) {
                        break;
                    } else {
                        System.out.println("销售了 " + s);
                    }
                }

            }).start();
        }

    }


    // 为什么这种方式会成功
    // 之前的情况是因为 判断之后作了修改
    // 这次是 修改之后 再判断

}
