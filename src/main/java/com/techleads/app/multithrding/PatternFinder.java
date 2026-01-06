package com.techleads.app.multithrding;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatternFinder {

    public List<Integer> find(File file, String pattern)  {
        List<Integer> lineNumbers = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            int lineNo =1;
            String line;
            while((line = br.readLine())!=null){
                if(line.contains(pattern)){
                    lineNumbers.add(lineNo);
                    lineNo++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return lineNumbers;
    }
}
