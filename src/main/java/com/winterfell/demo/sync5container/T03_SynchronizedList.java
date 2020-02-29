package com.winterfell.demo.sync5container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author winterfell
 */
public class T03_SynchronizedList {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        List<String> syncList = Collections.synchronizedList(list);

        System.out.println(syncList.getClass());

    }

}
