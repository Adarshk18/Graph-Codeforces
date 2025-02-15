import java.io.*;
import java.util.*;

public class RoadConstruction{
    static int[] parent;
    static int[] size;
    static int numComponents;
    static int maxSize;

    static int find(int x) {
        if (parent[x] == x) return x;
        return parent[x] = find(parent[x]); // Path compression
    }

    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY) return; // Already in the same component

        // Union by size
        if (size[rootX] < size[rootY]) {
            parent[rootX] = rootY;
            size[rootY] += size[rootX];
            maxSize = Math.max(maxSize, size[rootY]);
        } else {
            parent[rootY] = rootX;
            size[rootX] += size[rootY];
            maxSize = Math.max(maxSize, size[rootX]);
        }

        numComponents--; // Two components merged into one
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // Initialize Union-Find
        parent = new int[n + 1];
        size = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        numComponents = n;
        maxSize = 1;

        // Process each road
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            union(a, b);
            pw.println(numComponents + " " + maxSize);
        }

        pw.flush();
    }
}