package para;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LongestSequence {
    public static class SequenceRange {
        public int matchingOnLeft, matchingOnRight;
        public int longestRange, sequenceLength;

        public SequenceRange(int left, int right, int longest, int length) {
            this.matchingOnLeft = left;
            this.matchingOnRight = right;
            this.longestRange = longest;
            this.sequenceLength = length;
        }
    }

    static final ForkJoinPool POOL = new ForkJoinPool();

    public static int getLongestSequence(int val, int[] arr, int sequentialCutoff) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    private static void usage() {
        System.err.println("USAGE: LongestSequence <number> <array> <sequential cutoff>");
        System.exit(2);
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            usage();
        }
        int val = 0;
        int[] arr = null;
        try {
            val = Integer.parseInt(args[0]);
            String[] stringArr = args[1].replaceAll("\\s*",  "").split(",");
            arr = new int[stringArr.length];
            for (int i = 0; i < stringArr.length; i++) {
                arr[i] = Integer.parseInt(stringArr[i]);
            }
            System.out.println(getLongestSequence(val, arr, Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            usage();
        }
    }
}
