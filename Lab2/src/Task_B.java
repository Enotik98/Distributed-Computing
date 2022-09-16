import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
class Producer implements Runnable {
    int i = 1;
    @Override
    public void run() {
        while (true) {
            if (i == Task_B.SIZE) {
                break;
            }
            while (Task_B.queue1.size() >= 1) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Producer " + i);
            try {
                Task_B.queue1.put(i++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}

class ProducerConsumer implements Runnable {

    public ProducerConsumer() {
        new Thread(this, "ProducerConsumer").start();
    }

    @Override
    public void run() {
        while (true) {
            Integer item = null;
            try {
                item = Task_B.queue1.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Consumer took " + item);
            try {
                Task_B.queue2.put(item);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class Count implements Runnable {
    int count = 0;

    @Override
    public void run(){
        while(true){
            Integer item = null;
            try {
                item = Task_B.queue2.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Count " + item);        }
    }
}

public class Task_B {
    public static int SIZE = 100;
    public static BlockingQueue<Integer> queue1 = new LinkedBlockingQueue<>();
    public static BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Producer producer = new Producer();
        Thread thread1 = new Thread(producer);
        ProducerConsumer producerConsumer = new ProducerConsumer();
        Thread thread2 = new Thread(producerConsumer);
        Count count = new Count();
        Thread thread3 = new Thread(count);
        thread1.start();
        thread2.start();
        thread3.start();

    }
}
