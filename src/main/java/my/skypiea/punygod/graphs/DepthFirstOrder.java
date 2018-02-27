package my.skypiea.punygod.graphs;

import my.skypiea.punygod.queues.Queue;
import my.skypiea.punygod.stacks.Stack;

public class DepthFirstOrder {
    private boolean[] marked;
    /**
     * 前序: 在递归调用前将顶点加入队列
     **/
    private Queue<Integer> pre;
    /**
     * 后序: 在递归调用后将顶点加入队列
     **/
    private Queue<Integer> post;
    /**
     * 逆后序: 在递归调用后将顶点压入堆栈
     **/
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];
    }

    public DepthFirstOrder(EdgeWeightedDiGraph G) {
        pre = new Queue<>();
        post = new Queue<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }


    private void dfs(Graph G, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }

    private void dfs(EdgeWeightedDiGraph G, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }


}
