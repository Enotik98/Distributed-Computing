package modul1;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Car implements Runnable{
    private int carName;
    private Semaphore semaphore;
    private Parking parking;

    public Car(Parking parking, int name) {
        this.parking = parking;
        this.semaphore = parking.semaphore;
        this.carName = name;
    }

    @Override
    public void run() {
        System.out.println("Car " + carName +" looking for a place");
        try {
            int parkingNumber = 0;
            if (semaphore.tryAcquire(4000, TimeUnit.MILLISECONDS)) {
                for (int i = 0; i < parking.parkingPlaces.length; i++) {
                    if (!parking.parkingPlaces[i]) {
                        parking.parkingPlaces[i] = true;
                        parkingNumber = i;
                        System.out.println("Car " + carName + " is parking place " + parkingNumber);
                        break;
                    }
                }

                Thread.sleep(3000);

                semaphore.release();
                parking.parkingPlaces[parkingNumber] = false;
                System.out.println("Car " + carName + " leaf the place " + parkingNumber);
            } else {
                System.out.println("Time is up the car " + carName + " has left");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

