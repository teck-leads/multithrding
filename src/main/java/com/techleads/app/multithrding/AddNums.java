package com.techleads.app.multithrding;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddNums {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(10, 20, 30, 40, 50, 60, 70, 80, 90);

        long start = System.currentTimeMillis();
//        int sum = add(list);
        //Time taken in millis: 3 for serial processing
//        System.out.println("Sum: " + sum);


        List<List<Integer>> parts = partition(list, 3);

        ExecutorService es = Executors.newFixedThreadPool(3);
        Map<List<Integer>, Future<Integer>> futureMap = new HashMap<>();
        for (List<Integer> part : parts) {
            Future<Integer> future = es.submit(() -> add(part));
            futureMap.put(part, future);
        }

        int finalSum = 0;
        for(Map.Entry<List<Integer>, Future<Integer>> entry : futureMap.entrySet()){
            try {
                Integer sum = entry.getValue().get(); // waits if needed
                System.out.println("Sum of "+ entry.getKey() + " = " + sum);
                finalSum += sum;
            } catch (Exception e) {
                System.err.println("Failed for part: " + entry.getKey());
                e.printStackTrace();
            }
        }
        System.out.println("Final Sum: " + finalSum);
        long end = System.currentTimeMillis();
        System.out.println("Time taken in millis: " + (end - start));
    }

    private static int add(List<Integer> list) {
        int sum = 0;
        for (Integer integer : list) {
            sum = sum + integer;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    public static <T> List<List<T>> partition(List<T> list, int parts) {
        List<List<T>> result = new ArrayList<>();
        int totalSize = list.size();
        int chunkSize = (int) Math.ceil((double) totalSize / parts);

        for (int i = 0; i < totalSize; i += chunkSize) {
            result.add(list.subList(i, Math.min(i + chunkSize, totalSize)));
        }
        return result;
    }

}
