import java.util.*;


public class Kefa{

    static List<List<Integer>> tree = new ArrayList<>();
    static int[] cats;
    static int n,m, cnt=0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        m = in.nextInt();

        cats = new int[n+1];
        for(int i=1; i<=n; i++){
            cats[i] = in.nextInt();
        }

        for (int i = 0; i <= n; i++) {
            tree.add(new ArrayList<>());
        }

        for(int i=0; i<n-1; i++){
            int u = in.nextInt();
            int v = in.nextInt();
            tree.get(u).add(v);
            tree.get(v).add(u);
        }

        boolean[] vis = new boolean[n+1];
        dfs(1,0,vis);

        System.out.println(cnt);
        
    }

    private static void dfs(int node, int catCount, boolean[] vis){
        vis[node] = true;

        if(cats[node]==1){
            catCount++;
        }else{
            catCount=0;
        }
        

        if(catCount>m){
            return;
        }

        boolean isLeaf = true;
        for(int it: tree.get(node)){
            if(!vis[it]){
                isLeaf = false;
                dfs(it, catCount, vis);
            }
        }
        if(isLeaf){
            cnt++;
        }
    }
} 
