package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private E[] data;             // data.length = capacity
    private int size;            // how many elements actually in the backing array
    private static final int initialCapacity = 10;
    private static final int GROWTHFACTOR = 2;


    public ArrayDeque(int initialCapacity) {
        this.data = (E[])new Object[initialCapacity];
        this.size = 0;
    }

    public ArrayDeque() {

        this(initialCapacity);
    }

    private int capacity() {

        return this.data.length;
    }


    private void ensureCapacity(int size) {
        if (this.capacity() < size) {
            E[] newData = (E[])new Object[(int)(this.capacity()*GROWTHFACTOR)];
            for (int i = 0; i < this.size; i++) {
                newData[i] = this.data[i];
            }
            this.data = newData;
        }
    }

    @Override
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        String result = "[";
        for (int i = 0; i < this.size; i++) {
            result += this.data[i] + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result + "]";
    }

    @Override
    public void addFront(E e) {
        this.ensureCapacity(this.size + 1);
        for (int i = this.size; i > 0; i--) {
            this.data[i] = this.data[i-1];
        }
        this.data[0] = e;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        this.ensureCapacity(this.size + 1);
        this.data[this.size] = e;
        this.size++;
    }

    @Override
    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        E returned = this.data[0];
        for (int i = 1; i < this.size; i++) {
            this.data[i-1] = this.data[i];
        }
        this.data[this.size-1] = null;            // eliminate copy of last element that was assigned to the element before it
        this.size--;
        return returned;
    }

    @Override
    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        E returned = this.data[this.size-1];
        this.data[this.size-1] = null;           // using null to remove element in back of arraydeque
        this.size--;
        return returned;
    }

    @Override
    public boolean enqueue(E e) {               // add element to back of arraydeque
        this.addFront(e);
        return true;

    }

    @Override
    public E dequeue() {                // remove element from front of arraydeque
        return this.removeBack();
    }

    @Override
    public boolean push(E e) {
        this.addBack(e);
        return true;
    }

    @Override
    public E pop() {

        return this.removeBack();
    }

    @Override
    public E peekFront() {
        if (this.size == 0) {
            return null;
        }
        return this.data[0];
    }

    @Override
    public E peekBack() {
        if (this.size == 0) {
            return null;
        }
        return this.data[this.size-1];
    }

    @Override
    public E peek() {
        return this.peekBack();
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<E> {
        private int currentIndex;

        public ArrayDequeIterator() {

            this.currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.currentIndex < ArrayDeque.this.size;
        }

        @Override
        public E next() {
            E element = ArrayDeque.this.data[this.currentIndex];
            this.currentIndex++;
            return element;
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}

