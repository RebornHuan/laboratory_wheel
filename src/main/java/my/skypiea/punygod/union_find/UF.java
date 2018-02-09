package my.skypiea.punygod.union_find;

public abstract class UF {
    protected int[] id;
    protected int count;

    public UF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        count = N;
    }

    /**
     * 在 p 和 q 之间添加一条连接
     *
     * @param p
     * @param q
     */
    abstract public void union(int p, int q);

    /**
     * p(0到N-1)所在的分量的标识符
     *
     * @param p
     * @return
     */
    abstract public int find(int p);


    /**
     * 如果p和q存在于同一个分量中则返回
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    /**
     * 连通分量的数量
     *
     * @return
     */
    public int count() {
        return count;
    }
}
