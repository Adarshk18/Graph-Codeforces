import java.io.*;
import java.util.*;

public class Planet {
    static int[] teleporter;
    static int[] result;
    static boolean[] vis, cycle;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        int n = Integer.parseInt(br.readLine());
        
        teleporter = new int[n + 1];
        result = new int[n + 1];
        vis = new boolean[n + 1];
        cycle = new boolean[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            teleporter[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= n; i++) {
            if (!vis[i]) {
                detectCycle(i);
            }
        }

        for (int i = 1; i <= n; i++) {
            pw.print(result[i] + " ");
        }
        pw.flush();
    }

    static void detectCycle(int start) {
        Map<Integer, Integer> indexMap = new HashMap<>();
        List<Integer> path = new ArrayList<>();
        
        int current = start;
        while (!vis[current]) {
            vis[current] = true;
            path.add(current);
            indexMap.put(current, path.size() - 1); // store index
            current = teleporter[current];
        }

        if (indexMap.containsKey(current)) {
            int cycleStart = indexMap.get(current);
            int cycleLength = path.size() - cycleStart;
            for (int i = cycleStart; i < path.size(); i++) {
                result[path.get(i)] = cycleLength;
                cycle[path.get(i)] = true;
            }
            for (int i = cycleStart - 1; i >= 0; i--) {
                result[path.get(i)] = result[path.get(i + 1)] + 1;
            }
        } else {
            for (int i = path.size() - 1; i >= 0; i--) {
                result[path.get(i)] = result[teleporter[path.get(i)]] + 1;
            }
        }
    }
}
