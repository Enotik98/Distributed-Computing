package a;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

class myThread extends Thread {
    private int increment;
    private JSpinner spinner;
    private int count = 0;
    private int priority;
    private int BOUND = 1000000;

    public myThread(JSpinner spinner, int increment) {
        this.spinner = spinner;
        this.increment = increment;
        this.priority = (int)spinner.getValue();
        setPriority(priority);

    }

    @Override
    public void run() {
        while (!interrupted()) {
            int val = Main.mySlider.getValue();
            ++count;

            if (count > BOUND ) {
                priority = (int)spinner.getValue();
                setPriority(priority);
                Main.mySlider.setValue(val + increment);
                count = 0;
            }

        }
    }

}

public class Main {

    static JSlider mySlider;

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 500);

        GridLayout ly = new GridLayout(6, 1, 100 , 20);

        JPanel myPanel = new JPanel();
        myPanel.setLayout(ly);
        mySlider = new JSlider(0, 100, 50);

        mySlider.setMajorTickSpacing(10);
        mySlider.setMinorTickSpacing(5);
        mySlider.setPaintLabels(true);
        mySlider.setPaintTicks(true);

        SpinnerModel spin1 = new SpinnerNumberModel(1, 0, 10, 1);
        JSpinner spinner1 = new JSpinner(spin1);
        SpinnerModel spin2 = new SpinnerNumberModel(1, 0, 10, 1);
        JSpinner spinner2 = new JSpinner(spin2);


        myThread myThread1 = new myThread(spinner1, +1);
        myThread myThread2 = new myThread(spinner2, -1);

        JButton btn = new JButton("Start");
        btn.addActionListener(
                (ActionEvent e) -> {
                    myThread1.start();
                    myThread2.start();

                    btn.setEnabled(false);
                });

        Box bh = Box.createHorizontalBox();
        bh.setSize(300, 200);

        bh.add(spinner1);
        bh.add(spinner2);
        myPanel.add(mySlider);
        myPanel.add(bh);
        myPanel.add(btn);

        window.setContentPane(myPanel);
        window.setVisible(true);
    }
}

