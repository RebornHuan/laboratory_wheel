package my.skypiea.punygod.graphs;

import my.skypiea.punygod.stacks.Stack;

public class TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private int[] edgTo;
    private Stack<Integer> cycle;
    private boolean isBipartite = true;

    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        edgTo = new int[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
            }
        }
    }

    public void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (isBipartite) return;
            if (!marked[w]) {
                color[w] = !color[v];
                edgTo[w] = v;
                dfs(G, w);
            } else {
                if (color[w] == color[v]) {
                    cycle = new Stack<Integer>();
                    cycle.push(w);
                    cycle.push(v);
                    isBipartite = false;
                }
            }
        }
    }

    public boolean color(int v) {
        validateVertex(v);
        if (!isBipartite)
            throw new UnsupportedOperationException("graph is not bipartite");
        return color[v];
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
