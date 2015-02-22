/*************************************************************************
 * Compilation: javac RandomizedQueue.java
 * Execution: java RandomizedQueue
 
 * Implementation of a randomized queue using a resizing array
 * where the item removed is chosen uniformly at random from
 * items in the data structure.
 * 
 * Author: Andrea Clausen
 * Date: 2015-02-21
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;            // queue elements
    private int N = 0;           // number of elements on queue
    private int rand = 0;        // index of random element in queue
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        // cast needed since no generic array creation in Java
        q = (Item[]) new Object[2];
    }
    
    /**
     * is the queue empty?
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }
    
    /**
     * return the number of items in the queue
     * @return the number of items in this queue
     */
    public int size() {
        return N;
    }
    
    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }    
    
    /**
     * add the item to the queue
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Cannot add null item");
        // double size of array if necessary and recopy to front of array
        if (N == q.length) resize(2*q.length);   // double size of array if necessary
        q[N++] = item;                           // add item
    }
    
    /**
     * remove and return a random item
     * @return a random item in the queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        rand = StdRandom.uniform(N);
        Item item = q[rand];
        q[rand] = q[N-1];
        q[N-1] = null;                            // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == q.length/4) resize(q.length/2); 
        return item;    
    }
    
    /**
     * return (but do not remove) a random item
     * @return a random item in the queue
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        rand = StdRandom.uniform(N);
        return q[rand];
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indexes = new int[N];
        
        private ArrayIterator() {
            for (int j = 0; j < N; j++) {
                indexes[j] = j;
            }
        }
        
        public boolean hasNext() {
            return i < N;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            rand = StdRandom.uniform(N-i);
            int index = indexes[rand];
            indexes[rand] = indexes[N-i-1];
            indexes[N-i-1] = index;
            Item item = q[index];
            i++;
            return item;
        }
    }
    
    /**
     * unit testing
     */
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}