import java.util.*;

public class HowManyPaths {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            List<List<Integer>> adj = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                adj.add(new ArrayList<>());
            }
            for (int i = 0; i < m; i++) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                adj.get(a).add(b);
            }
            int[] pathCount = new int[n + 1];
            boolean[] visited = new boolean[n + 1];
            boolean[] inStack = new boolean[n + 1];
            boolean[] hasCycle = new boolean[n + 1];
            Arrays.fill(pathCount, 0);
            pathCount[1] = 1;
            dfs(1, adj, visited, inStack, hasCycle, pathCount);
            // Propagate infinite paths to all reachable vertices
            propagateInfinitePaths(1, adj, hasCycle, new boolean[n + 1]);
            StringBuilder sb = new StringBuilder();
            for (int v = 1; v <= n; v++) {
                if (!visited[v]) {
                    sb.append("0 ");
                } else if (hasCycle[v]) {
                    sb.append("-1 ");
                } else if (pathCount[v] == 1) {
                    sb.append("1 ");
                } else {
                    sb.append("2 ");
                }
            }
            System.out.println(sb.toString().trim());
        }
        sc.close();
    }

    private static void dfs(int u, List<List<Integer>> adj, boolean[] visited, boolean[] inStack, boolean[] hasCycle, int[] pathCount) {
        visited[u] = true;
        inStack[u] = true;
        for (int v : adj.get(u)) {
            if (!visited[v]) {
                pathCount[v] += pathCount[u];
                dfs(v, adj, visited, inStack, hasCycle, pathCount);
            } else if (inStack[v]) {
                hasCycle[v] = true;
            } else {
                pathCount[v] += pathCount[u];
            }
            if (hasCycle[v]) {
                hasCycle[u] = true;
            }
        }
        inStack[u] = false;
    }

    private static void propagateInfinitePaths(int u, List<List<Integer>> adj, boolean[] hasCycle, boolean[] propagated) {
        if (propagated[u]) return;
        propagated[u] = true;
        if (hasCycle[u]) {
            for (int v : adj.get(u)) {
                hasCycle[v] = true;
                propagateInfinitePaths(v, adj, hasCycle, propagated);
            }
        }
    }
}