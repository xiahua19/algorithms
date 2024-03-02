/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        validateParam(wordnet);
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        validateParam(wordnet);

        int[] dist = new int[nouns.length];
        for (int i = 0; i < dist.length; ++i) {
            dist[i] = 0;
        }

        for (int i = 0; i < nouns.length; ++i) {
            for (int j = 0; j < nouns.length; ++j) {
                dist[i] += wordnet.distance(nouns[i], nouns[j]);
            }
        }

        int maxDist = 0;
        int maxIndex = 0;
        for (int i = 0; i < dist.length; ++i) {
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                maxIndex = i;
            }
        }
        return nouns[maxIndex];
    }

    // check the input is null or not
    private void validateParam(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("input argument is null");
        }
    }


    // unit testing
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
