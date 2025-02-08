import java.io.*;
import java.util.*;

public class EscapeLabyrinth {
    static int n, m;
    static char[][] grid;
    static int[][] monsterTime, playerTime;
    static int[] dx = {-1, 1, 0, 0}; // U, D, L, R
    static int[] dy = {0, 0, -1, 1};
    static char[] moveChar = {'U', 'D', 'L', 'R'};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        grid = new char[n][m];
        monsterTime = new int[n][m];
        playerTime = new int[n][m];

        Queue<int[]> monsterQueue = new ArrayDeque<>();
        Queue<int[]> playerQueue = new ArrayDeque<>();

        int startX = 0, startY = 0;
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            Arrays.fill(monsterTime[i], Integer.MAX_VALUE);
            Arrays.fill(playerTime[i], Integer.MAX_VALUE);
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'M') {
                    monsterQueue.add(new int[]{i, j});
                    monsterTime[i][j] = 0;
                }
                if (grid[i][j] == 'A') {
                    startX = i;
                    startY = j;
                }
            }
        }

        // BFS for Monsters
        bfs(monsterQueue, monsterTime);

        // BFS for Player
        int[][] parent = new int[n][m];
        for (int[] row : parent) Arrays.fill(row, -1);
        playerQueue.add(new int[]{startX, startY});
        playerTime[startX][startY] = 0;

        boolean escaped = false;
        int exitX = -1, exitY = -1;

        while (!playerQueue.isEmpty()) {
            int[] node = playerQueue.poll();
            int x = node[0], y = node[1];

            // Check if reached boundary
            if (x == 0 || x == n - 1 || y == 0 || y == m - 1) {
                escaped = true;
                exitX = x;
                exitY = y;
                break;
            }

            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir], ny = y + dy[dir];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] == '.') {
                    if (playerTime[nx][ny] == Integer.MAX_VALUE && playerTime[x][y] + 1 < monsterTime[nx][ny]) {
                        playerTime[nx][ny] = playerTime[x][y] + 1;
                        parent[nx][ny] = dir;
                        playerQueue.add(new int[]{nx, ny});
                    }
                }
            }
        }

        if (escaped) {
            System.out.println("YES");
            List<Character> path = new ArrayList<>();
            int x = exitX, y = exitY;
            while (parent[x][y] != -1) {
                int dir = parent[x][y];
                path.add(moveChar[dir]);
                x -= dx[dir];
                y -= dy[dir];
            }
            Collections.reverse(path);
            System.out.println(path.size());
            for (char c : path) System.out.print(c);
            System.out.println();
        } else {
            System.out.println("NO");
        }
    }

    static void bfs(Queue<int[]> queue, int[][] time) {
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int x = node[0], y = node[1];

            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir], ny = y + dy[dir];

                if (nx >= 0 && ny >= 0 && nx < n && ny < m && grid[nx][ny] == '.') {
                    if (time[nx][ny] == Integer.MAX_VALUE) {
                        time[nx][ny] = time[x][y] + 1;
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }
    }
}