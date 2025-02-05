import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GreedyMerchants {
    static int n, m, k, timer;
    static List<Integer>[] graph;
    static int[] tin, low;
    static boolean[] isBridge;
    static List<int[]> bridges;
    static int[] component;
    static List<Integer>[] tree;
    static int compCount;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer str = new StringTokenizer(br.readLine());

        n = Integer.parseInt(str.nextToken());
        m = Integer.parseInt(str.nextToken());

        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            graph[i] = new ArrayList<>();

        tin = new int[n + 1];
        low = new int[n + 1];
        Arrays.fill(tin, -1);
        Arrays.fill(low, -1);

        bridges = new ArrayList<>();
        isBridge = new boolean[m];
        Map<String, Integer> edgeIndex = new HashMap<>();

        // Read roads
        for (int i = 0; i < m; i++) {
            str = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(str.nextToken()), v = Integer.parseInt(str.nextToken());
            graph[u].add(v);
            graph[v].add(u);
            edgeIndex.put(u + "," + v, i);
            edgeIndex.put(v + "," + u, i);
        }

        // Step 1: Find Bridges (Tarjan's Algorithm)
        timer = 0;
        for (int i = 1; i <= n; i++) {
            if (tin[i] == -1)
                findBridges(i, -1);
        }

        // Step 2: Build Component Graph
        component = new int[n + 1];
        Arrays.fill(component, -1);
        compCount = 0;

        for (int i = 1; i <= n; i++) {
            if (component[i] == -1) {
                compCount++;
                dfsComponent(i, compCount);
            }
        }

        tree = new ArrayList[compCount + 1];
        for (int i = 1; i <= compCount; i++)
            tree[i] = new ArrayList<>();

        for (int[] bridge : bridges) {
            int c1 = component[bridge[0]];
            int c2 = component[bridge[1]];
            tree[c1].add(c2);
            tree[c2].add(c1);
        }

        // Step 3: Read Merchants and Find Important Roads
        str = new StringTokenizer(br.readLine()); // Read a new line for k
        k = Integer.parseInt(str.nextToken());

        for (int i = 0; i < k; i++) {
            str = new StringTokenizer(br.readLine()); 
            int s = Integer.parseInt(str.nextToken()), l = Integer.parseInt(str.nextToken());
            int c1 = component[s], c2 = component[l];

            if (c1 == c2) {
                System.out.println(0);
            } else {
                System.out.println(countBridges(c1, c2));
            }
        }
    }

    // Tarjan's Algorithm to find bridges
    static void findBridges(int node, int parent) {
        tin[node] = low[node] = timer++;
        for (int neighbor : graph[node]) {
            if (neighbor == parent)
                continue;
            if (tin[neighbor] == -1) {
                findBridges(neighbor, node);
                low[node] = Math.min(low[node], low[neighbor]);
                if (low[neighbor] > tin[node]) {
                    bridges.add(new int[] { node, neighbor });
                }
            } else {
                low[node] = Math.min(low[node], tin[neighbor]);
            }
        }
    }

    // DFS to create component graph
    static void dfsComponent(int node, int comp) {
        component[node] = comp;
        for (int neighbor : graph[node]) {
            if (component[neighbor] == -1 && !isBridgeEdge(node, neighbor)) {
                dfsComponent(neighbor, comp);
            }
        }
    }

    static boolean isBridgeEdge(int u, int v) {
        for (int[] bridge : bridges) {
            if ((bridge[0] == u && bridge[1] == v) || (bridge[0] == v && bridge[1] == u))
                return true;
        }
        return false;
    }

    // BFS to count bridges in path
    static int countBridges(int c1, int c2) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> dist = new HashMap<>();
        queue.add(c1);
        dist.put(c1, 0);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == c2)
                return dist.get(node);

            for (int neighbor : tree[node]) {
                if (!dist.containsKey(neighbor)) {
                    dist.put(neighbor, dist.get(node) + 1);
                    queue.add(neighbor);
                }
            }
        }
        return 0;
    }
}
