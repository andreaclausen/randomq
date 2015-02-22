/*************************************************************************
 * Compilation: javac Subset.java
 * Execution: java Subset k
 
 * Client program that takes a command-line integer k; reads in a sequence
 * of N strings from standard input, and prints out exactly k of them, uniformly
 * at random. Each item from the sequence will be printed out at most once.
 * 
 * Author: Andrea Clausen
 * Date: 2015-02-22
 *
 *************************************************************************/

public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
         }
        
        for (int i = 0; i < k; i++) {
            StdOut.print(q.dequeue() + "\n");
        }   
    }
}