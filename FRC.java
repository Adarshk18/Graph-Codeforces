import java.io.*;
import java.util.*;
 
public class FRC {
    static List<Integer>[] graph, reverseGraph;
    static boolean[] vis;
    static Stack<Integer> stack;
    static List<List<Integer>> scc;

    static void dfs(int u) {
        vis[u] = true;
        for (int v : graph[u]) {
            if (!vis[v]) dfs(v);
        }
        stack.push(u);
    }

    static void reverseDfs(int u, List<Integer> component) {
        vis[u] = true;
        component.add(u);
        for (int v : reverseGraph[u]) {
            if (!vis[v]) reverseDfs(v, component);
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        graph = new ArrayList[n + 1];
        reverseGraph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            reverseGraph[b].add(a);
        }

        vis = new boolean[n + 1];
        stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            if (!vis[i]) dfs(i);
        }

        vis = new boolean[n + 1];
        scc = new ArrayList<>();
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!vis[u]) {
                List<Integer> component = new ArrayList<>();
                reverseDfs(u, component);
                scc.add(component);
            }
        }

        if (scc.size() == 1) {
            pw.println("YES");
        } else {
            pw.println("NO");
            int a = scc.get(0).get(0);
            int b = scc.get(1).get(0);
            pw.println(b + " " + a); // Swap output order for correct output
        }
        pw.flush();
    }
}
