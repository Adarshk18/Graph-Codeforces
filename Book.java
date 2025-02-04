import java.io.*;
import java.util.*;

public class Book {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }
            int[] inDegree = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int k = Integer.parseInt(st.nextToken());
                for (int j = 0; j < k; j++) {
                    int req = Integer.parseInt(st.nextToken());
                    graph.get(req).add(i);
                    inDegree[i]++;
                }
            }
            int[] depth = new int[n + 1];
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                if (inDegree[i] == 0) {
                    queue.add(i);
                    depth[i] = 1; // Initial depth for chapters with no prerequisites
                }
            }
            int count = 0;
            int maxPasses = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                count++;
                maxPasses = Math.max(maxPasses, depth[current]);
                for (int neighbor : graph.get(current)) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        queue.add(neighbor);
                        // Update depth based on the order of chapters
                        depth[neighbor] = depth[current] + (neighbor < current ? 1 : 0);
                    }
                }
            }
            if (count == n) {
                bw.write(maxPasses + "\n");
            } else {
                bw.write("-1\n");
            }
        }
        bw.flush();
        bw.close();
        br.close();
    }
}