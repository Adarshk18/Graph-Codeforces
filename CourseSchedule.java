import java.io.*;
import java.util.*;


public class CourseSchedule {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }

        int[] inDegree = new int[n+1];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            adj.get(a).add(b);
            inDegree[b]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            if(inDegree[i]==0){
                q.add(i);
            }
        }

        List<Integer> res = new ArrayList<>();
        while(!q.isEmpty()){
            int u = q.poll();

            res.add(u);
            for(int v: adj.get(u)){
                inDegree[v]--;
                if(inDegree[v]==0){
                    q.add(v);
                }
            }
        }

        if(res.size()==n){
            for(int course: res){
                pw.print(course + " ");
            }
            pw.println();
        }else{
            pw.print("IMPOSSIBLE");
        }
        pw.flush();
    }
}
