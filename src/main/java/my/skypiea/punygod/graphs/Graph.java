package my.skypiea.punygod.graphs;

import my.skypiea.punygod.queues.Bag;


/**
 * 邻接表数组
 */
public class Graph {

    private static final String NEWLINE = System.getProperty("line.separator");


    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Graph(int v) {
        V = v;
        adj = new Bag[v];
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

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    /**
     * 无向图
     *
     * @param v
     * @param w
     */
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public int maxDegree() {
        int max = 0;
        for (int i = 0; i < V; i++) {
            if (degree(i) > max) {
                max = degree(i);
            }
        }
        return max;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
