import java.util.*;

public class CycleComponents {
    static final int MAXN = 200007;
    static boolean[] visited = new boolean[MAXN];
    static List<Integer>[] adj = new ArrayList[MAXN];
    static Stack<Integer> stack = new Stack<>();

    static {
        for (int i = 0; i < MAXN; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    static void dfs(int node) {
        visited[node] = true;
        for (int neighbor : adj[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor);
            }
        }
        stack.push(node);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // Number of nodes
        int m = sc.nextInt(); // Number of edges

        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                dfs(i);

                boolean isCycle = true;
                if (stack.size() > 2) {
                    while (!stack.isEmpty()) {
                        if (adj[stack.peek()].size() != 2) {
                            isCycle = false;
                            break;
                        }
                        stack.pop();
                    }

                    if (isCycle) {
                        count++;
                    }
                }

                while (!stack.isEmpty()) {
                    stack.pop();
                }
            }
        }

        System.out.println(count);
        sc.close();
    }
}
