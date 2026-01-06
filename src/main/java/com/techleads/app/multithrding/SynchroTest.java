package com.techleads.app.multithrding;
class Incrementer {
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    // approch 1: synchronized method
    public synchronized void incrementSynchronized(){
        int y =getX();
        y++;
        try{
            Thread.sleep(1000); // Simulate some processing time
        }catch (Exception e){
            e.printStackTrace();
        }
        setX(y);

    }
    public void incrementSynchronizedBlock(){
        synchronized (this){
            int y =getX();
            y++;
            try{
                Thread.sleep(1000); // Simulate some processing time
            }catch (Exception e){
                e.printStackTrace();
            }
            setX(y);
        }


    }
}
class MyThread extends Thread{
    Incrementer obj;
    public MyThread(Incrementer obj){
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.incrementSynchronizedBlock();
    }
}

public class SynchroTest {
    public static void main(String[] args) {
        Incrementer incrementer = new Incrementer();
        incrementer.setX(1);

        MyThread t1 = new MyThread(incrementer);
        MyThread t2 = new MyThread(incrementer);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Final value of x: " + incrementer.getX());
    }
}
