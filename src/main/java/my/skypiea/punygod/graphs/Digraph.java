package my.skypiea.punygod.graphs;

import my.skypiea.punygod.queues.Bag;

public class Digraph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int v) {
        V = v;
        adj = (Bag<Integer>[]) new Object[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(V);
        for (int i = 0; i < V; i++) {
            for (int w : adj(i)) {
                reverse.addEdge(w, i);
            }
        }
        return reverse;
    }


}
