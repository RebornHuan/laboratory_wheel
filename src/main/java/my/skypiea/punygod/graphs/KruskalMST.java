package my.skypiea.punygod.graphs;

import my.skypiea.punygod.queues.MinPQ;
import my.skypiea.punygod.queues.Queue;
import my.skypiea.punygod.union_find.QuickFindUF;
import my.skypiea.punygod.union_find.UF;

public class KruskalMST {
    private double weight;
    private Queue<Edge> mst = new Queue<>();

    public KruskalMST(EdgeWeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : G.edges()) {
            pq.insert(e);
        }

        UF uf = new QuickFindUF(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();
            int w = e.either();
            int v = e.other(w);
            if (!uf.connected(w, v)) {
                uf.union(w, v);
                mst.enqueue(e);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
