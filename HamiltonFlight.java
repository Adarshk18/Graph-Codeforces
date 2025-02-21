import java.io.*;
import java.util.*;

public class HamiltonFlight {
    static final int MOD = 1000000007;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Integer>[] adj = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
        }

        int[][] dp = new int[1<<n][n+1];
        dp[1<<(1-1)][1] = 1;

        for (int mask=0; mask<(1<<n); mask++) {
            for (int u = 0; u <= n; u++) {
                if((mask & (1<<(u-1)))==0) continue;


                for(int v: adj[u]){
                    if((mask & (1<<(v-1)))==0) continue;

                    dp[mask | (1<<(v-1))][v] = (dp[mask | (1<<(v-1))][v] + dp[mask][u])%MOD;
                }
            }
        }
        pw.println(dp[(1<<n)-1][n]);
        pw.flush();
    }
    
}
