package org.example.Graph.DirectGraph;

/**
 * author: osmanthuspeace
 * createTime: 2024/5/22
 */
public class KosarajuStrongCC {
    private final boolean[] marked;
    private final int[] id;
    private int count;

    public KosarajuStrongCC(DirectGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        var order = new DFOrder(G.reverse());
        //按反向图的拓扑序搜索正向图（有强联通分量的图一定是有环的，此处的拓扑序只是逆后序，但是可以保证无环的部分的顺序是正确的）
        //如果正向图中的s0可以到达v2，那么反向图中v2就可以到达s0，那么拓扑序中v2就在s0之前（然而实际上v2在s0之后，只能说明v2和s0之间是环）
        //那么在正向图中，就会先搜索v，再搜索s，此时如果v不能到s，那么v被标记而s没有被标记，就会导致s无法到达v，不成立，故v也能到s
        //或者这样理解：
        //将每一个强联通分量看成一个点，称为缩点，
        //那么这样简化之后图就变成了一个无环图，此时要遍历这个图找所有的强联通分量，需要用拓扑序，
        //因为用反向图的拓扑序遍历意味着从没有子节点的强联通分量开始，此时的每一个当前缩点是无法到达其他的缩点的
        for (int s : order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(DirectGraph G, int cur) {
        marked[cur] = true;
        id[cur] = count;
        for (int w : G.adj(cur)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
    }

    public boolean isStronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }
}
