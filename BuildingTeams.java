import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BuildingTeams{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer str = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(str.nextToken());
        int m = Integer.parseInt(str.nextToken());

        List<List<Integer>> adj = new ArrayList<>();
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

        int[] color = new int[n+1];
        Arrays.fill(color, -1);

        for (int i = 1; i <= n; i++) {
            if(color[i]==-1){
                if(!bfs(i,adj,color)){
                    System.out.println("IMPOSSIBLE");
                    return;
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            System.out.print(color[i] + " ");
        }
    }

    private static boolean bfs(int start, List<List<Integer>> adj, int[] color){
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        color[start] = 1;

        while(!q.isEmpty()){
            int node = q.poll();

            for (int it: adj.get(node)) {
                if(color[it]==-1){
                    color[it] = 3- color[node];
                    q.add(it);
                }else if(color[it]==color[node]){
                    return false;
                }
            }
        }
        return true;
    }
}