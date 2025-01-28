import java.util.*;
 
public class StargateJourneyOptimized {
    static class Edge {
        int to, time;
        Edge(int to, int time) {
            this.to = to;
            this.time = time;
        }
    }
 
    public static int findMinTime(int n, int m, List<Edge>[] graph, List<Integer>[] waitingTimes) {
        int[] minTime = new int[n + 1];
        Arrays.fill(minTime, Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1])); // {planet, time}
        pq.add(new int[]{1, 0}); // Start at planet 1 with time 0
        minTime[1] = 0;
 
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int planet = curr[0], currTime = curr[1];
 
            // Skip outdated paths
            if (currTime > minTime[planet]) continue;
 
            // Adjust for waiting times using binary search
            currTime = calculateWaitTime(currTime, waitingTimes[planet]);
 
            // Traverse neighbors
            for (Edge edge : graph[planet]) {
                int nextPlanet = edge.to, travelTime = edge.time;
                int nextTime = currTime + travelTime;
 
                // Update shortest time if better path is found
                if (nextTime < minTime[nextPlanet]) {
                    minTime[nextPlanet] = nextTime;
                    pq.add(new int[]{nextPlanet, nextTime});
                }
            }
        }
 
        return minTime[n] == Integer.MAX_VALUE ? -1 : minTime[n];
    }
 
    private static int calculateWaitTime(int currTime, List<Integer> waitingTimes) {
        if (waitingTimes.isEmpty()) return currTime;
 
        // Binary search to find the first waiting time >= currTime
        int idx = Collections.binarySearch(waitingTimes, currTime);
        if (idx < 0) idx = -idx - 1; // Get the insertion point
        if (idx < waitingTimes.size() && waitingTimes.get(idx) == currTime) {
            while (idx < waitingTimes.size() && waitingTimes.get(idx) == currTime) {
                currTime++;
                idx++;
            }
        }
        return currTime;
    }
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
 
        // Initialize graph
        List<Edge>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt();
            graph[a].add(new Edge(b, c));
            graph[b].add(new Edge(a, c));
        }
 
        // Read waiting times
        List<Integer>[] waitingTimes = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            int k = sc.nextInt();
            waitingTimes[i] = new ArrayList<>();
            for (int j = 0; j < k; j++) waitingTimes[i].add(sc.nextInt());
        }
 
        System.out.println(findMinTime(n, m, graph, waitingTimes));
    }
}