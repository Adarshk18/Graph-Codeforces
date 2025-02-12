import java.io.*;
import java.util.*;

public class GameLevels {
    static List<List<Integer>> adj;
    static int[] dp;
    static int MOD = 1000000007;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adj.get(a).add(b); // Directed graph
        }

        dp = new int[n + 1];
        Arrays.fill(dp, 0);
        dp[1] = 1; // There's one way to be at the starting level

        // Perform topological sorting using BFS (Kahn's algorithm)
        int[] inDegree = new int[n + 1];
        for (int u = 1; u <= n; u++) {
            for (int v : adj.get(u)) {
                inDegree[v]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int u = 1; u <= n; u++) {
            if (inDegree[u] == 0) {
                queue.add(u);
            }
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                dp[v] = (dp[v] + dp[u]) % MOD; // Add the ways from u to v
                inDegree[v]--;
                if (inDegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        // Output the number of ways to reach level n
        pw.println(dp[n]);

        pw.flush();
        pw.close();
    }
}