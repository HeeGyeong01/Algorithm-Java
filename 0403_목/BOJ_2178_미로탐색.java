import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2178_미로탐색 {
  static BufferedReader br;
  static StringTokenizer st;
  static int N, M;
  static int[][] grid, visited;
  static int[] dr = { 0, 1, 0, -1 }; //4방
  static int[] dc = { 1, 0, -1, 0 };

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken()); // 행
    M = Integer.parseInt(st.nextToken()); // 열
    grid = new int[N][M]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
    visited = new int[N][M];

    // grid 상태 입력받음
    for (int row = 0; row < N; row++) {
      String line = br.readLine();
      for (int col = 0; col < M; col++) {
        grid[row][col] = line.charAt(col) - '0';
      }
    }

    bfs();

    System.out.println(visited[N-1][M-1]);

  }

  public static void bfs() {
    Queue<int[]> que = new ArrayDeque<>();
    que.offer(new int[] { 0, 0 }); // row, col
    visited[0][0] = 1;

    while (!que.isEmpty()) {
      int[] v = que.poll();

      for (int dir = 0; dir < 4; dir++) {
        int nr = v[0] + dr[dir];
        int nc = v[1] + dc[dir];
        // 해당 칸이 벽이나 바이러스가 있는 칸인 경우
        boolean inBounds = nr >= 0 && nr < N && nc >= 0 && nc < M;
        if (inBounds && grid[nr][nc] == 1 && visited[nr][nc] == 0) { // grid 범위 안에 있고, 칸의 값이 1이고, 방문한 적 없는 경우

          visited[nr][nc] = visited[v[0]][v[1]] +1;
          que.offer(new int[] { nr, nc });
          // 도착지 도착한 경우 
          if (nr == (N - 1) && nc == (M - 1)) return; 

        }

      }
    }
  }

}