import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> list = new RandomizedQueue<>();

        while (true) {
            try {
                list.enqueue(StdIn.readString());
            }
            catch (NoSuchElementException e) {
                break;
            }
        }

        for (int i = 0; i < k; i++) {
            System.out.println(list.sample());
        }
    }
}
