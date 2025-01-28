import java.util.*;

public class MinimumNorthMagnets {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();
        in.nextLine();

        char[][] grid = new char[n][m];
        for (int i = 0; i < n; i++) {
            grid[i] = in.nextLine().toCharArray();
        }

        int blackCount =0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(grid[i][j]=='#'){
                    blackCount++;
                }
            }
        }

        if(blackCount==0){
            System.out.println(0);
            return;
        }

        boolean[] rowHasBlack = new boolean[n];
        boolean[] colHasBlack = new boolean[m];
        boolean valid = false;

        //rows
        for (int i = 0; i < n; i++) {
            boolean inBlackSegment = false;
            for (int j = 0; j < m; j++) {
                if(grid[i][j] == '#'){
                    rowHasBlack[i] = true;
                    if(!inBlackSegment){
                        inBlackSegment = true;
                    }
                }else if(inBlackSegment){
                    for (int k = j+1; k < m; k++) {
                        if(grid[i][k]=='#'){
                            valid = false;
                            break;
                        }
                    }
                }
            }
        }

        //col
        for (int j = 0; j < m; j++) {
            boolean inBlackSegment = false;
            for (int i = 0; i < n; i++) {
                if(grid[i][j] == '#'){
                    colHasBlack[i] = true;
                    if(!inBlackSegment){
                        inBlackSegment = true;
                    }
                }else if(inBlackSegment){
                    for (int k = i+1; k < n; k++) {
                        if(grid[k][j]=='#'){
                            valid = false;
                            break;
                        }
                    }
                }
            }
        }


        for (int i = 0; i < n; i++) {
            if(!rowHasBlack[i]) valid = false;
        }

        for (int j = 0; j < m; j++) {
            if(!colHasBlack[j]) valid = false;
        }
        
        if(!valid){
            System.out.println(-1);
            return;
        }

        int northMagnet = 0;
        boolean[][] vis = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(grid[i][j] == '#'){
                    northMagnet++;

                    Queue<int[]> q = new LinkedList<>();
                    q.add(new int[]{i,j});

                    while(!q.isEmpty()){
                        int[] cell = q.poll();
                        int x = cell[0];
                        int y = cell[1];

                        if(vis[x][y]) continue;
                        vis[x][y] = true;

                        if(x>0 && grid[x-1][y]=='#' && !vis[x-1][y]) q.add(new int[]{x-1,y});
                        if(x<n-1 && grid[x+1][y]=='#' && !vis[x+1][y]) q.add(new int[]{x+1,y});
                        if(y>0 && grid[x][y-1]=='#' && !vis[x][y-1]) q.add(new int[]{x,y-1});
                        if(y<m-1 && grid[x][y+1]=='#' && !vis[x][y+1]) q.add(new int[]{x,y+1});
                    }
                }
            }
        }
        System.out.println(northMagnet);
    }
}
