package pq;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Demonstrates how you can use either System.currentTimeMillis
 * or the Princeton Stopwatch class to time code.
 */
public class TimingTestDemo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < 100000; i += 1) {
            for (int j = 0; j < 10000; j += 1) {
                sum = sum + i + j;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed: " + (end - start) / 1000.0 +  " seconds.");

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 100000; i += 1) {
            for (int j = 0; j < 10000; j += 1) {
                sum = sum + i + j;
            }
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 100000; j++) {
                i++;
            }
        }
        System.out.println("Total time elapsed: " + sw.elapsedTime() +  " seconds.");
        ExtrinsicMinPQ<Integer> minPQ = new ArrayHeapMinPQ<>();
    }
}
