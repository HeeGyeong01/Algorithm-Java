import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

/**
 * 1. 초기 map 정보를 2차원 배열로 입력받음
 * 2. 주변 8방에 지뢰가 없는 칸 -> 0, 주변 8방에 지뢰가 하나라도 있는 칸 -> 1로 바꿈.
 * 3. map의 모든 칸을 순회하면서 
 *    주변 8방에 지뢰가 없고, 방문한 적 없는 칸인 경우
 *    -> bfs 시행, clikCount +1 함
 *    3-1. bfs() : 큐에 초기 좌표 넣음.
 *                 큐가 비어있지 않은 경우 -> 8방향 탐색
 *                 -> 해당 방향으로 한 칸 간 결과가 map[][] 범위 안이고, 지뢰칸이 아니고, 방문 안 한 칸인 경우
 *                 -> 방문 처리
 *                 -> 주변 8방에 지뢰가 없는 칸인 경우 -> 큐에 넣음
 * 4. 방문 처리 안된 칸들 순회하면서 clikCount +1 함
 * 5. 정답으로 clikCount 출력함.
 */
public class SWEA_1868_파핑파핑지뢰찾기_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static int N, clickCount;
  static boolean[][] visited;
  static char[][] map;
  static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 }, dc = { 0, 0, -1, 1, -1, 1, -1, 1 }; //위, 아래, 왼, 오, 대각선 4방향

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
      N = Integer.parseInt(br.readLine());
      map = new char[N][N]; // 입력으로 받는 map 정보를 저장할 2차원 배열
      visited = new boolean[N][N];
      clickCount = 0;

      // 1. 초기 map 상태 입력받음
      for (int row = 0; row < N; row++) {
        String str = br.readLine();
        for (int col = 0; col < N; col++) {
          map[row][col] = str.charAt(col);
        }
      }

      // 2. 주변 8방에 지뢰가 없는 칸 -> 0, 주변 8방에 지뢰가 하나라도 있는 칸 -> 1로 바꿈.
      isBomb();

      // 3. 8방에 지뢰 없고 방문 안한 칸
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {

          // 주변 8방에 지뢰가 없고, 방문한 적 없는 칸인 경우
          if (map[row][col] == '0' && !visited[row][col]) {
            bfs(row, col);
            clickCount += 1;
          }

        }
      }

      // 4. 방문 안한 칸들 처리
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {

          // 방문한 적 없는 칸인 경우
          if (map[row][col] != '*' && !visited[row][col]) {
            clickCount += 1;
          }

        }
      }

      // 5. 정답 출력
      sb.append('#').append(tc).append(' ').append(clickCount).append('\n');

    }

    System.out.println(sb);

  }

  // 2. 주변 8방에 지뢰가 없는 칸 -> 0, 주변 8방에 지뢰가 하나라도 있는 칸 -> 1로 바꿈.
  public static void isBomb() {
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        if (map[row][col] == '*') {
          continue;
        }

        boolean flag = false; // 주변에 지뢰 있는지 여부
        for (int dirIdx = 0; dirIdx < 8; dirIdx++) { //8방향 탐색

          int nextRow = row + dr[dirIdx];
          int nextCol = col + dc[dirIdx];

          // 해당 방향으로 한 칸 간 결과가 map[][] 범위 안이고, 지뢰가 있는 경우
          if (inBounds(nextRow, nextCol) && map[nextRow][nextCol] == '*') {
            flag = true;
            break;
          }

        }
        // 주변 8방에 지뢰 있는 경우 -> 1, 없는 경우 -> 0
        map[row][col] = flag ? '1' : '0';
        // 주변 8방에 지뢰 없는 경우
      }
    }

  }

  // 지뢰 찾기 메소드
  // 한번 클릭 -> 8방향 탐색
  public static void bfs(int row, int col) {

    ArrayDeque<Loc> que = new ArrayDeque<>();
    que.add(new Loc(row, col));
    visited[row][col] = true;

    // 큐가 비어있지 않은 경우
    while (!que.isEmpty()) {
      Loc loc = que.poll();

      for (int dirIdx = 0; dirIdx < 8; dirIdx++) { //8방향 탐색 

        int nextRow = loc.row + dr[dirIdx];
        int nextCol = loc.col + dc[dirIdx];

        // 해당 방향으로 한 칸 간 결과가 map[][] 범위 안이고, 지뢰칸이 아니고, 방문 안 한 칸인 경우
        if (inBounds(nextRow, nextCol) && map[nextRow][nextCol] != '*' && !visited[nextRow][nextCol]) {
          visited[nextRow][nextCol] = true;
          // 
          if (map[nextRow][nextCol] == '0') { // 주변 8방에 지뢰가 없는 칸인 경우 
            que.add(new Loc(nextRow, nextCol)); // 큐에 넣음.
          }
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
