import java.util.*;

public class A {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt(); // Number of test cases
        while (t-- > 0) {
            int n = sc.nextInt();
            int[] b = new int[n - 2];
            for (int i = 0; i < n - 2; i++) {
                b[i] = sc.nextInt();
            }

            System.out.println(canConstructArray(b, n) ? "YES" : "NO");
        }
        sc.close();
    }

    static boolean canConstructArray(int[] b, int n) {
        // Start constructing the array a
        int[] a = new int[n];
        Arrays.fill(a, 1); // Fill with initial value

        for (int i = 0; i < b.length; i++) {
            if (b[i] == 1) {
                // a[i+1] must match a[i] and a[i+2]
                a[i + 1] = a[i];
                a[i + 2] = a[i];
            } else {
                // a[i+1] must differ from a[i] or a[i+2]
                if (a[i + 1] == a[i]) {
                    a[i + 1] = a[i] + 1; // Assign a different value
                }
            }
        }

        // Check if the constructed array matches the given pattern
        for (int i = 1; i < n - 1; i++) {
            if ((a[i] == a[i - 1] && a[i] == a[i + 1]) != (b[i - 1] == 1)) {
                return false; // Pattern mismatch
            }
        }
        return true; // Pattern matches
    }
}
