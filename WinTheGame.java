import java.io.*;
import java.util.*;

public class WinTheGame {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Initialize adjacency list
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Read teleporters and build the graph
        int[] inDegree = new int[n + 1];
        int[] outDegree = new int[n + 1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            outDegree[a]++;
            inDegree[b]++;
        }

        // Check if the graph has an Eulerian Trail
        int start = 1;
        int end = n;
        boolean hasEulerianTrail = true;

        // Check in-degree and out-degree conditions
        int startCount = 0, endCount = 0;
        for (int i = 1; i <= n; i++) {
            if (outDegree[i] - inDegree[i] == 1) {
                startCount++;
                start = i;
            } else if (inDegree[i] - outDegree[i] == 1) {
                endCount++;
                end = i;
            } else if (inDegree[i] != outDegree[i]) {
                hasEulerianTrail = false;
                break;
            }
        }

        if (!hasEulerianTrail || startCount != 1 || endCount != 1) {
            out.write("IMPOSSIBLE\n");
            out.flush();
            return;
        }

        // Check if the graph is strongly connected (ignoring isolated vertices)
        boolean[] visited = new boolean[n + 1];
        dfs(start, visited, graph);

        for (int i = 1; i <= n; i++) {
            if (!visited[i] && (inDegree[i] > 0 || outDegree[i] > 0)) {
                out.write("IMPOSSIBLE\n");
                out.flush();
                return;
            }
        }

        // Find Eulerian Trail using Hierholzer's Algorithm
        Deque<Integer> stack = new ArrayDeque<>();
        List<Integer> path = new ArrayList<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int u = stack.peek();
            if (graph[u].size() > 0) {
                int v = graph[u].remove(graph[u].size() - 1);
                stack.push(v);
            } else {
                path.add(stack.pop());
            }
        }

        // Reverse the path to get the correct order
        Collections.reverse(path);

        // Check if the path uses all edges
        if (path.size() != m + 1) {
            out.write("IMPOSSIBLE\n");
        } else {
            for (int node : path) {
                out.write(node + " ");
            }
        }

        out.flush();
    }

    static void dfs(int node, boolean[] visited, List<Integer>[] graph) {
        visited[node] = true;
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor, visited, graph);
            }
        }
    }
}