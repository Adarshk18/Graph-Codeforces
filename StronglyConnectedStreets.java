import java.util.*;
 
public class StronglyConnectedStreets {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
 
        int n = in.nextInt();
        int m = in.nextInt();
 
        String horizontals = in.next();
        String verticals = in.next();
 
        // Check if the grid is strongly connected
        if (isStronglyConnected(n, m, horizontals, verticals)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
 
    static boolean isStronglyConnected(int n, int m, String horizontals, String verticals) {
        // Create adjacency lists for both forward and reverse graphs
        boolean[][] visitedForward = new boolean[n][m];
        boolean[][] visitedBackward = new boolean[n][m];
 
        // Check forward connectivity from (0, 0)
        dfs(n, m, horizontals, verticals, 0, 0, visitedForward, true);
 
        // Check backward connectivity from (0, 0)
        dfs(n, m, horizontals, verticals, 0, 0, visitedBackward, false);
 
        // Verify if all junctions are reachable in both forward and reverse directions
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!visitedForward[i][j] || !visitedBackward[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
 
    static void dfs(int n, int m, String horizontals, String verticals, int row, int col, boolean[][] visited, boolean isForward) {
        visited[row][col] = true;
 
        // Move horizontally
        if (horizontals.charAt(row) == '>') {
            if (isForward && col + 1 < m && !visited[row][col + 1]) {
                dfs(n, m, horizontals, verticals, row, col + 1, visited, isForward);
            }
            if (!isForward && col - 1 >= 0 && !visited[row][col - 1]) {
                dfs(n, m, horizontals, verticals, row, col - 1, visited, isForward);
            }
        } else { // '<'
            if (isForward && col - 1 >= 0 && !visited[row][col - 1]) {
                dfs(n, m, horizontals, verticals, row, col - 1, visited, isForward);
            }
            if (!isForward && col + 1 < m && !visited[row][col + 1]) {
                dfs(n, m, horizontals, verticals, row, col + 1, visited, isForward);
            }
        }
 
        // Move vertically
        if (verticals.charAt(col) == 'v') {
            if (isForward && row + 1 < n && !visited[row + 1][col]) {
                dfs(n, m, horizontals, verticals, row + 1, col, visited, isForward);
            }
            if (!isForward && row - 1 >= 0 && !visited[row - 1][col]) {
                dfs(n, m, horizontals, verticals, row - 1, col, visited, isForward);
            }
        } else { // '^'
            if (isForward && row - 1 >= 0 && !visited[row - 1][col]) {
                dfs(n, m, horizontals, verticals, row - 1, col, visited, isForward);
            }
            if (!isForward && row + 1 < n && !visited[row + 1][col]) {
                dfs(n, m, horizontals, verticals, row + 1, col, visited, isForward);
            }
        }
    }
}