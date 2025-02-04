import java.util.*;

public class LabyrinthPath {
    static int n, m;
    static char[][] grid;
    static boolean[][] visited;
    static int[][] dist;
    static int[][] parent;
    static int startX, startY, endX, endY;

    // Directions: Up, Down, Left, Right
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    static char[] moveChar = {'U', 'D', 'L', 'R'};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        sc.nextLine(); // Consume newline

        grid = new char[n][m];
        visited = new boolean[n][m];
        dist = new int[n][m];
        parent = new int[n][m];

        // Read input and locate 'A' and 'B'
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'A') {
                    startX = i;
                    startY = j;
                } else if (grid[i][j] == 'B') {
                    endX = i;
                    endY = j;
                }
            }
        }

        // BFS to find shortest path
        if (bfs()) {
            System.out.println("YES");
            System.out.println(dist[endX][endY]);
            System.out.println(reconstructPath());
        } else {
            System.out.println("NO");
        }
    }

    // BFS for shortest path
    static boolean bfs() {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];

            // Try all four directions
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];

                if (isValid(nx, ny)) {
                    queue.add(new int[]{nx, ny});
                    visited[nx][ny] = true;
                    dist[nx][ny] = dist[x][y] + 1;
                    parent[nx][ny] = i; // Store direction index

                    if (nx == endX && ny == endY) return true; // Found shortest path
                }
            }
        }
        return false; // No path found
    }

    // Check if a move is valid
    static boolean isValid(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m && !visited[x][y] && grid[x][y] != '#';
    }

    // Reconstruct path from B to A
    static String reconstructPath() {
        StringBuilder path = new StringBuilder();
        int x = endX, y = endY;

        while (x != startX || y != startY) {
            int dir = parent[x][y];
            path.append(moveChar[dir]);
            x -= dx[dir];
            y -= dy[dir];
        }

        return path.reverse().toString(); // Reverse to get correct path order
    }
}
