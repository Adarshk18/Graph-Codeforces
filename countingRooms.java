import java.util.Scanner;

public class countingRooms {
    static int n, m;
    static char[][] grid;
    static boolean[][] visited;

    // DFS function to traverse the grid
    public static void dfs(int x, int y) {
        // Base case: Out of bounds or already visited or a wall
        if (x < 0 || x >= n || y < 0 || y >= m || visited[x][y] || grid[x][y] == '#') {
            return;
        }

        visited[x][y] = true; // Mark cell as visited

        // Explore 4 possible directions (Up, Down, Left, Right)
        dfs(x - 1, y); // Up
        dfs(x + 1, y); // Down
        dfs(x, y - 1); // Left
        dfs(x, y + 1); // Right
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        // Read dimensions
        n = in.nextInt();
        m = in.nextInt();
        in.nextLine(); // Move to the next line after reading integers

        grid = new char[n][m];
        visited = new boolean[n][m];

        // Read grid input correctly
        for (int i = 0; i < n; i++) {
            grid[i] = in.nextLine().toCharArray();
        }

        // // Print the grid to verify input correctness
        // System.out.println("Grid:");
        // for (int i = 0; i < n; i++) {
        //     System.out.println(grid[i]);
        // }

        int roomCount = 0;
        
        // Iterate through the entire grid to find unvisited floor cells (.)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '.' && !visited[i][j]) {
                    // System.out.println("Starting DFS at: (" + i + ", " + j + ")");
                    dfs(i, j); // Run DFS for this room
                    roomCount++; // Increase room count
                }
            }
        }

        // Print final room count
        System.out.println(roomCount);
        in.close();
    }
}
