import java.io.*;
import java.util.*;

public class PlanetQuery1 {
    static final int MAX_LOG = 30; // 2^30 is larger than 1e9

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] t = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            t[i] = Integer.parseInt(st.nextToken());
        }

        // Precompute the destinations for each planet after 2^i steps
        int[][] dp = new int[MAX_LOG + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[0][i] = t[i]; // After 2^0 = 1 step
        }

        for (int i = 1; i <= MAX_LOG; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][dp[i - 1][j]]; // After 2^i steps
            }
        }

        // Process queries
        for (int i = 0; i < q; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            int current = x;
            for (int j = MAX_LOG; j >= 0; j--) {
                if ((k & (1 << j)) != 0) { // If the j-th bit of k is set
                    current = dp[j][current];
                }
            }
            pw.println(current);
        }

        pw.close();
    }
}