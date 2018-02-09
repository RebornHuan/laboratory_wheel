package my.skypiea.punygod.union_find;

public class QuickFindUF extends UF {
    public QuickFindUF(int N) {
        super(N);
    }

    @Override
    public void union(int p, int q) {
        int idP = find(p);
        int idQ = find(q);
        if (idP == idQ) return;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == idP) id[i] = idQ;
        }
        count--;

    }

    @Override
    public int find(int p) {
        return id[p];
    }
}
