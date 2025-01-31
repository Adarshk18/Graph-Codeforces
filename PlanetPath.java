import java.util.*;
import java.io.*;
 
public class PlanetPath {
    static class Node implements Comparable<Node> {
        int planet, time;
 
        Node(int planet, int time) {
            this.planet = planet;
            this.time = time;
        }
 
        public int compareTo(Node other) {
            return Integer.compare(this.time, other.time);
        }
    }
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken()); 
        int m = Integer.parseInt(st.nextToken()); 
 
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
 
        //edges
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            graph.get(a).add(new int[]{b, c});
            graph.get(b).add(new int[]{a, c});
        }
 
        // arrival times
        List<TreeSet<Integer>> arrivalTimes = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            arrivalTimes.add(new TreeSet<>());
        }
 
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(st.nextToken());
            for (int j = 0; j < k; j++) {
                arrivalTimes.get(i).add(Integer.parseInt(st.nextToken()));
            }
        }
 
        System.out.println(findMinimumTime(n, graph, arrivalTimes));
    }
 
    private static int findMinimumTime(int n, List<List<int[]>> graph, List<TreeSet<Integer>> arrivalTimes) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] minTime = new int[n + 1];
        Arrays.fill(minTime, Integer.MAX_VALUE);
 
        pq.add(new Node(1, 0));
        minTime[1] = 0;
 
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int planet = current.planet;
            int time = current.time;
 
            // If already found a shorter path, move on
            if (time > minTime[planet]) continue;
 
            // Adjust for waiting time due to other travelers
            while (arrivalTimes.get(planet).contains(time)) {
                time++; // wait for 1 sec
            }
 
           
            for (int[] neighbor : graph.get(planet)) {
                int nextPlanet = neighbor[0];
                int travelTime = neighbor[1];
                int newTime = time + travelTime;
 
                if (newTime < minTime[nextPlanet]) {
                    minTime[nextPlanet] = newTime;
                    pq.add(new Node(nextPlanet, newTime));
                }
            }
        }
 
        return (minTime[n] == Integer.MAX_VALUE) ? -1 : minTime[n];
    }
}