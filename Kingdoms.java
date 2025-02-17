import java.io.*;
import java.util.*;

public class Kingdoms {
    static List<Integer>[] graph, reverseGraph;
    static boolean[] visited;
    static Deque<Integer> stack;
    static int[] kingdomLabels;
    static int kingdomCount;

    static void dfs(int start) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = true;
        while (!stack.isEmpty()) {
            int u = stack.peek();
            boolean hasUnvisited = false;
            for (int v : graph[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    stack.push(v);
                    hasUnvisited = true;
                    break;
                }
            }
            if (!hasUnvisited) {
                stack.pop();
                Kingdoms.stack.push(u);
            }
        }
    }

    static void reverseDfs(int start, int label) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = true;
        while (!stack.isEmpty()) {
            int u = stack.pop();
            kingdomLabels[u] = label;
            for (int v : reverseGraph[u]) {
                if (!visited[v]) {
                    visited[v] = true;
                    stack.push(v);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Edge case: Single planet
        if (n == 1) {
            sb.append(1).append("\n1\n");
            System.out.print(sb);
            return;
        }

        graph = new ArrayList[n + 1];
        reverseGraph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            reverseGraph[b].add(a);
        }

        visited = new boolean[n + 1];
        stack = new ArrayDeque<>();

        // First pass DFS to record finishing times
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }

        // Second pass DFS on reversed graph
        visited = new boolean[n + 1];
        kingdomLabels = new int[n + 1];
        kingdomCount = 0;

        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                kingdomCount++;
                reverseDfs(u, kingdomCount);
            }
        }

        // Print results
        sb.append(kingdomCount).append("\n");
        for (int i = 1; i <= n; i++) {
            sb.append(kingdomLabels[i]).append(" ");
        }
        System.out.println(sb);
    }
}
