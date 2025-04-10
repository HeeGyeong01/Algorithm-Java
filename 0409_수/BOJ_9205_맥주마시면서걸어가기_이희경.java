import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

public class BOJ_9205_맥주마시면서걸어가기_이희경 {
  static int n;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;
  static Pos[] stores;
  static boolean[][] dist;

  static class Pos {
    int row, col;

    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      boolean result = false;
      n = Integer.parseInt(br.readLine().trim()); // 편의점 개수
      stores = new Pos[n + 2];
      dist = new boolean[n + 2][n + 2];

      // 집, 편의점, 목적지 위치 입력받음
      for (int idx = 0; idx < n + 2; idx++) {
        st = new StringTokenizer(br.readLine());
        int row = Integer.parseInt(st.nextToken());
        int col = Integer.parseInt(st.nextToken());
        stores[idx] = new Pos(row, col);
      }

      // 인접행렬 만들기
      for (int i = 0; i < n + 2; i++) {
        for (int j = i + 1; j < n + 2; j++) {
          int distance = Math.abs(stores[i].row - stores[j].row) + Math.abs(stores[i].col - stores[j].col);
          // 1000 이하 거리만 저장함.
          if (distance <= 1000) {
            dist[i][j] = dist[j][i] = true;
          }
        }
      }
      // 한번에 목적지 갈 수 있는지 확인
      if (Math.abs(stores[0].row - stores[n + 1].row) + Math.abs(stores[0].col - stores[n + 1].col) <= 1000) {
        result = true;
      } else { // 편의점 들려야 함.
        result = isPossible();
      }

      // 정답 출력.
      sb.append(result ? "happy" : "sad").append('\n');

    }

    System.out.println(sb);
  }

  private static boolean isPossible() {
    //플로이드 워셜
    for (int k = 0; k < n + 2; k++) {
      // 출발지
      for (int s = 0; s < n + 2; s++) {
        //도착지
        for (int e = 0; e < n + 2; e++) {
          if (dist[s][k] == true && dist[k][e] == true) {

            dist[s][e] = true;
          }
        }
      }
    }

    return dist[0][n + 1];
  }

}