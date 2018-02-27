package my.skypiea.punygod.graphs;

public class Topological {

    private Iterable<Integer> order;

    public Topological(Digraph G) {
        DigraphCycle digraphCycle = new DigraphCycle(G);
        if (!digraphCycle.hasCycle()) {
            DepthFirstOrder depthFirstOrder = new DepthFirstOrder(G);
            order = depthFirstOrder.reversePost();
        }
    }

    public Topological(EdgeWeightedDiGraph G) {
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }
}
