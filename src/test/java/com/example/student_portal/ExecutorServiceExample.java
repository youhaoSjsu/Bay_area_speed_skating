package com.example.student_portal;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {

    public static class A implements Runnable {

        @Override
        public void run() {
            getMethod();
        }
        public void getMethod()
        {
            System.out.println("A.getmethod");
        }
    }
    public static class B implements  Runnable{
        @Override
        public void run()
        {
            getMethod();
        }
        public void getMethod(){
            System.out.println("B.getMethod");
        }
    }

     public static void runSameTime(){
         ExecutorService es = Executors.newFixedThreadPool(2);
         A a = new A();
         B b = new B();

         es.submit(b);
         es.submit(a);
         es.shutdown();
         try{
             es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
         } catch (InterruptedException e) {
             throw new RuntimeException(e);
         }

     }
    public static void main(String[] args) {
        runSameTime();
    }
}
