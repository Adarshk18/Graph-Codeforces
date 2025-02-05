import java.io.*;
import java.util.*;

public class RoundTrip {
    static List<List<Integer>> adj;
    static int[] parent;
    static boolean[] vis;
    static int cycleStart = -1, cycleEnd = -1;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer str = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(str.nextToken());
        int m = Integer.parseInt(str.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            str = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(str.nextToken());
            int b = Integer.parseInt(str.nextToken());
            adj.get(a).add(b);
            adj.get(b).add(a);
        }

        vis = new boolean[n+1];
        parent = new int[n+1];
        Arrays.fill(parent, -1);

        for (int i = 1; i <= n; i++) {
            if(!vis[i] && dfs(i,-1)){
                printCycle();
                return;
            }
        }
        System.out.println("IMPOSSIBLE");
    }

    private static boolean dfs(int node, int par){
        vis[node] = true;
        parent[node] = par;

        for(int it: adj.get(node)){
            if(it==par) continue;

            if(vis[it]){
                cycleStart = it;
                cycleEnd = node;
                return true;
            }
            parent[it] = node;
            if(dfs(it,node)) return true;
        }
        return false;
    }

    private static void printCycle(){
        List<Integer> cycle = new ArrayList<>();
        cycle.add(cycleStart);
        for(int v=cycleEnd; v!=cycleStart; v= parent[v]){
            cycle.add(v);
        }
        cycle.add(cycleStart);
        Collections.reverse(cycle);


        System.out.println(cycle.size());
        for(int city: cycle){
            System.out.print(city + " ");
        }
        System.out.println();
    }
}
