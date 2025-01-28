import java.util.Scanner;

public class GregAndGraph {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        //adj matrix
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = in.nextInt();
            }
        }

        //deletion order
        int[] deleteOrder = new int[n];
        for (int i = 0; i < n; i++) {
            deleteOrder[i] = in.nextInt()-1;
        }

        //addition order which is reverse of deleteOrder
        int[] addOrder = new int[n];
        for (int i = 0; i <n; i++) {
            addOrder[i] = deleteOrder[n-1-i];
        }

        long[] result = new long[n];

        //iterate over addOrder
        for (int k = 0; k < n; k++) {
            int newVertex = addOrder[k];

            //update shortest path using floyd warshall
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(dist[i][newVertex]!= Integer.MAX_VALUE && dist[newVertex][j]!=Integer.MAX_VALUE){
                        dist[i][j] = Math.min(dist[i][j], dist[i][newVertex]+dist[newVertex][j]);
                    }
                }
            }

            long sum=0;
            for (int i = 0; i <= k; i++) {
                for (int j = 0; j <= k; j++) {
                    sum += dist[addOrder[i]][addOrder[j]];
                }
            }
            result[n-1-k] = sum;
        }
        for (long res: result) {
            System.out.print(res + " ");
        }
    }
}