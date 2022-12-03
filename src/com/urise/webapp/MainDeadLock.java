package com.urise.webapp;

public class MainDeadLock {

    public static void main(String[] args) {
        MainDeadLock deadLock1 = new MainDeadLock();
        MainDeadLock deadLock2 = new MainDeadLock();
        Thread t1 = new Thread(() -> {
            synchronized (deadLock1) {
                Thread.yield();
                synchronized (deadLock2) {
                    System.out.println("Deadlock");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (deadLock2) {
                Thread.yield();
                synchronized (deadLock1) {
                    System.out.println("Deadlock");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
