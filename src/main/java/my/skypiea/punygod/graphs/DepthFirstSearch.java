package my.skypiea.punygod.graphs;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    /**
     * 找到和起点s连通的所有顶点;
     *
     * @param G
     * @param s
     */
    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        validateVertex(s);
        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        marked[s] = true;
        count++;
        for (int w : G.adj(s)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean marked(int w) {
        return marked[w];
    }

    public int count() {
        return count;
    }

    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }


}
