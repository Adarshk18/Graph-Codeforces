import java.util.*;

public final  class Graph {
    static List<Integer>[] adj;
    static int[] color;
    static boolean isBipartite;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // number of vertices
        int m = sc.nextInt(); // number of edges
        
        adj = new ArrayList[n + 1];
        color = new int[n + 1];
        Arrays.fill(color, -1); // unvisited
        
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }
        
        int[][] edges = new int[m][2];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            edges[i][0] = u;
            edges[i][1] = v;
            adj[u].add(v);
            adj[v].add(u);
        }
        
        isBipartite = true;
        for (int i = 1; i <= n; i++) {
            if (color[i] == -1) {
                bfs(i);
            }
        }
        
        if (!isBipartite) {
            System.out.println("NO");
            return;
        }
        
        StringBuilder result = new StringBuilder();
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (color[u] == 0 && color[v] == 1) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        
        System.out.println("YES");
        System.out.println(result.toString());
    }
    
    static void bfs(int start) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        color[start] = 0; // start coloring with 0
        
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (color[v] == -1) {
                    color[v] = 1 - color[u]; // alternate colors
                    queue.add(v);
                } else if (color[v] == color[u]) {
                    isBipartite = false; // conflict found
                    return;
                }
            }
        }
    }
}
