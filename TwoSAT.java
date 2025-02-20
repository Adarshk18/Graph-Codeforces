import java.io.*;
import java.util.*;

public class TwoSAT {
    static List<Integer>[] adj, adj_t;
    static Stack<Integer> topo = new Stack<>();
    static int[] colors;
    static boolean[] visited;
    
    static void dfs1(int node) {
        visited[node] = true;
        for (int child : adj[node]) {
            if (!visited[child]) {
                dfs1(child);
            }
        }
        topo.push(node);
    }

    static void dfs2(int node, int color) {
        visited[node] = true;
        colors[node] = color;
        for (int child : adj_t[node]) {
            if (!visited[child]) {
                dfs2(child, color);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        adj = new ArrayList[2 * n];
        adj_t = new ArrayList[2 * n];
        colors = new int[2 * n];
        visited = new boolean[2 * n];

        for (int i = 0; i < 2 * n; i++) {
            adj[i] = new ArrayList<>();
            adj_t[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            char c1 = st.nextToken().charAt(0);
            int x1 = Integer.parseInt(st.nextToken()) - 1;
            char c2 = st.nextToken().charAt(0);
            int x2 = Integer.parseInt(st.nextToken()) - 1;

            int b1 = (c1 == '+') ? 1 : 0;
            int b2 = (c2 == '+') ? 1 : 0;
            
            // Construct the implication graph
            adj[n * (1 - b1) + x1].add(n * b2 + x2);
            adj_t[n * b2 + x2].add(n * (1 - b1) + x1);
            adj[n * (1 - b2) + x2].add(n * b1 + x1);
            adj_t[n * b1 + x1].add(n * (1 - b2) + x2);
        }

        // First DFS pass to fill the stack
        Arrays.fill(visited, false);
        for (int i = 0; i < 2 * n; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }

        // Second DFS pass to find SCCs
        Arrays.fill(visited, false);
        int color = 0;
        while (!topo.isEmpty()) {
            int node = topo.pop();
            if (!visited[node]) {
                dfs2(node, color++);
            }
        }

        // Determine assignment
        char[] ans = new char[n];
        for (int i = 0; i < n; i++) {
            if (colors[i] == colors[i + n]) {
                pw.println("IMPOSSIBLE");
                pw.flush();
                return;
            }
            ans[i] = (colors[i] > colors[i + n]) ? '-' : '+';
        }

        for (char c : ans) {
            pw.print(c + " ");
        }
        pw.println();
        pw.flush();
    }
}
