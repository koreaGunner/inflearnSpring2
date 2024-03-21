package hello.core.hashMapTest;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

public class HashMapThreadTest2 {

    private static final int maxThreads = 10;
    @Test
    @RepeatedTest(5)
    void performanceTest() throws InterruptedException {
        Hashtable<Integer, Integer> hashtable = new Hashtable<>();
        Map<Integer, Integer> synHashMap = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        long hashtableTime = measure(hashtable);
        long synHashMapTime = measure(synHashMap);
        long concurrentHashMapTime = measure(concurrentHashMap);

        System.out.println("hashTableTime = " + hashtableTime);
        System.out.println("synHashMapTime = " + synHashMapTime);
        System.out.println("concurrentHashMapTime = " + concurrentHashMapTime);
        System.out.println();
    }

    private static long measure(Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreads);
        int count = 200000;

        long startTime = System.nanoTime();

        for (int i = 0; i < maxThreads; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < count; j++) {
                    int value = ThreadLocalRandom.current().nextInt();
                    map.put(value, value);
                    map.get(value);
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / count * maxThreads;
    }
}
