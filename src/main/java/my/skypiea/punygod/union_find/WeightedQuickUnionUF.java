package my.skypiea.punygod.union_find;

public class WeightedQuickUnionUF extends UF {
    private int[] sz;

    public WeightedQuickUnionUF(int N) {
        super(N);
        this.sz = new int[N];
        for (int i = 0; i < N; i++) {
            sz[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
        count--;

    }

    @Override
    public int find(int p) {
        while (p != id[p]) p = id[p];
        return p;
    }
}
