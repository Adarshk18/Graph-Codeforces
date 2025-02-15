import java.io.*;
import java.util.*;

class Road implements Comparable<Road>{
    int a, b,cost;

    public Road(int a, int b , int cost){
        this.a = a;
        this.b = b;
        this.cost = cost;
    }

    @Override
    public int compareTo(Road other){
        return Integer.compare(this.cost, other.cost);
    }
}

public class RoadReparation {
    static int[] parent;

    static int find(int x){
        if(parent[x]==x) return x;
        return parent[x] = find(parent[x]);

    }

    static boolean union(int x, int y){
        int rootX = find(x);
        int rootY = find(y);

        if(rootX == rootY) return false;
        parent[rootY] = rootX;
        return true;
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Road> roads = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            roads.add(new Road(a, b, c));
        }

        //sort the roads by cost
        Collections.sort(roads);

        parent = new int[n+1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        long totalCost = 0;
        int edgeAdded = 0;

        for (Road road : roads) {
            if(union(road.a, road.b)){
                totalCost += road.cost;
                edgeAdded++;

                if(edgeAdded==n-1) break;
            }
        }
        if(edgeAdded==n-1){
            pw.println(totalCost);
        }else{
            pw.print("IMPOSSIBLE");
        }
        pw.flush();
    }
}
