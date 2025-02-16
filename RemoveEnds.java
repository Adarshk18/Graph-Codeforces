import java.io.*;
import java.util.*;

public class RemoveEnds {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int t = Integer.parseInt(br.readLine().trim());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
            for (int i = 0; i < n; i++) {
                int val = Integer.parseInt(st.nextToken());
                map.put(val, map.getOrDefault(val, 0) + 1);
            }
            
            long coins = 0;
            boolean positiveTurn = true;
            
            while (!map.isEmpty()) {
                int key = (positiveTurn ? map.ceilingKey(0) : map.floorKey(0));
                coins += Math.abs(key);
                
                if (map.get(key) == 1) {
                    map.remove(key);
                } else {
                    map.put(key, map.get(key) - 1);
                }
                
                positiveTurn = !positiveTurn;
            }
            
            out.println(coins);
        }

        out.flush();
        out.close();
    }
}
