package taskB;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
class Monitor{
    private final int threadsNum;
    private boolean Equal = true;
    private int thrCounter = 0;
    private boolean check = false;

    public boolean isEqual() {
        return Equal;
    }

    private final int [] threads;

    public Monitor(int threadsNum) {
        this.threadsNum = threadsNum;
        threads = new int[threadsNum];
    }
    public void equalCheck(){
        boolean isEqual = true;
        Arrays.sort(threads);
        for (int i = 0; i < threads.length-2; i++) {
            if (threads[i] != threads[i+1]){
                isEqual = false;
                break;
            }
        }
        if (!isEqual){
            if (threads[0] == threads[threads.length-2] || threads[threads.length-1] == threads[1]){
                Equal = false;
                System.out.println("Are equal");
            }
        }
    }
    public synchronized void getThreads(int data){
        threads[thrCounter] = data;
        thrCounter++;
        if (thrCounter == threadsNum) {
            notifyAll();
            check = true;
        }
        while (!check){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        thrCounter--;
        if (thrCounter == 0){
            equalCheck();
            check = false;
        }
    }
    
}
class Swaper implements Runnable{
    private final Random random = new Random();
    private boolean work = true;
    private String line;
    private final CyclicBarrier barrier;
    private final Monitor monitor;
    private int countAB;
    private final int index;

    public Swaper(String line, CyclicBarrier barrier, Monitor monitor, int index) {
        this.line = line;
        this.barrier = barrier;
        this.monitor = monitor;
        this.index = index;
        this.countAB = countABMentioning(line);
    }

    @Override
    public void run() {
        while (work){
            int randIndex = random.nextInt(line.length());
            switch (line.charAt(randIndex)){
                case 'A' :{
                    line = line.substring(0,randIndex) + 'C' + line.substring(randIndex + 1);
                    countAB--;
                    break;
                }
                case 'B' :
                    line = line.substring(0,randIndex) + 'D' + line.substring(randIndex + 1);
                    countAB--;
                    break;
                case 'C':
                    line = line.substring(0,randIndex) + 'A' + line.substring(randIndex + 1);
                    countAB++;
                    break;
                case 'D':
                    line = line.substring(0,randIndex) + 'B' + line.substring(randIndex + 1);
                    countAB++;
                    break;
            }
            monitor.getThreads(countAB);
            System.out.println("Thread" + this.index + " " + line + " " + countAB);
            try {
                barrier.await();
            }catch (InterruptedException | BrokenBarrierException e){
                e.printStackTrace();
            }
            if (this.index == 1){
                System.out.println();
            }
            work = monitor.isEqual();
        }
    }

    private int countABMentioning(String str){
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'A' || str.charAt(i) == 'B'){
                count++;
            }
        }
        return count;
    }
}

public class taskB {
    private static final int NUM_THR = 4;

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(NUM_THR);
        Monitor monitor = new Monitor(NUM_THR);

        Thread Line1 = new Thread(new Swaper("ABCDCDAABCD", barrier, monitor, 1));
        Thread Line2 = new Thread(new Swaper("AAACAACBBAC", barrier, monitor, 2));
        Thread Line3 = new Thread(new Swaper("ACDCADCACDC", barrier, monitor, 3));
        Thread Line4 = new Thread(new Swaper("CDABBABCDAB", barrier, monitor, 4));
        Line1.start();
        Line2.start();
        Line3.start();
        Line4.start();

    }
}
