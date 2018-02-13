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

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }
}
