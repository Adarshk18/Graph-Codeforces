import java.io.*;
import java.util.*;

public class LongestFlight {
    static List<List<Integer>> adj;
    static int[] dist;
    static int[] parent;
    
    public static void main(String[] args)throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adj.get(a).add(b);
        }

        dist = new int[n+1];
        parent= new int[n+1];
        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        dist[1] = 1;

        //performing toposort using bfs
        int[] inDegree = new int[n+1];
        for (int i = 1; i <= n; i++) {
            for(int it: adj.get(i)){
                inDegree[it]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for(int i=0; i<n; i++){
            if(inDegree[i]==0){
                q.add(i);
            }
        }

        while(!q.isEmpty()){
            int u = q.poll();
            for(int v: adj.get(u)){
                if(dist[u]!=-1 && dist[v] < dist[u]+1){
                    dist[v] = dist[u]+1;
                    parent[v] = u;
                }
                inDegree[v]--;
                if(inDegree[v]==0){
                    q.add(v);
                }
            }
        }

        if(dist[n]==-1){
            pw.println("IMPOSSIBLE");
        }else{
            pw.println(dist[n]);
            List<Integer> path = new ArrayList<>();
            for(int v=n; v!=-1; v = parent[v]){
                path.add(v);
            }
            Collections.reverse(path);
            for(int city: path){
                pw.print(city + " ");
            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }


}
