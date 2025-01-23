import java.util.*;

public class KingsPath {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int x0 = in.nextInt();
        int y0 = in.nextInt();
        int x1 = in.nextInt();
        int y1 = in.nextInt();

        int n = in.nextInt();

        Map<Integer, Set<Integer>> allowed = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int r = in.nextInt();
            int a = in.nextInt();
            int b = in.nextInt();
            allowed.putIfAbsent(r, new HashSet<>());
            for(int j=a; j<=b; j++){
                allowed.get(r).add(j);
            }
        }

        Set<Pair> vis = new HashSet<>();
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(new Pair(x0, y0),0));
        vis.add(new Pair(x0,y0));


        int[] incrX = {-1,0,1,-1,1,-1,0,1};
        int[] incrY = {1,1,1,0,0,-1,-1,-1};

        int ans = -1;

        while(!q.isEmpty()){
            Node curr = q.poll();
            Pair currPos = curr.position;
            int distance = curr.distance;

            if(currPos.x == x1 && currPos.y == y1){
                ans = distance;
                break;
            }

            for (int i = 0; i < 8; i++) {
                Pair newPos = new Pair(currPos.x + incrX[i], currPos.y+incrY[i]);

                if(allowed.containsKey(newPos.x) && allowed.get(newPos.x).contains(newPos.y) && !vis.contains(newPos)){
                    q.add(new Node(newPos, distance+1));
                    vis.add(newPos);
                }
            }
        }
        System.out.println(ans);
    }

    static class Pair{
        int x, y;
        Pair(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    static class Node{
        Pair position;
        int distance;

        Node(Pair position, int distance){
            this.position = position;
            this.distance = distance;
        }
    }
}
