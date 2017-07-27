package com.suprised.schedule;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    
    private static Random random = new Random();

    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>(1000);
        ThreadPoolExecutor pools = new ThreadPoolExecutor(10, 100, 1000, TimeUnit.SECONDS, queue);
        for (int i=0; i<10000; i++) {
            // 无返回值
            //pools.execute(new RunnableImpl(i, random.nextInt(100)));
            //System.out.println("taskCount:" + pools.getTaskCount() + ", activeCount: " + pools.getActiveCount());
            // 带返回值的线程
            Future<Integer> retValue = pools.submit(new CallableImpl(i, random.nextInt(800)));
            System.out.println(retValue.get());
            // 带超时的返回结果
            // System.out.println(retValue.get(700, TimeUnit.MILLISECONDS));
        }
    }
    
}

class RunnableImpl implements Runnable {

    private int index = 0;
    private int sleepTime = 100;
    
    public RunnableImpl(int index, int sleepTime) {
        this.index = index;
        this.sleepTime = sleepTime;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(sleepTime);
            System.out.println("index:" + index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 带返回值的线程
 */
class CallableImpl implements Callable<Integer> {

    private int retValue = 0;
    private int sleepTime = 100;
    
    public CallableImpl(int retValue, int sleepTime) {
        this.retValue = retValue;
        this.sleepTime = sleepTime;
    }
    
    @Override
    public Integer call() throws Exception {
        Thread.sleep(sleepTime);
        System.out.println("sleepTime:" + sleepTime);
        return retValue;
    }
    
}
