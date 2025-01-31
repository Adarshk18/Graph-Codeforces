import java.util.*;

public class BookReading {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int t = sc.nextInt(); // Number of test cases
        while (t-- > 0) {
            int n = sc.nextInt(); // Number of chapters
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
            }

            int[] inDegree = new int[n + 1]; // In-degree of each chapter
            for (int i = 1; i <= n; i++) {
                int k = sc.nextInt(); // Number of prerequisites
                for (int j = 0; j < k; j++) {
                    int prerequisite = sc.nextInt();
                    graph.get(prerequisite).add(i); // Add edge from prerequisite to chapter
                    inDegree[i]++;
                }
            }

            // Perform BFS to assign layers
            Queue<Integer> queue = new LinkedList<>();
            int[] layer = new int[n + 1]; // Layer of each chapter
            for (int i = 1; i <= n; i++) {
                if (inDegree[i] == 0) {
                    queue.add(i);
                    layer[i] = 1; // First layer
                }
            }

            int maxLayer = 0;
            int processed = 0;
            while (!queue.isEmpty()) {
                int current = queue.poll();
                processed++;
                maxLayer = Math.max(maxLayer, layer[current]);

                for (int neighbor : graph.get(current)) {
                    inDegree[neighbor]--;
                    if (inDegree[neighbor] == 0) {
                        // Assign the layer of the neighbor
                        layer[neighbor] = layer[current];
                        if (neighbor < current) {
                            layer[neighbor]++; // Increment layer if prerequisite is after the chapter
                        }
                        queue.add(neighbor);
                    }
                }
            }

            if (processed != n) {
                System.out.println(-1); // Cycle detected
            } else {
                System.out.println(maxLayer); // Number of readings
            }
        }

        sc.close();
    }
}