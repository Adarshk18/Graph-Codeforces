import java.io.*;
import java.util.*;

public class PizzaToppings {
    static int n, m;
    static ArrayList<Integer>[] graph, reverseGraph;
    static int[] visited, component;
    static ArrayDeque<Integer> stack;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        graph = new ArrayList[2 * m + 1];
        reverseGraph = new ArrayList[2 * m + 1];
        for (int i = 1; i <= 2 * m; i++) {
            graph[i] = new ArrayList<>();
            reverseGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int u = getNode(st.nextToken(), Integer.parseInt(st.nextToken()));
            int v = getNode(st.nextToken(), Integer.parseInt(st.nextToken()));
            graph[negate(u)].add(v);
            graph[negate(v)].add(u);
            reverseGraph[v].add(negate(u));
            reverseGraph[u].add(negate(v));
        }

        visited = new int[2 * m + 1];
        component = new int[2 * m + 1];
        Arrays.fill(component, -1);
        stack = new ArrayDeque<>();

        for (int i = 1; i <= 2 * m; i++) {
            if (visited[i] == 0) dfs(i);
        }

        int comp = 0;
        while (!stack.isEmpty()) {
            int u = stack.pollLast();
            if (component[u] == -1) dfsReverse(u, comp++);
        }

        for (int i = 1; i <= m; i++) {
            if (component[i] == component[i + m]) {
                pw.println("IMPOSSIBLE");
                pw.flush();
                return;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= m; i++) {
            result.append(component[i] > component[i + m] ? '+' : '-');
        }
        pw.println(result);
        pw.flush();
    }

    static int getNode(String sign, int x) {
        return sign.equals("+") ? x : x + m;
    }

    static int negate(int u) {
        return u <= m ? u + m : u - m;
    }

    static void dfs(int u) {
        visited[u] = 1;
        ArrayList<Integer> adj = graph[u];
        for (int v : adj) {
            if (visited[v] == 0) dfs(v);
        }
        stack.add(u);
    }

    static void dfsReverse(int u, int comp) {
        component[u] = comp;
        ArrayList<Integer> adj = reverseGraph[u];
        for (int v : adj) {
            if (component[v] == -1) dfsReverse(v, comp);
        }
    }
}
