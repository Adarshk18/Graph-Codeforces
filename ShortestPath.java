import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ShortestPath {
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

        int[] parent = new int[n+1];
        Arrays.fill(parent, -1);

        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        parent[1] = 0;

        while(!q.isEmpty()){
            int node = q.poll();

            if(node==n) break;

            for (int it : adj.get(node)) {
                if(parent[it]==-1){
                    parent[it] = node;
                    q.add(it);
                }
                
            }
        }

        if(parent[n]==-1){
            System.out.println("IMPOSSIBLE");
        }else{
            //reconstruct the path by backtracking
            List<Integer> path = new ArrayList<>();
            for(int at=n; at!=0; at = parent[at]){
                path.add(at);
            }
            Collections.reverse(path);

            System.out.println(path.size());
            for (int computer : path) {
                System.out.print(computer + " ");
            }
            System.out.println();
        }

        
    }
}
