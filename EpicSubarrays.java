import java.util.*;

public class EpicSubarrays {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }
            System.out.println(countEpicSubarrays(a, n, k));
        }
        sc.close();
    }

    private static int countEpicSubarrays(int[] a, int n, int k) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            int minLeft = a[i];
            int maxRight = a[i];
            for (int j = i; j < n; j++) {
                if (a[j] < minLeft) minLeft = a[j];
                if (a[j] > maxRight) maxRight = a[j];
                if (j > i && minLeft + maxRight == k) {
                    count++;
                }
            }
        }
        return count;
    }
}