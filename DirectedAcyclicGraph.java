import java.util.*;

public class DirectedAcyclicGraph {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt(); // Number of vertices
            int m = sc.nextInt(); // Number of edges
            
            List<int[]> undirectedEdges = new ArrayList<>();
            List<int[]> directedEdges = new ArrayList<>();
            Map<Integer, List<Integer>> adjList = new HashMap<>();
            int[] indegree = new int[n + 1];
            
            // Initialize adjacency list
            for (int i = 1; i <= n; i++) {
                adjList.put(i, new ArrayList<>());
            }
            
            for (int i = 0; i < m; i++) {
                int type = sc.nextInt();
                int u = sc.nextInt();
                int v = sc.nextInt();
                
                if (type == 1) {
                    // Directed edge
                    adjList.get(u).add(v);
                    indegree[v]++;
                    directedEdges.add(new int[]{u, v});
                } else {
                    // Undirected edge
                    undirectedEdges.add(new int[]{u, v});
                }
            }
            
            // Topological Sort using Kahn's Algorithm
            Queue<Integer> queue = new LinkedList<>();
            List<Integer> topoOrder = new ArrayList<>();
            
            for (int i = 1; i <= n; i++) {
                if (indegree[i] == 0) {
                    queue.add(i);
                }
            }
            
            while (!queue.isEmpty()) {
                int node = queue.poll();
                topoOrder.add(node);
                
                for (int neighbor : adjList.get(node)) {
                    indegree[neighbor]--;
                    if (indegree[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            
            // If topological sort doesn't cover all nodes, there is a cycle
            if (topoOrder.size() != n) {
                System.out.println("NO");
                continue;
            }
            
            // Map nodes to their position in the topological order
            Map<Integer, Integer> position = new HashMap<>();
            for (int i = 0; i < topoOrder.size(); i++) {
                position.put(topoOrder.get(i), i);
            }
            
            // Direct undirected edges based on topological order
            List<int[]> resultEdges = new ArrayList<>(directedEdges);
            for (int[] edge : undirectedEdges) {
                int u = edge[0], v = edge[1];
                if (position.get(u) < position.get(v)) {
                    resultEdges.add(new int[]{u, v});
                } else {
                    resultEdges.add(new int[]{v, u});
                }
            }
            
            // Output result
            System.out.println("YES");
            for (int[] edge : resultEdges) {
                System.out.println(edge[0] + " " + edge[1]);
            }
        }
        
        sc.close();
    }
}
