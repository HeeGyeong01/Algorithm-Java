import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 깎을 봉우리를 선택
 * 2. 1 ~ K 범위 중 얼만큼 깎을지 선택
 * 3. 높은 봉우리 중 하나 선택
 * 4. 위와 같은 조건에서의 최장 등산로 길이를 DFS 이용해서 계산 
 */
public class SWEA_1949_등산로조성_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static List<Pos> startSpot;
  static int N, K, maxHeight, maxLength;
  static int[][] grid;
  static boolean[][] visited;
  static int[] dr = { -1, 0, 1, 0 }; // 위, 오른쪽, 아래, 왼쪽
  static int[] dc = { 0, 1, 0, -1 };

  static class Pos {
    int row, col, type, time;

    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine().trim());
      N = Integer.parseInt(st.nextToken()); // grid의 행
      K = Integer.parseInt(st.nextToken()); // grid의 열

      grid = new int[N][N]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
      startSpot = new ArrayList<>();
      maxHeight = 0;
      maxLength = 1;

      // grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          int height = Integer.parseInt(st.nextToken());
          grid[row][col] = height;
          maxHeight = maxHeight < height ? height : maxHeight;
        }
      }

      // 시작 봉우리(가장 높은 칸) 리스트 생성
      setStart();

      // Step1. 깎을 봉우리를 선택
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {
          // Step2. 1 ~ K 범위 중 얼만큼 깎을지 선택
          for (int k = 1; k <= K && k <= grid[row][col]; k++) {
            grid[row][col] -= k;

            // Step3. 높은 봉우리 중 하나 선택
            for (Pos p : startSpot) {
              // 가장 높은 봉우리가 깎였을수도 있으므로
              if (grid[p.row][p.col] == maxHeight) {
                // Step4. 위와 같은 조건에서의 최장 등산로 길이 계산 
                visited = new boolean[N][N]; // 방문 확인 배열 초기화
                visited[p.row][p.col] = true;
                backtrack(p, 1);
              }
            }

            // 깎은 봉우리 원복
            grid[row][col] += k;
          }
        }
      }

      sb.append('#').append(tc).append(' ').append(maxLength).append('\n');
    }
    System.out.println(sb);
  }

  // 시작 봉우리 리스트 생성
  private static void setStart() {
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        if (grid[row][col] == maxHeight) {
          startSpot.add(new Pos(row, col));
        }
      }
    }
  }

  // 최장 등산로 길이 계산 
  private static void backtrack(Pos cur, int length) {

    for (int dir = 0; dir < 4; dir++) {
      int nr = cur.row + dr[dir];
      int nc = cur.col + dc[dir];
      boolean inBounds = nr >= 0 && nr < N && nc >= 0 && nc < N;

      // 다음 칸이 범위 안에 있고, 방문한 적 없고, 현재 높이보다 작은 경우
      if (inBounds && visited[nr][nc] == false && grid[cur.row][cur.col] > grid[nr][nc]) {
        visited[nr][nc] = true;
        maxLength = Math.max(maxLength, length + 1);
        backtrack(new Pos(nr, nc), length + 1);
        visited[nr][nc] = false;
      }
    }

  }

}