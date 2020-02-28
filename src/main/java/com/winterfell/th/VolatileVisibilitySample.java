package com.winterfell.th;

/**
 * @author winterfell
 */
public class VolatileVisibilitySample {

    private static boolean initFlag = false;

    public static void refresh() {
        System.out.println("refresh data ...");
        initFlag = true;
        System.out.println("refresh data success ...");
    }

    public static void loadData() {
        while (!initFlag) {
        }
        System.out.println("当前线程 " + Thread.currentThread().getName() + "嗅探到 initFlag状态的改变");
    }


    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            loadData();
        }, "threadA");

        threadA.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadB = new Thread(() -> {
            refresh();
        }, "threadB");

        threadB.start();
    }

}
