import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. grid 정보를 2차원 배열로 입력받음
 * 2. grid의 모든 칸을 순회하면서 -> 방문한 적 없는 칸인 경우
 *    -> bfs 시행, areaCount +1 함
 *    2-1. bfs() : 큐에 초기 좌표 넣고 방문처리 함.
 *                 큐가 비어있지 않은 경우 -> 4방향 탐색
 *                 -> 해당 방향으로 한 칸 간 결과가 grid[][] 범위 안이고, 방문 안 한 칸이고 이전칸이랑 같은 문자인 경우
 *                 -> 방문 처리하고 큐에 넣음
 * 3. 적록색약의 경우를 계산하기 위해서 grid[][]에서 초록인 칸을 빨강으로 바꾼다.
 * 4. 2번의 bfs를 다시 시행한다.
 * 5. 정답으로 각각 구한 areaCount 출력.
 */
public class BOJ_10026_적록색약_이희경 {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  static int N;
  static char[][] grid;
  static boolean[][] visited;
  static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 }; // 위, 아래, 왼, 오 4방향

  static class Loc { // 해당 칸의 좌표값 저장하는 클래스
    int row;
    int col;

    Loc(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    N = Integer.parseInt(br.readLine()); // 그리드의 높이와 너비
    grid = new char[N][N];

    // 초기 grid[][] 정보 입력받음
    for (int row = 0; row < N; row++) {
      String str = br.readLine();
      for (int col = 0; col < N; col++) {
        grid[row][col] = str.charAt(col);
      }
    }

    int normalAreaNum = countArea();

    //적록색약의 경우를 계산하기 위해서 초록을 빨강으로 바꾼다.
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        if (grid[row][col] == 'G') {
          grid[row][col] = 'R';
        }
      }
    }

    int redGreenAreaNum = countArea();

    System.out.println(normalAreaNum + " " + redGreenAreaNum);

  }

  public static int countArea() {
    visited = new boolean[N][N];
    int areaCount = 0;

    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        if (!visited[row][col]) { // 방문하지 않은 칸인 경우 BFS 돌림.
          bfs(row, col, grid[row][col]);
          areaCount += 1;

        }
      }
    }
    return areaCount;

  }

  public static void bfs(int row, int col, char color) {
    ArrayDeque<Loc> que = new ArrayDeque<>();
    que.add(new Loc(row, col));
    visited[row][col] = true;

    while (!que.isEmpty()) {
      Loc loc = que.poll();

      for (int idx = 0; idx < 4; idx++) {
        int nextRow = loc.row + dr[idx];
        int nextCol = loc.col + dc[idx];

        boolean inBounds = (nextRow >= 0 && nextRow < N && nextCol >= 0 && nextCol < N);
        // grid[][] 범위 안의 칸이고 방문한적 없는 칸이고 이전칸이랑 같은 문자인 경우
        if (inBounds && !visited[nextRow][nextCol] && grid[nextRow][nextCol] == color) {
          visited[nextRow][nextCol] = true;
          que.add(new Loc(nextRow, nextCol));
        }

      }
    }
  }

}
