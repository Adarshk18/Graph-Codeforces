import java.io.*;
import java.util.*;

class Tunnel {
    int from, to, weight;

    public Tunnel(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

public class MaxScoreGame {
    static final long INF = Long.MAX_VALUE / 2; // Prevent overflow

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken()); // Number of rooms
        int m = Integer.parseInt(st.nextToken()); // Number of tunnels

        List<Tunnel> tunnels = new ArrayList<>();
        boolean[] hasSelfLoop = new boolean[n + 1];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            tunnels.add(new Tunnel(a, b, x));

            // Mark self-loops
            if (a == b) {
                hasSelfLoop[a] = true;
            }
        }

        // Edge Case 1: If n = 1, the answer must be 0, no movement required.
        if (n == 1) {
            out.println(-1);
            out.flush();
            return;
        }

        // Bellman-Ford Algorithm to find the longest path
        long[] dist = new long[n + 1];
        Arrays.fill(dist, -INF);
        dist[1] = 0; // Start from room 1

        // Standard relaxation for (n - 1) times
        for (int i = 1; i <= n - 1; i++) {
            for (Tunnel t : tunnels) {
                if (dist[t.from] != -INF && dist[t.from] + t.weight > dist[t.to]) {
                    dist[t.to] = dist[t.from] + t.weight;
                }
            }
        }

        // Step 2: Detect positive cycles affecting room `n`
        boolean[] inCycle = new boolean[n + 1];

        for (int i = 1; i <= n - 1; i++) {
            for (Tunnel t : tunnels) {
                if (dist[t.from] != -INF && dist[t.from] + t.weight > dist[t.to]) {
                    dist[t.to] = INF; // Mark as potentially infinite
                    inCycle[t.to] = true;
                }
            }
        }

        // Step 3: BFS to check if a positive cycle affects `n`
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];

        for (int i = 1; i <= n; i++) {
            if (inCycle[i]) {
                queue.add(i);
                visited[i] = true;
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (node == n) {
                out.println(-1);
                out.flush();
                return;
            }
            for (Tunnel t : tunnels) {
                if (t.from == node && !visited[t.to]) {
                    visited[t.to] = true;
                    queue.add(t.to);
                }
            }
        }

        // If no infinite cycle affects `n`, print max score
        out.println(dist[n]);
        out.flush();
    }
}
