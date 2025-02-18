import java.io.*;
import java.util.*;

public class CoinCollector {
    static List<List<Integer>> graph;
    static long[] dp;
    static boolean[] vis;
    static int[] coins;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        coins = new int[n+1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            coins[i] = Integer.parseInt(st.nextToken());
        }

        graph = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph.get(a).add(b);
        }

        dp = new long[n+1];
        vis = new boolean[n+1];
        for (int i = 1; i <= n; i++) {
            if(!vis[i]){
                dfs(i);
    
            }
        }


        long maxCoins = 0;
        for (int i = 1; i <= n; i++) {
            maxCoins = Math.max(maxCoins, dp[i]);
        }
        pw.println(maxCoins);
        pw.flush();
    }

    static void dfs(int rooms){
        vis[rooms] = true;
        dp[rooms] = coins[rooms];

        for (int v : graph.get(rooms)) {
            if(!vis[v]){
                dfs(v);
            }
            dp[rooms] = Math.max(dp[rooms], coins[rooms]+ dp[v]);
        }
    }
}
