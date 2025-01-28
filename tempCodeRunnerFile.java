import java.util.*;

public class G{
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        long[][] dist = new long[n][n];
        int[] order = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = in.nextLong();
            }
        }

        for (int i = 0; i < n; i++) {
            order[i] = in.nextInt()-1;
        }

        long[] result = new long[n];
        boolean[] active = new boolean[n];

        for (int step = n-1; step >= 0; step--) {
            int k = order[step];
            active[k] = true;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(active[i] && active[j]){
                        dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                    }
                }
            }

            long sum =0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(active[i] && active[j]){
                        sum += dist[i][j];
                    }
                }
            }
            result[step] = sum;
        }

        for(long results: result){
            System.out.print(results + " ");
        }
    }
}