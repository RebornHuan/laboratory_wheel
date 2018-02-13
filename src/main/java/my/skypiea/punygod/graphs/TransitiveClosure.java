package my.skypiea.punygod.graphs;

public class TransitiveClosure {
    private DigraphDFS[] all;

    public TransitiveClosure(Digraph G) {
        all = new DigraphDFS[G.V()];
        for (int i = 0; i < G.V(); i++) {
            all[i] = new DigraphDFS(G, i);
        }
    }

    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }

    private void validateVertex(int v) {
        int V = all.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
