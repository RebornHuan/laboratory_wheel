package my.skypiea.punygod.graphs;

import my.skypiea.punygod.queues.MinPQ;
import my.skypiea.punygod.queues.Queue;

public class LazyPrimeMST {

    private double weight;
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimeMST(Graph G) {
        pq = new MinPQ<>();
        mst = new Queue<>();
        marked = new boolean[G.V()];
    }

    private void prim(EdgeWeightedGraph G, int s) {
        scan(G, s);
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (!marked[v] && !marked[w]) continue;
            mst.enqueue(e);
            weight = e.weight();
            if (!marked[v]) scan(G, v);
            if (!marked[w]) scan(G, w);
        }

    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (!marked[e.other(v)]) pq.insert(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }


}
