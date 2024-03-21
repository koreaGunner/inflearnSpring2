package hello.core.hashMapTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HashMapThreadTest {
    private static final int maxThreads = 10;
    private static final HashMap<String, Integer> hashMAP = new HashMap<>();
    private static final Hashtable<String, Integer> hashTable = new Hashtable<>();
    private static final ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    private static final Map<String, Integer> synHashMap = Collections.synchronizedMap(new HashMap<>());

    @Test
    @RepeatedTest(1000)
    void concurrencyTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);

        for (int i = 0; i < maxThreads; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    String key = String.valueOf(j);

                    hashMAP.put(key, j);
                    hashTable.put(key, j);
                    concurrentHashMap.put(key, j);
                    synHashMap.put(key, j);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("hashMap size : " + hashMAP.size());
        Assertions.assertThat(hashMAP.size()).isNotEqualTo(10000);
        System.out.println("hashTable size : " + hashTable.size());
        Assertions.assertThat(hashTable.size()).isEqualTo(10000);
        System.out.println("concurrentHashMap size : " + concurrentHashMap.size());
        Assertions.assertThat(concurrentHashMap.size()).isEqualTo(10000);
        System.out.println("synHashMap size : " + synHashMap.size());
        Assertions.assertThat(synHashMap.size()).isEqualTo(10000);
    }
}
