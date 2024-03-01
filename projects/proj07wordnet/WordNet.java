/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private Digraph digraph;
    private ArrayList<String> synsetsList;
    private HashMap<String, Integer> synsetsMap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        validateParam(synsets);
        validateParam(hypernyms);

        synsetsList = new ArrayList<String>();
        synsetsMap = new HashMap<String, Integer>();

        readSynsets(synsets);
        digraph = new Digraph(synsetsList.size());
        readHypernyms(hypernyms);
    }

    // read synsets.txt, add the synset to synsetsList
    private void readSynsets(String synsets) {
        In in = new In(synsets);
        int order = 0;
        String noun = "";
        while (!StdIn.isEmpty()) {
            noun = in.readLine().split(",")[1];
            synsetsList.add(noun);
            synsetsMap.put(noun, order);
            order++;
        }
    }

    // read hypernyms.txt, add the edge to the directed graph
    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);
        while (!StdIn.isEmpty()) {
            String[] words = in.readLine().split(",");
            for (int i = 1; i < words.length; ++i) {
                digraph.addEdge(Integer.parseInt(words[0]), Integer.parseInt(words[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return synsetsList;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        validateParam(word);
        return synsetsList.contains(word);
    }

    // TODO
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateParam(nounA);
        validateParam(nounB);
        validateWord(nounA);
        validateWord(nounB);
    }

    // TODO
    private int bfs(String nounA, String nounB) {
        int numA = synsetsMap.get(nounA);
        int numB = synsetsMap.get(nounB);

    }

    // TODO
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (define below)
    public String sap(String nounA, String nounB) {
        validateParam(nounA);
        validateParam(nounB);
        validateWord(nounA);
        validateWord(nounB);
    }

    // check the input is null or not
    private void validateParam(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("input argument is null");
        }
    }

    // check the input word is whether in the
    private void validateWord(String word) {
        if (!synsetsList.contains(word)) {
            throw new IllegalArgumentException("input word is not in synsets");
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
