/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        validateParam(G);
        digraph = G;
    }

    private int[] bfs(int num) {
        int[] dist = new int[digraph.V()];
        for (int i = 0; i < dist.length; ++i) {
            dist[i] = Integer.MAX_VALUE;
        }
        boolean[] marked = new boolean[digraph.V()];
        for (int i = 0; i < marked.length; ++i) {
            marked[i] = false;
        }

        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(num);
        marked[num] = true;
        dist[num] = 0;

        while (!queue.isEmpty()) {
            int n = queue.dequeue();
            for (int v : digraph.adj(n)) {
                if (marked[v]) continue;
                queue.enqueue(v);
                marked[v] = true;
                dist[v] = dist[n] + 1;
            }
        }
        
        return dist;
    }

    // length of shortest ancestral path between v and w
    // -1 if no such path
    public int length(int v, int w) {
        validateNum(v);
        validateNum(w);

        int[] distFromV = bfs(v);
        int[] distFromW = bfs(w);
        int minDist = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); ++i) {
            int distSum = distFromV[i] + distFromW[i];
            if (distSum >= 0 && distSum < minDist) {
                minDist = distSum;
            }
        }
        if (minDist == Integer.MAX_VALUE) return -1;
        return minDist;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path
    // -1 if no such path
    public int ancestor(int v, int w) {
        validateNum(v);
        validateNum(w);

        int[] distFromV = bfs(v);
        int[] distFromW = bfs(w);
        int minDist = Integer.MAX_VALUE;
        int minAncestor = -1;
        for (int i = 0; i < digraph.V(); ++i) {
            int distSum = distFromV[i] + distFromW[i];
            if (distSum >= 0 && distSum < minDist) {
                minDist = distSum;
                minAncestor = i;
            }
        }
        return minAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w
    // -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateParam(v);
        validateParam(w);

        int minLength = Integer.MAX_VALUE;
        for (Integer vv : v) {
            validateParam(vv);
            validateNum(vv);
            for (Integer ww : w) {
                validateParam(ww);
                validateNum(ww);
                int length = length(vv, ww);
                if (length == -1) continue;
                if (length < minLength) minLength = length;
            }
        }
        if (minLength == Integer.MAX_VALUE) return -1;
        return minLength;
    }

    // a common ancestor that participates in shortest ancestral path
    // -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateParam(v);
        validateParam(w);

        int minLenght = Integer.MAX_VALUE;
        int minAncestor = -1;

        for (Integer vv : v) {
            validateParam(vv);
            validateNum(vv);

            for (Integer ww : w) {
                validateParam(ww);
                validateNum(ww);

                int length = length(vv, ww);
                if (length == -1) continue;
                if (length < minLenght) {
                    minLenght = length;
                    minAncestor = ancestor(vv, ww);
                }
            }
        }
        return minAncestor;
    }

    // check the input is null or not
    private void validateParam(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("input argument is null");
        }
    }

    // check the input number is outside range or not
    private void validateNum(int num) {
        if (num < 0 || num >= digraph.V()) {
            throw new IllegalArgumentException("input vertex is outside range");
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
