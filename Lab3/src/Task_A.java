import java.util.concurrent.Semaphore;

class PotHoney {
    int SIZE = 50;
    int currentSize = 0;
    static Semaphore semBee = new Semaphore(3);
    static Semaphore semWinnie = new Semaphore(0);

    void addHoney(String name){
        try {
            semBee.acquire();
        }catch (InterruptedException e){
            System.out.println("Error addHoney");
        }
        while(currentSize != SIZE){
            currentSize++;
            if (currentSize == SIZE){
                System.out.println(" Pot is full");
            }
            System.out.println( name + " size: " + currentSize);
        }
        semWinnie.release(3);
    }
    void eatHoney(){
        try {
            semWinnie.acquire();
        }catch (InterruptedException e){
            System.out.println(" Error eatHoney");
        }
        if (currentSize == SIZE){
            currentSize = 0;
            System.out.println(" Winnie eating");
            semBee.release(3);
        }
    }
}
class WinniePooh implements Runnable{
    PotHoney honey;
    String name;

    public WinniePooh(PotHoney honey, String name) {
        this.honey = honey;
        this.name = name;
    }
    @Override
    public void run() {

        for (int i = 0; i < 3 ; i++) {
            honey.eatHoney();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
class Bees implements Runnable{
    PotHoney honey;
    String name;

    public Bees(PotHoney honey, String name) {
        this.honey = honey;
        this.name = name;
    }
     @Override
    public void run() {
        for (int i = 0; i < 3 ; i++) {
            honey.addHoney(name);
        }
    }
}
public class Task_A {
    public static void main(String[] args) {
        PotHoney honey = new PotHoney();
        for (int i = 1; i <= 3; i++) {
            Bees bees = new Bees(honey, "Bee "+i);
            new Thread(bees).start();
        }
        WinniePooh winniePooh = new WinniePooh(honey, "WinniPooh");
        new Thread(winniePooh).start();
    }
}
