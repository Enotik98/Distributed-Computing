package modul1;

import java.util.concurrent.Semaphore;

public class Parking {
    public final boolean[] parkingPlaces = new boolean[3];
    Semaphore semaphore = new Semaphore(parkingPlaces.length, true);

}
