package com.urise.webapp;

public class MainDeadLock {

    public static void main(String[] args) {
        MainDeadLock deadLock1 = new MainDeadLock();
        MainDeadLock deadLock2 = new MainDeadLock();

        deadLock(deadLock1, deadLock2);
        deadLock(deadLock2, deadLock1);
    }

    public static void deadLock(MainDeadLock dl1, MainDeadLock dl2) {
        new Thread(() -> {
            synchronized (dl1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (dl2) {
                }
            }
        }).start();
    }
}