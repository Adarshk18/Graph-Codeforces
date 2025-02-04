import java.util.*;
import java.io.*;

public class BuildingRoads {
    static List<List<Integer>> adj;
    static BitSet vis;
    static List<Integer> representative;

    public static void dfs(int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        vis.set(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (int neighbor : adj.get(node)) {
                if (!vis.get(neighbor)) {
                    vis.set(neighbor);
                    stack.push(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        // Reading the edges and building the graph
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        vis = new BitSet(n + 1);
        representative = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (!vis.get(i)) {
                representative.add(i);
                dfs(i);
            }
        }

        int noOfConnected = representative.size() - 1;
        System.out.println(noOfConnected);

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < noOfConnected; i++) {
            output.append(representative.get(i)).append(" ").append(representative.get(i + 1)).append("\n");
        }
        System.out.print(output);
    }
}