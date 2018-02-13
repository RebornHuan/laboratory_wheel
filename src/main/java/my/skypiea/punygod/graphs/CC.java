package my.skypiea.punygod.graphs;

public class CC {
    private boolean[] marked;
    private int count;
    private int[] id;
    private int[] size;

    public CC(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            if (!marked[i]) dfs(G, i);
            count++;
        }
    }

    public void dfs(Graph G, int s) {
        marked[s] = true;
        id[s] = count;
        size[count]++;
        for (int w : G.adj(s)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean connected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return id[v] == id[w];
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        validateVertex(v);
        return id[v];
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }
}
