package taskA;

import java.security.SecureRandom;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

class Let {
    private final int partAtStart;
    private int partAwait;

    public Let(int part) {
        this.partAtStart = part;
        this.partAwait = part;
    }
    public synchronized void await() throws InterruptedException {
        partAwait --;
        if (partAwait > 0 ) this.wait();
        partAwait = partAtStart;
        notifyAll();
    }
}
class Rookie implements Runnable{
    private static final AtomicBoolean finished = new AtomicBoolean(false);

    private final int [] recruits;
    private final Let let;
//    private final CyclicBarrier barrier;
    private final int partIndex;
    private final int leftIndex;
    private final int rightIndex;

    public Rookie(int[] recruits, Let let, int partIndex, int leftIndex, int rightIndex) {
        this.recruits = recruits;
        this.let = let;
        this.partIndex = partIndex;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    //    public Rookie(int[] recruits, Let let, int partIndex, int leftIndex, int rightIndex) {
//        this.recruits = recruits;
//        this.let = let;
////        this.barrier = barrier;
//        this.partIndex = partIndex;
//        this.leftIndex = leftIndex;
//        this.rightIndex = rightIndex;
//    }

    @Override
    public void run() {
        while (!finished.get()){
            for (int i = leftIndex; i < rightIndex-1; i++) {
                if (recruits[i] == 1 && recruits[i+1] == 0){
                    recruits[i+1] = 1;
                    if (i == leftIndex){
                        recruits[i] = 0;
                    }else {
                        while (recruits[i - 1] != 0 && i != leftIndex + 1) {
                            i--;
                        }
                        recruits[i] = 0;
                        i--;
                    }
                }
            }

            System.out.println();
            for (int j = leftIndex; j < rightIndex; j++) {
                System.out.print(j + " ");
            }
            System.out.println();
            for (int j = leftIndex; j < rightIndex; j++) {
                System.out.print(recruits[j] + " ");
            }
//            try {
//                barrier.await();
//            }catch (InterruptedException | BrokenBarrierException e){
//                e.printStackTrace();
//            }
            try {
                let.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            checkFinish();

        }
    }
    private void checkFinish(){
        for (int i = leftIndex; i < rightIndex-1; i++) {
            if (recruits[i] == 1 && recruits[i+1] == 0) {
                finished.set(false);
                break;
            }
        }
        finished.set(true);

    }
}
public class taskA {
    private static final SecureRandom random = new SecureRandom();
    private static final int SIZE = 50;
    private static final char [] recr = new char [SIZE];
    private static final int [] recruits = new int [SIZE];

    private static void fillingArray(){
        for (int i = 0; i < SIZE; i++) {
            if (random.nextBoolean()) {
                recr[i] = '>';
                recruits[i] = 1;

            }
            else {
                recr[i] = '<';
                recruits[i] = 0;
            }
        }
    }
    public static void main(String[] args) {
        fillingArray();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(recruits[i] + " ");
        }
        Let let = new Let(2);
//        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread thread = new Thread(new Rookie(recruits, let, 1, 0, 25));
        Thread thread1 = new Thread(new Rookie(recruits, let, 2, 25, SIZE));

        thread.start();
        thread1.start();

    }
}
