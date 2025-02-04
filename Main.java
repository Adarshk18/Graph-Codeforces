import java.io.*;
import java.util.*;

public class Main {
    
    static final long INF = (long) 1e9;
    static List<List<Integer>> g;
    
    public static void bfs(int s, long[] d) {
        Arrays.fill(d, INF);
        d[s] = 0;
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        
        while (!q.isEmpty()) {
            int node = q.poll();
            
            for (int child : g.get(node)) {
                if (d[child] == INF) {
                    d[child] = d[node] + 1;
                    q.add(child);
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int t = Integer.parseInt(br.readLine().trim());
        
        while (t-- > 0) {
            String[] nmabc = br.readLine().split(" ");
            int n = Integer.parseInt(nmabc[0]);
            int m = Integer.parseInt(nmabc[1]);
            int a = Integer.parseInt(nmabc[2]) - 1;
            int b = Integer.parseInt(nmabc[3]) - 1;
            int c = Integer.parseInt(nmabc[4]) - 1;

            long[] p = new long[m]; // Prices array
            String[] prices = br.readLine().split(" ");
            for (int i = 0; i < m; i++) {
                p[i] = Long.parseLong(prices[i]);
            }
            
            Arrays.sort(p);
            
            long[] pref = new long[m + 1];
            for (int i = 0; i < m; i++) {
                pref[i + 1] = pref[i] + p[i];
            }

            g = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                String[] xy = br.readLine().split(" ");
                int x = Integer.parseInt(xy[0]) - 1;
                int y = Integer.parseInt(xy[1]) - 1;

                g.get(x).add(y);
                g.get(y).add(x);
            }

            long[] da = new long[n];
            long[] db = new long[n];
            long[] dc = new long[n];

            bfs(a, da);
            bfs(b, db);
            bfs(c, dc);

            long ans = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (da[i] + db[i] + dc[i] <= m) {
                    ans = Math.min(ans, pref[(int) db[i]] + pref[(int) (da[i] + db[i] + dc[i])]);
                }
            }

            out.println(ans);
        }
        
        out.flush();
        out.close();
    }
}
