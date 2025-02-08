import java.util.*;
import java.io.*;

public class ShortestPath1 {
    static class Pair implements Comparable<Pair> {
        int city;
        long cost;

        public Pair(int city, long cost) {
            this.city = city;
            this.cost = cost;
        }

        @Override
        public int compareTo(Pair other) {
            return Long.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter p = new PrintWriter(System.out);
        StringTokenizer str = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(str.nextToken());
        int m = Integer.parseInt(str.nextToken());

        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            str = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(str.nextToken());
            int b = Integer.parseInt(str.nextToken());
            long c =  Long.parseLong(str.nextToken());

            adj.get(a).add(new Pair(b, c));
        }

        // implement dijkstra
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(1, 0));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int node = curr.city;
            long cost = curr.cost;

            if (cost > dist[node])
                continue;

            for (Pair it : adj.get(node)) {
                int nextNode = it.city;
                long newCost = cost + it.cost;

                if (newCost < dist[nextNode]) {
                    dist[nextNode] = newCost;
                    pq.add(new Pair(nextNode,newCost));
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            p.print(dist[i] + " ");
        }
        p.println();
        p.flush();
    }
}


