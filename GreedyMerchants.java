import java.util.*;

public class GreedyMerchants {
    static int n, m;
    static List<Integer>[] graph;
    static List<int[]> edges;
    static boolean[] visited;
    static int[] tin, low;
    static int timer = 0;
    static Set<String> bridges = new HashSet<>();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        graph = new ArrayList[n + 1];
        edges = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int a = sc.nextInt(), b = sc.nextInt();
            graph[a].add(b);
            graph[b].add(a);
            edges.add(new int[]{a, b});
        }

        // Step 1: Find Bridges using Tarjan's Algorithm
        findBridges();

        int k = sc.nextInt();
        for (int i = 0; i < k; i++) {
            int s = sc.nextInt(), l = sc.nextInt();
            System.out.println(countImportantRoads(s, l));
        }

        sc.close();
    }

    // Tarjan's Algorithm to find Bridges
    static void findBridges() {
        visited = new boolean[n + 1];
        tin = new int[n + 1];
        low = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i, -1);
            }
        }
    }

    static void dfs(int node, int parent) {
        visited[node] = true;
        tin[node] = low[node] = timer++;
        
        for (int neighbor : graph[node]) {
            if (neighbor == parent) continue;
            if (visited[neighbor]) {
                low[node] = Math.min(low[node], tin[neighbor]);
            } else {
                dfs(neighbor, node);
                low[node] = Math.min(low[node], low[neighbor]);
                if (low[neighbor] > tin[node]) {
                    // Edge (node, neighbor) is a bridge
                    bridges.add(node + "," + neighbor);
                    bridges.add(neighbor + "," + node);
                }
            }
        }
    }

    // Count important roads (bridges) on the path from warehouse to shop
    static int countImportantRoads(int s, int l) {
        // BFS/DFS to find the path and count bridge edges
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        Map<Integer, Integer> parent = new HashMap<>();
        queue.add(s);
        visited[s] = true;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr == l) break;
            for (int neighbor : graph[curr]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent.put(neighbor, curr);
                    queue.add(neighbor);
                }
            }
        }

        // Trace the path from l back to s and count bridges
        int count = 0;
        int curr = l;
        while (parent.containsKey(curr)) {
            int prev = parent.get(curr);
            if (bridges.contains(curr + "," + prev)) {
                count++;
            }
            curr = prev;
        }

        return count;
    }
}
