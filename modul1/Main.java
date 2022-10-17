package modul1;

public class Main {

    public static void main(String[] args) {
        Parking parking = new Parking();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
                new Thread(new Car(parking, i)).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
