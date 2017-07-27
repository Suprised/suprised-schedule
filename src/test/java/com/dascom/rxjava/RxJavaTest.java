package com.dascom.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTest {

    
    public static void main(String[] args) {
        
        /*String question = "中国古代统治时间最长的一个王朝是___;A、夏 B、商 C、周 D、秦";
        String regex = ".*[A-Z]{1}、{1}.*?[[A-Z]、]?.*";//.*?[[A-Z]&&、]{1}
        
        Pattern p = Pattern.compile(regex);
        Matcher matche = p.matcher(question);
        boolean matches = matche.matches();
        if (matches) {
            int start = matche.start();
            int end = matche.end();
            System.out.println(start + "," + end);
            
            System.out.println(matche.groupCount());
            
            while(matche.find()) {
                System.out.println(matche.group());
            }
        }*/
        
        
        // boolean matches = question.matches(regex);
        // System.out.println("是否匹配：" + matches);
        
        // 观察者
        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe():" + d.toString());
            }

            @Override
            public void onNext(String t) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("onNext():" + t);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError()");
            }

            @Override
            public void onComplete() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("onComplete();");
            }
        };
        
        //  被观察者
        Observable<String> observable = Observable.just("suprised", "happness", "小liuliu")
                                            .subscribeOn(Schedulers.computation());
        
        // 订阅: 观察者和被观察者关联
        observable.subscribe(observer);
        
        /*for (int i=0; i<100; i++) {
            System.out.println(i);
        }*/
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        
        while(true){
        }
    }
    
}
