import java.io.*;
import java.util.*;

public class FlightRoutes {
    static final int MX = 200005;
    static int n, m, k;
    static List<List<Pair>> adj = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int t = Integer.parseInt(st.nextToken());
        while (t-- > 0) {
            solve(br, out);
        }
        out.flush();
        out.close();
        br.close();
    }

    static void solve(BufferedReader br, PrintWriter out) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        adj.clear();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adj.get(a).add(new Pair(b, w));
        }

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.offer(new Pair(1, 0));
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[1] = 0;

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int d = curr.weight, u = curr.node;
            if (d > dist[u]) continue;
            for (Pair p : adj.get(u)) {
                int v = p.node, w = p.weight;
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new Pair(v, dist[v]));
                }
            }
        }
        
        Arrays.sort(dist, 1, n + 1);
        for (int i = 1; i <= k && i <= n; i++) {
            out.print(dist[i] + " ");
        }
        out.println();
    }

    static class Pair implements Comparable<Pair> {
        int node, weight;
        Pair(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
        public int compareTo(Pair o) {
            return Integer.compare(this.weight, o.weight);
        }
    }
}
