import java.io.*;
import java.util.*;

public class Monsters {
    static char[] moves = {'U', 'D', 'L', 'R'};
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] grid = new char[n][m];
        int[][] monsterDist = new int[n][m];
        int[][] playerDist = new int[n][m];
        int[] start = new int[2];
        Queue<int[]> monsterQueue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                monsterDist[i][j] = Integer.MAX_VALUE;
                playerDist[i][j] = Integer.MAX_VALUE;
                if (grid[i][j] == 'A') {
                    start[0] = i;
                    start[1] = j;
                    playerDist[i][j] = 0;
                } else if (grid[i][j] == 'M') {
                    monsterQueue.offer(new int[]{i, j});
                    monsterDist[i][j] = 0;
                }
            }
        }

        // BFS for monsters
        while (!monsterQueue.isEmpty()) {
            int[] current = monsterQueue.poll();
            int x = current[0];
            int y = current[1];
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] != '#' && monsterDist[nx][ny] == Integer.MAX_VALUE) {
                    monsterDist[nx][ny] = monsterDist[x][y] + 1;
                    monsterQueue.offer(new int[]{nx, ny});
                }
            }
        }

        // BFS for player
        Queue<int[]> playerQueue = new LinkedList<>();
        playerQueue.offer(start);
        boolean escaped = false;
        int[][] parent = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(parent[i], -1);
        }

        while (!playerQueue.isEmpty()) {
            int[] current = playerQueue.poll();
            int x = current[0];
            int y = current[1];
            if (x == 0 || x == n - 1 || y == 0 || y == m - 1) {
                escaped = true;
                // Reconstruct path
                StringBuilder path = new StringBuilder();
                while (x != start[0] || y != start[1]) {
                    int p = parent[x][y];
                    path.append(moves[p]);
                    x -= dx[p];
                    y -= dy[p];
                }
                path.reverse();
                pw.println("YES");
                pw.println(path.length());
                pw.println(path.toString());
                break;
            }
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < m && grid[nx][ny] != '#' && playerDist[nx][ny] == Integer.MAX_VALUE) {
                    if (playerDist[x][y] + 1 < monsterDist[nx][ny]) {
                        playerDist[nx][ny] = playerDist[x][y] + 1;
                        parent[nx][ny] = i;
                        playerQueue.offer(new int[]{nx, ny});
                    }
                }
            }
        }

        if (!escaped) {
            pw.println("NO");
        }

        pw.flush();
        pw.close();
    }
}