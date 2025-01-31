import java.util.*;

public class UnstableStringSort {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read input values
        int n = sc.nextInt();
        int k = sc.nextInt();
        
        int[] p = new int[n];
        int[] q = new int[n];
        
        for (int i = 0; i < n; i++) {
            p[i] = sc.nextInt() - 1;
        }
        for (int i = 0; i < n; i++) {
            q[i] = sc.nextInt() - 1;
        }
        
        sc.close();
        
        // Solve the problem
        String result = solve(n, k, p, q);
        
        // Output the result
        System.out.println(result);
    }
    
    public static String solve(int n, int k, int[] p, int[] q) {
        int[] label = new int[n];
        Arrays.fill(label, -1);
        
        int currentLabel = 0;
        
        // Assign labels based on first permutation `p`
        for (int i = 0; i < n; i++) {
            if (i > 0 && p[i] > p[i - 1]) {
                label[p[i]] = label[p[i - 1]];
            } else {
                label[p[i]] = currentLabel++;
            }
        }
        
        // Ensure labels are consistent with the second permutation `q`
        for (int i = 0; i < n; i++) {
            if (i > 0 && q[i] > q[i - 1]) {
                label[q[i]] = Math.max(label[q[i]], label[q[i - 1]]);
            }
        }
        
        // Check if we have enough distinct labels
        int maxLabel = Arrays.stream(label).max().orElse(0);
        if (maxLabel + 1 < k) {
            return "NO";
        }
        
        // Generate the output string
        char[] s = new char[n];
        for (int i = 0; i < n; i++) {
            s[i] = (char) ('a' + Math.min(label[i], k - 1));
        }
        
        return "YES\n" + new String(s);
    }
}
