/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> deque = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            deque.enqueue(item);
        }

        for (int i = 0; i < Integer.parseInt(args[0]); ++i) {
            StdOut.println(deque.dequeue());
        }
    }
}
