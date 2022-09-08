package b;

import javax.swing.*;

class myThread extends Thread {
    private int increment;
    private int priority;

    public myThread(int increment, int priority) {
        this.increment = increment;
        this.priority = priority;
        setPriority(priority);
    }

    @Override
    public void run(){
        Main.semaphone = 1;
        while (!interrupted()) {
            int val = Main.mySlider.getValue();
            if ((val > 10 && increment < 0) || (val < 90 && increment > 0)) {
                Main.mySlider.setValue(val + increment);
            }
        }

    }
}

public class Main {

    static volatile int semaphone = 0;
    static JLabel lbl;
    static JSlider mySlider;
    static  private myThread myThread1;
    static private myThread myThread2;


    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 500);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        mySlider = new JSlider(0,100);
        lbl = new JLabel();
        mySlider.setMajorTickSpacing(10);
        mySlider.setMinorTickSpacing(5);
        mySlider.setPaintLabels(true);
        mySlider.setPaintTicks(true);



        myPanel.add(mySlider);
        myPanel.add(lbl);

        JPanel T1Panel = new JPanel();
        JButton start1btn = new JButton("Start1");
        JButton stop1btn = new JButton("Stop1");
        stop1btn.setEnabled(false);

        start1btn.addActionListener(e -> {
            if (semaphone == 0) {
                Main.lbl.setText("");
                myThread1 = new myThread(-1, Thread.MIN_PRIORITY);
                myThread1.setPriority(Thread.MIN_PRIORITY);
                start1btn.setEnabled(false);
                stop1btn.setEnabled(true);
                myThread1.start();
            }else{
                Main.lbl.setText("Зайнято!");
            }
        });

        stop1btn.addActionListener(e -> {
            Main.lbl.setText("");
            myThread1.interrupt();
            start1btn.setEnabled(true);
            stop1btn.setEnabled(false);
            semaphone = 0;
        });

        JPanel T2Panel = new JPanel();
        JButton start2btn = new JButton("Start2");
        JButton stop2btn = new JButton("Stop2");
        stop2btn.setEnabled(false);

        start2btn.addActionListener(e -> {
            if (semaphone == 0) {
                Main.lbl.setText("");
                myThread2 = new myThread(+1, Thread.MAX_PRIORITY);
                myThread2.setPriority(Thread.MIN_PRIORITY);
                start2btn.setEnabled(false);
                stop2btn.setEnabled(true);
                myThread2.start();
            }else{
                Main.lbl.setText("Зайнято!");
            }
        });

        stop2btn.addActionListener(e -> {
            Main.lbl.setText("");
            myThread2.interrupt();
            start2btn.setEnabled(true);
            stop2btn.setEnabled(false);

            semaphone = 0;
        });

        T1Panel.add(start1btn);
        T1Panel.add(stop1btn);
        T2Panel.add(start2btn);
        T2Panel.add(stop2btn);

        myPanel.add(T1Panel);
        myPanel.add(T2Panel);
        window.setContentPane(myPanel);
        window.setVisible(true);
    }
}
