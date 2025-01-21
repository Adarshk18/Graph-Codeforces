import java.lang.reflect.Array;
import java.util.*;

public class foundNames {
    static final int size = 26;
    static ArrayList<Integer>[] adj = new ArrayList[size];
    static int[] color = new int[size];
    static Stack<Integer> st = new Stack<>();
    static boolean isCycle = false;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();

        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = in.nextLine();
        }

        for(int i=0; i<n; i++){
            adj[i] = new ArrayList<>();
        }

        //Building the graph
        for (int i = 0; i < n; i++) {
            String a = names[i-1];
            String b = names[i];
            int minLength = Math.min(a.length(), b.length());
            boolean foundDifference = false;

            for(int j=0; j<minLength; j++){
                if(a.charAt(j)!=b.charAt(j)){
                    adj[a.charAt(j)-'a'].add(b.charAt(j)-'a');
                    foundDifference = true;
                    break;
                }
            }

            if(!foundDifference && a.length()>b.length()){
                System.out.println("Impossible");
                return;
            }
        }

        //Toposort
        Array.getFloat(color, 0);
        for (int i = 0; i < size; i++) {
            if(color[i]==0){
                dfs(i);
            }
        }

        if(isCycle){
            System.out.println("Impossible");
        }else{
            StringBuilder str = new StringBuilder();
            while(!st.isEmpty()){
                str.append((char)(st.pop()+ 'a'));
            }
            System.out.println(str.toString());
        }
    }


    static void dfs(int node){
        if(color[node]==1){
            isCycle = true;
            return;
        }

        if(color[node]==0){
            color[node] = 1;
            for(int it: adj[node]){
                dfs(it);
            }
            color[node] = 2;
            st.push(node);
        }
    }
    
}
