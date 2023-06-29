package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private Node<E> head;
    private int size;
    private Node<E> tail;

    private static class Node<E> {
        public final E data;
        public Node<E> next;
        public Node<E> prev;;

        public Node(E data) {

            this(data, null, null);
        }

        public Node(E data, Node<E> next, Node<E> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    public LinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        String result = "[";
        Iterator<E> stringIterator = this.iterator();
        for (int i = 0; i < this.size; i++) {
            result += stringIterator.next() + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return result + "]";
    }

    @Override
    public void addFront(E e) {
        if (this.size == 0) {
            this.head = new Node<>(e);
            this.tail = this.head;
        } else {
            Node<E> oldHead = this.head;
            this.head = new Node<>(e);
            this.head.next = oldHead;
            oldHead.prev = this.head;
        }
        this.size++;
    // make a node variable, set it equal to this.head
        // this.head = node with e
        // node with e.next (this,head) = old head variable

        // storing a node as a variable maintains the succesding linked nodes

        // because this is doubly linked list, have to take care of prev pointer
        // node with oldhead.prev = this.head
    }

    @Override
    public void addBack(E e) {
        // special case if this.tail is empty
        if (this.size == 0) {
            this.tail = new Node<>(e);
            this.head = this.tail;
        } else {
            Node<E> oldTail = this.tail;
            oldTail.next = new Node<>(e);
            this.tail = oldTail.next;     // moving tail
            this.tail.prev = oldTail;
        }
        this.size++;
    }

    @Override
    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        E returned = this.head.data;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head = this.head.next;
            this.head.prev = null;
        }
        this.size--;
        return returned;
    }

    @Override
    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        E returned = this.tail.data;
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            Node<E> rest = this.tail.prev;
            this.tail = rest;
            this.tail.next = null;
        }
        this.size--;
        return returned;
    }

    @Override
    public boolean enqueue(E e) {       // return addFront
        this.addFront(e);
        return true;
    }

    @Override
    public E dequeue() {            // return removeBack
        if (this.size > 0) {
            return this.removeBack();
        }
        return null;
    }

    @Override
    public boolean push(E e) {
        this.addBack(e);
        return true;
    }

    @Override
    public E pop() {
        if (this.size > 0) {
            return this.removeBack();
        }
        return null;
    }

    @Override
    public E peekFront() {
        if (this.head == null) {
            return null;
        }
        return this.head.data;
    }

    @Override
    public E peekBack() {
        if (this.tail == null) {
            return null;
        }
        return this.tail.data;
    }

    @Override
    public E peek() {           // return peekBack
        if (this.size > 0) {
            return this.peekBack();
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    private class LinkedDequeIterator implements Iterator<E> {
        private Node<E> currNode;

        public LinkedDequeIterator(){
            currNode = LinkedDeque.this.head;
        }

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                return null;
            }
            E elem = (E)currNode.data;
            currNode = currNode.next;
            return elem;
        }
    }

    @Override
    public int size() {
        return this.size;
    }
}
