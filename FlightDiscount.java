import java.io.*;
import java.util.*;

public class FlightDiscount {
    static class Pair implements Comparable<Pair> {
        int city;
        long price;
        boolean usedCoupon;

        public Pair(int city, long price, boolean usedCoupon) {
            this.city = city;
            this.price = price;
            this.usedCoupon = usedCoupon;
        }

        @Override
        public int compareTo(Pair other) {
            return Long.compare(this.price, other.price);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter p = new PrintWriter(System.out);
        StringTokenizer str = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(str.nextToken());
        int m = Integer.parseInt(str.nextToken());

        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            str = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(str.nextToken());
            int b = Integer.parseInt(str.nextToken());
            long c = Long.parseLong(str.nextToken());

            adj.get(a).add(new Pair(b, c, false));
            
        }

        long[][] dist = new long[n + 1][2]; 
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        dist[1][0] = 0;

        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(1, 0, false));

        while (!pq.isEmpty()) {
            Pair curr = pq.poll();
            int node = curr.city;
            long price = curr.price;
            boolean usedCoupon = curr.usedCoupon;
            int couponIndex = usedCoupon ? 1 : 0;

            if (price > dist[node][couponIndex]) continue;  

            for (Pair it : adj.get(node)) {
                int nextNode = it.city;
                long nextPrice = it.price;

                // Normal edge relaxation
                if (price + nextPrice < dist[nextNode][couponIndex]) {
                    dist[nextNode][couponIndex] = price + nextPrice;
                    pq.add(new Pair(nextNode, dist[nextNode][couponIndex], usedCoupon));
                }

                // Applying the coupon (if not used)
                if (!usedCoupon && price + (nextPrice / 2) < dist[nextNode][1]) {
                    dist[nextNode][1] = price + (nextPrice / 2);
                    pq.add(new Pair(nextNode, dist[nextNode][1], true));
                }
            }
        }

        p.println(Math.min(dist[n][0], dist[n][1]));
        p.flush();
    }
}