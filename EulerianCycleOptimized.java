import java.io.*;
import java.util.*;

public class EulerianCycleFast {
    static int n, m;
    static int[] head, nxt, to, cur, deg;
    static boolean[] mark;
    static List<Integer> path = new ArrayList<>();
    static int cnt = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        head = new int[n + 1];
        nxt = new int[2 * (m + 1)];
        to = new int[2 * (m + 1)];
        cur = new int[n + 1];
        deg = new int[n + 1];
        mark = new boolean[2 * (m + 1)];

        Arrays.fill(head, -1);

        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            deg[u]++;
            deg[v]++;
            addEdge(u, v);
            addEdge(v, u);
        }

        for (int i = 1; i <= n; i++) {
            cur[i] = head[i];
            if ((deg[i] & 1) == 1) { // If degree is odd
                out.println("IMPOSSIBLE");
                out.close();
                return;
            }
        }

        iterativeDFS(1);

        if (path.size() == m + 1) {
            for (int node : path) {
                out.print(node + " ");
            }
        } else {
            out.println("IMPOSSIBLE");
        }

        out.close();
    }

    static void addEdge(int u, int v) {
        nxt[cnt] = head[u];
        head[u] = cnt;
        to[cnt] = v;
        cnt++;
    }

    static void iterativeDFS(int start) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.peek();
            boolean foundEdge = false;

            for (int i = cur[node]; i != -1; i = nxt[i]) {
                cur[node] = nxt[i];
                if (!mark[i]) {
                    mark[i] = true;
                    mark[i ^ 1] = true;
                    stack.push(to[i]);
                    foundEdge = true;
                    break;
                }
            }

            if (!foundEdge) {
                path.add(stack.pop());
            }
        }
    }
}
