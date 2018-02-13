package my.skypiea.punygod.graphs;

import my.skypiea.punygod.stacks.Stack;

public class DigraphCycle {
    private boolean[] marked;
    private int[] edgTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public DigraphCycle(Digraph G) {
        marked = new boolean[G.V()];
        edgTo = new int[G.V()];
        onStack = new boolean[G.V()];

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) dfs(G, s);
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : G.adj(v)) {
            if (hasCycle()) return;
            if (!marked[w]) {
                edgTo[w] = v;
                dfs(G, w);
            } else {
                if (onStack[w]) {
                    cycle = new Stack<>();
                    for (int x = v; x != w; x = edgTo[x]) {
                        cycle.push(x);
                    }
                    cycle.push(w);
                    cycle.push(v);
                }
            }
        }
        onStack[v] = false;

    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasCycle() {
        return cycle != null;
    }
}
