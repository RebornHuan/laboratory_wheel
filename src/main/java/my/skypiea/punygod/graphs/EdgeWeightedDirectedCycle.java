package my.skypiea.punygod.graphs;

import my.skypiea.punygod.stacks.Stack;

public class EdgeWeightedDirectedCycle {
    private boolean[] marked;
    private DirectedEdge[] edgTo;
    private Stack<DirectedEdge> cycle;
    private boolean[] onStack;

    public EdgeWeightedDirectedCycle(EdgeWeightedDiGraph G) {
        marked = new boolean[G.V()];
        edgTo = new DirectedEdge[G.V()];
        onStack = new boolean[G.V()];

        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) dfs(G, s);
        }
    }

    private void dfs(EdgeWeightedDiGraph G, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            if (hasCycle()) return;
            int w = e.to();
            if (!marked[w]) {
                edgTo[w] = e;
                dfs(G, w);
            } else {
                if (onStack[w]) {
                    cycle = new Stack<DirectedEdge>();
                    DirectedEdge f = e;
                    while (f.from() != w) {
                        cycle.push(f);
                        f = edgTo[f.from()];
                    }
                    cycle.push(f);
                }
            }
        }
        onStack[v] = false;

    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    public boolean hasCycle() {
        return cycle != null;
    }
}
