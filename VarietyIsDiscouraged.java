import java.io.*;
import java.util.*;

public class VarietyIsDiscouraged {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int t = Integer.parseInt(br.readLine());

        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] a = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                a[i] = Integer.parseInt(st.nextToken());
            }

            // Check if removing no subarray is best
            Set<Integer> unique = new HashSet<>();
            for (int num : a) {
                unique.add(num);
            }
            if (unique.size() == n) {
                pw.println(0);
                continue;
            }

            // Sliding window approach
            Map<Integer, Integer> freq = new HashMap<>();
            int distinct = 0;
            int minLength = n;
            int bestL = 1, bestR = n;

            // Count frequencies of all elements
            for (int num : a) {
                freq.put(num, freq.getOrDefault(num, 0) + 1);
            }

            // Try removing subarray from both sides
            for (int left = 0; left < n; left++) {
                // Reduce frequency from the left
                freq.put(a[left], freq.get(a[left]) - 1);
                if (freq.get(a[left]) == 0) {
                    freq.remove(a[left]);
                }

                // Shrink from the right while maintaining duplicates
                int right = n - 1;
                while (right >= 0 && (freq.containsKey(a[right]) && freq.get(a[right]) > 1)) {
                    freq.put(a[right], freq.get(a[right]) - 1);
                    if (freq.get(a[right]) == 0) {
                        freq.remove(a[right]);
                    }
                    right--;
                }

                // Check length of the subarray removed
                int length = right - left + 1;
                if (length < minLength) {
                    minLength = length;
                    bestL = left + 1;
                    bestR = right + 1;
                }
            }

            // Print result
            pw.println(bestL + " " + bestR);
        }

        pw.flush();
        pw.close();
    }
}
