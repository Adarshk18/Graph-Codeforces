import java.io.*;
import java.util.*;

public class Investigation {
    static final int MOD = 1000000007;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adj.get(a).add(new Edge(b, c));
        }

        long[] dist = new long[n+1];
        int[] count = new int[n+1];
        int[] minFlight = new int[n+1];
        int[] maxFlight = new int[n+1];

        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;
        count[1] = 1;
        minFlight[1] =0;
        maxFlight[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingLong(node -> node.dist));
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.id;
            long d = node.dist;

            if(d>dist[u]) continue;

            for (Edge edge : adj.get(u)) {
                int v = edge.to;
                long w = edge.weight;

                if(dist[v]>dist[u]+w){
                    dist[v] = dist[u] + w;
                    count[v] = count[u];
                    minFlight[v] = minFlight[u]+1;
                    maxFlight[v] = maxFlight[u]+1;
                    pq.add(new Node(v, dist[v]));
                }else if(dist[v] == dist[u]+w){
                    count[v] = (count[v] + count[u]) % MOD;
                    minFlight[v] = Math.min(minFlight[v], minFlight[u]+1);
                    maxFlight[v] = Math.max(maxFlight[v], maxFlight[u]+1);
                }
            }

        }
        pw.println(dist[n] + " " + count[n] + " " +  minFlight[n] + " " + maxFlight[n]);
        pw.close();
    }

    static class Edge{
        int to;
        long weight;

        Edge(int to, long weight){
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node{
        int id;
        long dist;

        Node(int id, long dist){
            this.id = id;
            this.dist = dist;
        }
    }
}
