import java.io.*;
import java.util.*;

public class NegativeCycle {
    static class Edge {
        int from, to;
        long weight;

        public Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());
            edges.add(new Edge(a, b, c));
        }

        // Distance array
        long[] distance = new long[n + 1];
        Arrays.fill(distance, 0); // Initialize all distances to 0

        // Parent array to reconstruct the cycle
        int[] parent = new int[n + 1];
        Arrays.fill(parent, -1);

        int lastRelaxedNode = -1;

        // Relax all edges n times
        for (int i = 0; i < n; i++) {
            lastRelaxedNode = -1;
            for (Edge edge : edges) {
                if (distance[edge.from] + edge.weight < distance[edge.to]) {
                    distance[edge.to] = distance[edge.from] + edge.weight;
                    parent[edge.to] = edge.from;
                    lastRelaxedNode = edge.to;
                }
            }
        }

        if (lastRelaxedNode == -1) {
            pw.println("NO"); // No negative cycle
        } else {
            // Reconstruct the cycle
            int node = lastRelaxedNode;
            for (int i = 0; i < n; i++) {
                node = parent[node]; // Move back n steps to ensure we are in the cycle
            }

            List<Integer> cycle = new ArrayList<>();
            for (int v = node;; v = parent[v]) {
                cycle.add(v);
                if (v == node && cycle.size() > 1) {
                    break;
                }
            }

            Collections.reverse(cycle);

            pw.println("YES");
            for (int v : cycle) {
                pw.print(v + " ");
            }
            pw.println();
        }

        pw.flush();
    }
}