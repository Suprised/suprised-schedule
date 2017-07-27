package com.suprised.schedule;

public class ThreadTest {

    public static void main(String[] args) throws Exception {
        ThreadTest test = new ThreadTest();
        ThreadDemo threadDemo = test.new ThreadDemo();
        threadDemo.start();
        
        Thread.sleep(20000);
    }
    
    
    class ThreadDemo extends Thread {
        
        public ThreadDemo() {
            setDaemon(true);
        }
        
        @Override
        public void run() {
            while(true) {
                System.out.println("守护线程");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
