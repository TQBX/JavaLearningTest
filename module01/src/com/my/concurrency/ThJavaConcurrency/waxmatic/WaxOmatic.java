package com.my.concurrency.ThJavaConcurrency.waxmatic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @auther Summerday
 */
public class WaxOmatic {
    public static void main(String[] args) throws Exception{
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();

        exec.execute(new WaxOff(car));

        exec.execute(new WaxOn(car));

        TimeUnit.SECONDS.sleep(5);

        exec.shutdownNow();

    }
}


class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (!waxOn)
            wait();
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn)
            wait();
    }


}

class WaxOn implements Runnable {

    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println("Wax On");
                TimeUnit.MILLISECONDS.sleep(100);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending wax on task");
    }
}


class WaxOff implements Runnable {
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {

            while (!Thread.interrupted()) {
                car.waitForWaxing();
                System.out.println("wax off");
                TimeUnit.MILLISECONDS.sleep(100);
                car.buffed();

            }
        }catch (InterruptedException e){
            System.out.println("exiting via interrupt");
        }
        System.out.println("ending wax off task");

    }



}