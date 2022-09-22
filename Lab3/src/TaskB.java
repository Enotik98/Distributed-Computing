import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaskB {

    private final AtomicBoolean isFinish ;
    private final AtomicBoolean isBarberBusy;
    private final Object barberControl;
    private final Object queueControl;
    private final Queue<Client> queue;

    TaskB(){
        isFinish = new AtomicBoolean(false);
        isBarberBusy = new AtomicBoolean(false);
        barberControl = new Object();
        queueControl = new Object();
        queue = new ConcurrentLinkedQueue<>();
    }
    private class Barber implements Runnable{
        String name;

        public Barber(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            synchronized (barberControl){
                while (!isFinish.get() || !queue.isEmpty()){
                    if (queue.isEmpty()){
                        try {
                            System.out.println(name + " falling asleep");
                            isBarberBusy.set(false);
                            barberControl.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }else {
                        Client client = queue.remove();
                        System.out.println(name + " getting next " + client.name);
                        client.startHaircut();
                    }
                }
            }
        }
    }

    public class Client extends Thread{
        String name;

        public Client(String name) {
            this.name = name;
        }

        private boolean isReady;

        Client(){
            isReady = false;
        }

        @Override
        public void run() {
            synchronized (queueControl){
                while (!isReady){
                    if (!isBarberBusy.get()){
                        synchronized (barberControl){
                            barberControl.notify();
                            isBarberBusy.set(true);
                            System.out.println(name + " awaking barber ");
                        }
                    }else {
                        try {
                            System.out.println(name + " falling asleep");
                            queueControl.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        public void startHaircut(){
            System.out.println(name + " shaving started");
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e ){
                e.printStackTrace();
            }
        }
    }
    public void startDay(){
        Thread barber = new Thread(new Barber("Barber"));
        barber.start();
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
        for (int i = 1; i <= 3; i++){
            Client client = new Client("Client " + i);
            queue.add(client);
            System.out.println(client.name + " comes to barbershop");
            client.start();
        }
        try {
            Thread.sleep(7000);
        }catch (InterruptedException e ){
            e.printStackTrace();
        }
        Client client = new Client("Client " + 4);
        queue.add(client);
        System.out.println(client.name + " comes to barbershop");
        client.start();
    }
    public static void main(String[] args) {
        TaskB barberShop = new TaskB();
        barberShop.startDay();
    }
}
