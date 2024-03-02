/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.TreeMap;

public class WordNet {
    private final Digraph digraph;
    private final SAP sap;
    private final TreeMap<Integer, String> id2Noun;
    private final TreeMap<String, Bag<Integer>> noun2Ids;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        validateParam(synsets);
        validateParam(hypernyms);

        id2Noun = new TreeMap<Integer, String>();
        noun2Ids = new TreeMap<String, Bag<Integer>>();

        readSynsets(synsets);
        digraph = new Digraph(id2Noun.size());
        readHypernyms(hypernyms);
        sap = new SAP(digraph);
    }

    // read synsets.txt, add the synset to synsetsList
    private void readSynsets(String synsets) {
        In in = new In(synsets);
        int order = 0;
        String noun = "";
        String[] lines = in.readAllLines();
        while (order != lines.length) {
            noun = lines[order].split(",")[1];
            id2Noun.put(order, noun);
            for (String s : noun.split(" ")) {
                if (noun2Ids.containsKey(s)) {
                    noun2Ids.get(s).add(order);
                }
                else {
                    Bag<Integer> ids = new Bag<Integer>();
                    ids.add(order);
                    noun2Ids.put(s, ids);
                }
            }
            order++;
        }
    }

    // read hypernyms.txt, add the edge to the directed graph
    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        int order = 0;
        String[] lines = in.readAllLines();

        while (order != lines.length) {
            String[] words = lines[order].split(",");
            for (int i = 1; i < words.length; ++i) {
                digraph.addEdge(Integer.parseInt(words[0]), Integer.parseInt(words[i]));
            }
            order++;
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return noun2Ids.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validateParam(word);
        return noun2Ids.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateParam(nounA);
        validateParam(nounB);
        validateWord(nounA);
        validateWord(nounB);

        return sap.length(noun2Ids.get(nounA), noun2Ids.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (define below)
    public String sap(String nounA, String nounB) {
        validateParam(nounA);
        validateParam(nounB);
        validateWord(nounA);
        validateWord(nounB);

        return id2Noun.get(sap.ancestor(noun2Ids.get(nounA), noun2Ids.get(nounB)));
    }

    // check the input is null or not
    private void validateParam(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("input argument is null");
        }
    }

    // check the input word is whether in the
    private void validateWord(String word) {
        if (!noun2Ids.containsKey(word)) {
            throw new IllegalArgumentException("input " + word + " is not in synsets");
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
