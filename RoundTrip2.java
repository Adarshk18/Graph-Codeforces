import java.util.*;
import java.io.*;


public class RoundTrip2 {
    static List<List<Integer>> adj;
    static int[] parent;
    static boolean[] vis;
    static boolean[] inStack;
    static List<Integer> cycle;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adj.get(a).add(b);
        }
        parent = new int[n+1];
        vis = new boolean[n+1];
        inStack = new boolean[n+1];
        cycle = new ArrayList<>();

        boolean found = false;
        for(int i=1; i<=n; i++){
            if(!vis[i] && dfs(i)){
                found = true;
                break;
            }
        }

        if(found){
            pw.println(cycle.size());
            for(int city: cycle){
                pw.print(city + " ");
            }
            pw.println();
        }else{
            pw.print("IMPOSSIBLE");
        }
        pw.flush();
    }

    static boolean dfs(int start){
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int u = stack.peek();

            if (!vis[u]) {
                vis[u] = true;
                inStack[u] = true;

                for(int v: adj.get(u)){
                    if (!vis[v]) {
                        parent[v] = u;
                        stack.push(v);
                    } else if (inStack[v]) {
                        // Cycle detected
                        cycle.add(v);
                        for (int x = u; x != v; x = parent[x]) {
                            cycle.add(x);
                        }
                        cycle.add(v);
                        Collections.reverse(cycle);
                        return true;
                    }
                }
            }else {
                inStack[u] = false;
                stack.pop();
            }
        }
        return false;
    }
}
