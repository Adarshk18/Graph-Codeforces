import java.util.*;

public class Planets {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int id;
        long time;
        Node(int id, long time) {
            this.id = id;
            this.time = time;
        }
        public int compareTo(Node other) {
            return Long.compare(this.time, other.time);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // Number of planets
        int m = sc.nextInt(); // Number of stargate connections

        // Graph representation
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Read stargate connections
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            graph.get(a).add(new Edge(b, c));
            graph.get(b).add(new Edge(a, c));
        }

        // Read arrival times of other travelers for each planet
        List<Set<Long>> arrivals = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            arrivals.add(new HashSet<>());
        }

        for (int i = 1; i <= n; i++) {
            int k = sc.nextInt(); // Number of arrival times for planet i
            for (int j = 0; j < k; j++) {
                long t = sc.nextLong();
                arrivals.get(i).add(t);
            }
        }

        // Dijkstra's algorithm
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;
            long time = current.time;

            // If we reach planet n, print the time and exit
            if (u == n) {
                System.out.println(time);
                return;
            }

            // Skip if we've already found a better time for this planet
            if (time > dist[u]) continue;

            // Explore all neighboring planets
            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                long travelTime = edge.weight;
                long arrivalTime = time + travelTime;

                // Handle waiting times due to other travelers
                long nextAvailableTime = arrivalTime;
                while (arrivals.get(v).contains(nextAvailableTime)) {
                    nextAvailableTime++;
                }

                // Update the distance if we found a better time
                if (nextAvailableTime < dist[v]) {
                    dist[v] = nextAvailableTime;
                    pq.add(new Node(v, nextAvailableTime));
                }
            }
        }

        // If we couldn't reach planet n, print -1
        System.out.println(-1);
    }
}