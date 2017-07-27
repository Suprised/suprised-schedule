package com.suprised.guava;

import java.util.SortedMap;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        TreeMap<Long, String> treeMap = new  TreeMap<Long, String>();
        for (long i=100; i>0; i--) {
            treeMap.put(i, "index_" + i);
        }
        
        /**
         * 获取key大于等于60的TreeMap
         */
        SortedMap<Long, String> sortedMap = treeMap.tailMap(60l);
        System.out.println(sortedMap.size() ==  41);
        
        long firstKey = sortedMap.firstKey();
        // 取最小key值
        System.out.println("firstKey:" + firstKey);
        System.out.println(sortedMap.get(firstKey));
        
        sortedMap.put(70l, "index_" + 10);
        System.out.println(sortedMap.get(sortedMap.firstKey()));
    }
    
}
