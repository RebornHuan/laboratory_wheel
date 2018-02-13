package my.skypiea.punygod.graphs;

import my.skypiea.punygod.stacks.Stack;

public class Cycle {
    private boolean[] marked;
    private boolean hasCycle;
    private Stack<Integer> cycle;
    private int[] edgeTo;


    public Cycle(Graph G) {
        if (hasSelfLoop(G)) return;
        if (hasParallelEdges(G)) return;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int s = 0; s < G.V(); s++) {
            dfs(G, s, s);
        }
    }

    public void dfs(Graph G, int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (hasCycle) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w, v);
            } else {
                if (u != w) {
                    hasCycle = true;
                    cycle = new Stack<Integer>();
                    for (int x = v; x != w; x = edgeTo[x]) {
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }
        }
    }

    private boolean hasSelfLoop(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (w == v) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasParallelEdges(Graph G) {
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {

            for (int w : G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<Integer>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                }
                marked[w] = true;
            }

            for (int w : G.adj(v)) {
                marked[w] = false;
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
