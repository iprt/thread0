package com.winterfell.demo.sync5container;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 并发容器中用的很多的东西
 *
 * @author winterfell
 */
public class T04_ConcurrentQueue {

    public static void main(String[] args) {

        Queue<String> strs = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < 10; i++) {
            strs.offer("hello" + i); // add
        }

        System.out.println(strs);

        System.out.println(strs.size());

        System.out.println(strs.poll());
        System.out.println(strs.size());

        System.out.println(strs.peek());
        System.out.println(strs.size());

        Deque<String> deque = new ConcurrentLinkedDeque<>();



    }

}
