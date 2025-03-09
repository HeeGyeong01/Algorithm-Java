import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * 1. 초기 map 정보를 2차원 배열로 입력받음
 * 2. 0 ~ 100일에 따라 순회 
 *    2-1. 해당 일자보다 작거나 같은 값의 칸을 방문처리함.(치즈 갉아먹는 것)
 *    2-2. 남아있는 치즈 칸부터 bfs하면서 방문하는 칸들을 방문처리. 
 *         -> bfs 실행할 때마다 chunk 수를 +1 해가면서 해당 일자에 몇개의 덩어리들이 생기는지 계산.
 * 3. 정답으로 maxChunk값 출력함.
 */
public class SWEA_7733_치즈도둑_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int N, maxChunk;
  static boolean[][] visited;
  static int[][] map;
  static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 }; //위, 아래, 왼, 오 4방향

  static class Loc { // 해당 칸의 좌표값 저장하는 클래스
    int row;
    int col;

    Loc(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 치즈 한 변의 길이
      map = new int[N][N]; // 입력으로 받는 map 정보를 저장할 2차원 배열
      visited = new boolean[N][N];
      maxChunk = 0;

      // 1. 초기 map 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      // 1~100일 동안 치즈 갉아먹는 것 계산 -> 몇 덩어리인지 확인
      for (int count = 0; count <= 100; count++) {
        visited = new boolean[N][N];

        // 갉아먹음
        for (int row = 0; row < N; row++) {
          for (int col = 0; col < N; col++) {
            if (map[row][col] <= count) {
              visited[row][col] = true;
            }
          }
        }

      }

      // 3. 정답 출력
      sb.append('#').append(tc).append(' ').append(maxChunk).append('\n');

    }

    System.out.println(sb);

  }

  // 덩어리 개수 찾는 메소드 4방향 탐색
  public static void bfs(int initRow, int initCol) {
    //초기값 방문처리, 큐에 삽입
    visited[initRow][initCol] = true;
    ArrayDeque<Loc> que = new ArrayDeque<>();
    que.add(new Loc(initRow, initCol));

    while (que.size() > 0) {
      Loc loc = que.poll();

      for (int idx = 0; idx < 4; idx++) {
        int nextRow = loc.row + dr[idx];
        int nextCol = loc.col + dc[idx];
        // 해당 방향으로 탐색한 칸이 범위 안에 있고 방문하지 않은 칸인 경우
        if (inBounds(nextRow, nextCol) && visited[nextRow][nextCol] == false) {
          que.add(new Loc(nextRow, nextCol));
          visited[nextRow][nextCol] = true; // 큐에 넣고 방문처리.
        }
      }
    }

  }

  // 해당 인덱스가 map[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < N && col >= 0 && col < N) {
      return true;
    }
    return false;

  }

}
