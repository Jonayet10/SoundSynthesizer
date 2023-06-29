package edu.caltech.cs2.project03;

import edu.caltech.cs2.datastructures.CircularArrayFixedSizeQueue;
import edu.caltech.cs2.interfaces.IFixedSizeQueue;
import edu.caltech.cs2.interfaces.IQueue;

import java.util.Random;


public class CircularArrayFixedSizeQueueGuitarString {
    private final static double samplingRate = 44100;
    private IFixedSizeQueue<Double> guitarString;
    private int capacity;
    private static Random rand = new Random();
    private final static double energyDecayFactor = 0.996;

    public CircularArrayFixedSizeQueueGuitarString(double frequency) {
        if (samplingRate % frequency == 0) {
            capacity = (int) (samplingRate / frequency);
        } else {
            capacity = (int) ((samplingRate / frequency) + 1);
        }
        this.guitarString = new CircularArrayFixedSizeQueue<>(capacity);

        for (int i = 0; i < capacity; i++) {
            this.guitarString.enqueue(0.0);
        }
    }

    public int length() {
        return guitarString.capacity();
    }
    //
    public void pluck() {
        guitarString = new CircularArrayFixedSizeQueue<>(capacity);
        for (int i = 0; i < capacity; i++) {
            double x = (1.5 + rand.nextDouble() - 2);
            guitarString.enqueue(x);  // is nextDouble going to go by 0.1s?
        }

    }

    public void tic() {
        double x = (double) guitarString.peek();           // why does IntelliJ make me cast this to double?
        guitarString.dequeue();
        double y = (double) guitarString.peek();
        double z = ((x + y) / 2) * energyDecayFactor;
        guitarString.enqueue(z);

    }

    public double sample() {
        return (double) guitarString.peek();
    }
}
