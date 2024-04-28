package org.example.base.thread.CreateThreadDemo;

public class ExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("1........");
    }

    public static void main(String[] args) {
        new ExtendsThread().start();
    }
}
