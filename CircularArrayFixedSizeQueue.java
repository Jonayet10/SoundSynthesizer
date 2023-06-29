package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IFixedSizeQueue;

import java.util.Iterator;

public class CircularArrayFixedSizeQueue<E> implements IFixedSizeQueue<E> {
    // idx 0 is front of queue, deqeueing (removing) from here
    // idx = len - 1 is end of queue, deqeueing (adding) here

    private int front;
    private int back;
    private E[] data;
    // size can be calculated with abs((back-front)) + 1

    public CircularArrayFixedSizeQueue(int capacity) {
        this.data = (E[]) new Object[capacity];
        this.front = 0;
        this.back = 0;
    }

    @Override
    public String toString() {          // should loop from front to back pointers
        if (this.size() == 0) {
            return "[]";
        }
        String result = "[";
        if (back >= front) {
            for (int i = front; i <= back; i++) {result += this.data[i] + ", ";
            }
        } else if (back < front) {
            for (int i = front; i <= this.data.length; i++) {
                result += this.data[i] + ", ";
            }
            for (int i = 0; i <= back; i++) {
                result += this.data[i] + ", ";
            }
        }
        result = result.substring(0, result.length() - 2);
        return result + "]";
    }

    @Override
    public boolean isFull() {
        return this.size() == this.data.length;
    }

    @Override
    public int capacity() {
        return this.data.length;
    }

    @Override
    public boolean enqueue(E e) {
        if (this.size() == 0) {
            this.data[back] = e;
            front = back;
            return true;
        }
        if (this.size() == this.capacity()) {
            return false;
        }
        this.data[(back + 1) % this.data.length] = e;
        back = (back + 1) % this.data.length;
        return true;
    }

    @Override
    public E dequeue() {
        if (this.size() == 0) {
            return null;
        }
        E returned = this.data[front];
        this.data[front] = null;
        front = (front + 1) % this.data.length;
        return returned;
    }

    @Override
    public E peek() {
        if (this.size() == 0) {
            return null;
        }
        return this.data[front];
    }

    @Override
    public int size() {
        int size = 0;
        if (back > front) {
            size = (back - front) + 1;
        } else if (this.data[back] == null && this.data[front] == null) {
            size = 0;
        } else if (back < front) {
            size = this.data.length - ((front - back) - 1);
        } else if (back == front && this.data[front+1] == null) {
                size = 1;
        } else if (back == front) {
            size = this.data.length;
        }
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularArrayFixedSizeQueueIterator();
    }

    private class CircularArrayFixedSizeQueueIterator implements Iterator<E> {
        private int currIdx;

        public CircularArrayFixedSizeQueueIterator() {

            this.currIdx = 0;
        }

        @Override
        public boolean hasNext() {
            // case : [0,0,0,back,0,0,front,0,0]
            if (this.currIdx < CircularArrayFixedSizeQueue.this.size()) {
                return true;
            }
            return false;
        }

        @Override
        public E next () {
            E element = CircularArrayFixedSizeQueue.this.data[(front + this.currIdx) % capacity()];
            currIdx++;
            return element;

            }
        }
    }
