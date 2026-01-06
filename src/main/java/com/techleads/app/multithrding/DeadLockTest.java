package com.techleads.app.multithrding;
class Writer1 extends Thread{
    Object book;
    Object pen;
    public Writer1(Object book, Object pen){
        this.book = book;
        this.pen = pen;
    }
    @Override
    public void run() {
        synchronized (book){
            System.out.println("Writer1 has book");
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (pen){
                System.out.println("Writer1 has pen");
            }
        }
    }
}

class Writer2 extends Thread{
    Object book;
    Object pen;
    public Writer2(Object book, Object pen){
        this.book = book;
        this.pen = pen;
    }
    @Override
    public void run() {
        synchronized (book){

            System.out.println("Writer2 has book");
            try{
                Thread.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
            synchronized (pen){

                System.out.println("Writer2 has pen");
            }
        }
    }
}

public class DeadLockTest {
    public static void main(String[] args) {
        Object book = new Object();
        Object pen = new Object();

        Writer1 writer1 = new Writer1(book, pen);
        Writer2 writer2 = new Writer2(book, pen);

        writer1.start();
        writer2.start();
        System.out.println("Main thread ended");
    }
}
