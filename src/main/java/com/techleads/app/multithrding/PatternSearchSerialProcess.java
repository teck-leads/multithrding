package com.techleads.app.multithrding;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PatternSearchSerialProcess {
    public static void main(String[] args) {
        String pattern ="public";
        File dir =new File("C:\\Users\\anupo\\Downloads\\multithrding\\src\\main\\java\\com\\techleads\\app\\multithrding\\myFiles");
        File[] files = dir.listFiles();
       PatternFinder finder = new PatternFinder();
       long start = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        Map<String, Future<List<Integer>>> futureMap = new HashMap<>();

        for (File file : files) {
            Future<List<Integer>> future =
                    es.submit(() -> new PatternFinder().find(file, pattern));
            futureMap.put(file.getName(), future);
        }

        for (Map.Entry<String, Future<List<Integer>>> entry : futureMap.entrySet()) {
            try {
                List<Integer> lineNumbers = entry.getValue().get(); // waits if needed
                if (!lineNumbers.isEmpty()) {
                    System.out.println(
                            "Pattern found in file: " + entry.getKey() +
                                    " at lines: " + lineNumbers
                    );
                }
            } catch (Exception e) {
                System.err.println("Failed for file: " + entry.getKey());
                e.printStackTrace();
            }
        }

        es.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Time taken in millis: " + (end - start));

    }
}
