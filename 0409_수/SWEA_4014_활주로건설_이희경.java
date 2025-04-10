import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_4014_활주로건설_이희경 {
  static int N, X;
  static int[][] map;
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      X = Integer.parseInt(st.nextToken());
      map = new int[N][N];

      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      int result = countRowPossible() + countColPossible();
      sb.append("#").append(tc).append(" ").append(result).append("\n");
    }

    System.out.println(sb);
  }

  private static int countRowPossible() {
    int rowCount = 0;
    for (int row = 0; row < N; row++) {
      boolean[] visited = new boolean[N]; // 각 행마다 초기화
      boolean flag = true;
      for (int col = 1; col < N; col++) {
        int diff = map[row][col] - map[row][col - 1];
        if (Math.abs(diff) > 1) {
          flag = false;
          break;
        }
        if (diff == 1) { // 오르막
          if (col - X < 0) {
            flag = false;
            break;
          }
          for (int i = col - X; i < col; i++) {
            if (visited[i] || map[row][i] != map[row][col - 1]) {
              flag = false;
              break;
            }
            visited[i] = true;
          }
        } else if (diff == -1) { // 내리막
          if (col + X - 1 >= N) {
            flag = false;
            break;
          }
          for (int i = col; i < col + X; i++) {
            if (visited[i] || map[row][i] != map[row][col]) {
              flag = false;
              break;
            }
            visited[i] = true;
          }
          col += X - 1; // 경사로 구간 건너뛰기
        }
        if (!flag)
          break;
      }
      if (flag)
        rowCount++;
    }
    return rowCount;
  }

  private static int countColPossible() {
    int colCount = 0;
    for (int col = 0; col < N; col++) {
      boolean[] visited = new boolean[N]; // 각 열마다 초기화
      boolean flag = true;
      for (int row = 1; row < N; row++) {
        int diff = map[row][col] - map[row - 1][col];
        if (Math.abs(diff) > 1) {
          flag = false;
          break;
        }
        if (diff == 1) { // 오르막
          if (row - X < 0) {
            flag = false;
            break;
          }
          for (int i = row - X; i < row; i++) {
            if (visited[i] || map[i][col] != map[row - 1][col]) {
              flag = false;
              break;
            }
            visited[i] = true;
          }
        } else if (diff == -1) { // 내리막
          if (row + X - 1 >= N) {
            flag = false;
            break;
          }
          for (int i = row; i < row + X; i++) {
            if (visited[i] || map[i][col] != map[row][col]) {
              flag = false;
              break;
            }
            visited[i] = true;
          }
          row += X - 1; // 경사로 구간 건너뛰기
        }
        if (!flag)
          break;
      }
      if (flag)
        colCount++;
    }
    return colCount;
  }
}