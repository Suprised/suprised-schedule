package com.suprised.guava;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * guava 缓存
 */
public class GuavaCacheTest {

    private static AtomicInteger count = new AtomicInteger(0);
    
    public static void main(String[] args) throws Exception {
        final LoadingCache<String, String> cache = getCacheLoder();
        for (int i=0; i<200; i++) {
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    try {
                        viewCache(cache);
                        viewCache(cache);
                        Thread.sleep(3000);
                        System.out.println("暂停三秒后启动");
                        viewCache(cache);
                        viewCache(cache);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        
        while(true){
        }
    }
    
    
    public static final void viewCache(LoadingCache<String, String> cache) throws Exception {
        System.out.println(cache.getUnchecked("cacheKey"));
        /*System.out.println(cache.getUnchecked("cacheKey"));
        System.out.println(cache.getUnchecked("cacheKey"));*/
    }
    
    
    public static final Cache<String, Object> getCache() {
        Cache<String, Object> cache = CacheBuilder.newBuilder().initialCapacity(1000).expireAfterAccess(60, TimeUnit.SECONDS)
            .build();
        return cache;
    }
    
    public static final LoadingCache<String, String> getCacheLoder() {
        // 缓存3秒
        // expireAfterAccess： 缓存项在给定时间内没有被读/写访问，则回收
        // expireAfterWrite ： 缓存项在给定时间内没有被写访问（创建或覆盖），则回收
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().expireAfterWrite(3, TimeUnit.SECONDS).initialCapacity(1000).build(new CacheLoader<String, String>(){

            @Override
            public String load(String cacheKey) throws Exception {
                int i = count.addAndGet(1);
                System.out.println(i);
                return "test" + i;
            }
        });
        return cache;
    }
    
    public void testDemo() throws Exception {
        // 设置缓存最大个数为100，缓存过期时间为5秒
        LoadingCache<Integer, UserData> cache = CacheBuilder.newBuilder().maximumSize(100)
                .expireAfterAccess(5, TimeUnit.SECONDS).build(new CacheLoader<Integer, UserData>() {
                    @Override
                    public UserData load(Integer key) throws Exception {
                        return UserData.get(key);
                    }
                });
        
        // 第一次加载会查数据库
        UserData user = cache.get(1);
        System.out.println(user);
        
        // 第二次加载时直接从缓存里取
        UserData user2 = cache.get(1);
        System.out.println(user2);

        // 第三次加载时，因为缓存已经过期所以会查数据库
        //Threads.sleep(10, TimeUnit.SECONDS);
        UserData user3 = cache.get(1);
        System.out.println(user3);
    }
    
    public void testAbc() {
        String[] b = {};
        assertThat(b.length, is(4));
    }
}
