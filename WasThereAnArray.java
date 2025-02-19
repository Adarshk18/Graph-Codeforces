import java.io.*;
import java.util.*;

public class WsT {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine().trim());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            st = new StringTokenizer(br.readLine().trim());

            int[] b = new int[n - 2];
            for (int i = 0; i < n - 2; i++) {
                b[i] = Integer.parseInt(st.nextToken());
            }

            out.println(canFormArray(b, n) ? "YES" : "NO");
        }
        out.close();
    }

    static boolean canFormArray(int[] b, int n) {
        // Since we only know b (not a), we try to construct a from b.
        // We'll simulate a possible array a by following the constraints from b.

        int[] a = new int[n];
        a[0] = 1; // Start with some value, as arrays can be shifted/scaled arbitrarily
        a[1] = 1;

        for (int i = 2; i < n - 1; i++) {
            if (b[i - 1] == 1) {
                // a[i] must match its neighbors
                a[i] = a[i - 1];
            } else {
                // a[i] must differ from at least one neighbor
                a[i] = a[i - 1] + 1;
            }
        }

        // Validate that the generated array matches b exactly
        for (int i = 1; i < n - 1; i++) {
            boolean equalsNeighbors = (a[i] == a[i - 1] && a[i] == a[i + 1]);
            if ((equalsNeighbors ? 1 : 0) != b[i - 1]) {
                return false;
            }
        }

        return true;
    }
}
