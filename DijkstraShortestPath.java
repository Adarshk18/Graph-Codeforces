import java.util.*;

public class DijkstraShortestPath {
    static class Edge {
        int to, weight;
        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // number of vertices
        int m = sc.nextInt(); // number of edges

        // Adjacency list representation of the graph
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Read the edges
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph.get(u).add(new Edge(v, w));
            graph.get(v).add(new Edge(u, w)); // undirected graph
        }

        // Dijkstra's algorithm
        long[] dist = new long[n + 1];
        int[] parent = new int[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[1] = 0;
        parent[1] = -1;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingLong(e -> e.weight));
        pq.add(new Edge(1, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.to;
            int currentDist = current.weight;

            if (currentDist > dist[u]) continue;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    parent[v] = u;
                    pq.add(new Edge(v, (int) dist[v]));
                }
            }
        }

        // If there is no path
        if (dist[n] == Long.MAX_VALUE) {
            System.out.println("-1");
            return;
        }

        // Reconstruct the path
        List<Integer> path = new ArrayList<>();
        for (int v = n; v != -1; v = parent[v]) {
            path.add(v);
        }
        Collections.reverse(path);

        // Print the path
        for (int vertex : path) {
            System.out.print(vertex + " ");
        }
    }
}
