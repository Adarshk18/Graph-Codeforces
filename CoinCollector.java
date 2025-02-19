import java.io.*;
import java.util.*;

public class CoinCollector {
    static List<List<Integer>> graph, revGraph, sccGraph;
    static int[] coins, sccCoins, sccId;
    static long[] dp;
    static boolean[] vis;
    static Stack<Integer> stack;
    static int sccCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        coins = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            coins[i] = Integer.parseInt(st.nextToken());
        }

        graph = new ArrayList<>();
        revGraph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            revGraph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph.get(a).add(b);
            revGraph.get(b).add(a);
        }

        // 1. Find SCCs
        findSCCs(n);

        // 2. Build Condensed Graph
        buildCondensedGraph();

        // 3. DP on Condensed DAG
        dp = new long[sccCount + 1];
        long maxCoins = 0;
        for (int i = 1; i <= sccCount; i++) {
            maxCoins = Math.max(maxCoins, dfsSCC(i));
        }

        pw.println(maxCoins);
        pw.flush();
    }

    static void findSCCs(int n) {
        vis = new boolean[n + 1];
        stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) dfs1(i);
        }

        vis = new boolean[n + 1];
        sccId = new int[n + 1];
        sccCoins = new int[n + 1];
        sccCount = 0;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!vis[node]) {
                sccCount++;
                dfs2(node, sccCount);
            }
        }
    }

    static void dfs1(int u) {
        vis[u] = true;
        for (int v : graph.get(u)) {
            if (!vis[v]) dfs1(v);
        }
        stack.push(u);
    }

    static void dfs2(int u, int id) {
        vis[u] = true;
        sccId[u] = id;
        sccCoins[id] += coins[u];
        for (int v : revGraph.get(u)) {
            if (!vis[v]) dfs2(v, id);
        }
    }

    static void buildCondensedGraph() {
        sccGraph = new ArrayList<>();
        for (int i = 0; i <= sccCount; i++) {
            sccGraph.add(new ArrayList<>());
        }

        for (int u = 1; u < graph.size(); u++) {
            for (int v : graph.get(u)) {
                if (sccId[u] != sccId[v]) {
                    sccGraph.get(sccId[u]).add(sccId[v]);
                }
            }
        }
    }

    static long dfsSCC(int u) {
        if (dp[u] != 0) return dp[u];
        dp[u] = sccCoins[u];
        for (int v : sccGraph.get(u)) {
            dp[u] = Math.max(dp[u], sccCoins[u] + dfsSCC(v));
        }
        return dp[u];
    }
}
