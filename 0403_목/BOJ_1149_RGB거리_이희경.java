import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dp[idx][b]에 idx집까지 칠했을 때 idx집의 색이 빨/초/파 색으로 칠했을 때의 최소 cost를 저장한다.
 * 
 * 1. idx = 1 ~ N-1까지 순회
 *    - 이전의 다른 색 2개 중 최솟값과 현재 cost를 더한 다음 dp[idx][0/1/2]에 저장함.
 * 2. dp[N - 1][0], dp[N - 1][1], dp[N - 1][2] 중에 최솟값을 출력.
 */
public class BOJ_1149_RGB거리_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int N;
  static int[][] cost, dp;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    N = Integer.parseInt(br.readLine()); // 집 개수

    cost = new int[N][3]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
    dp = new int[N][3];

    // grid 상태 입력받음
    for (int row = 0; row < N; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < 3; col++) {
        cost[row][col] = Integer.parseInt(st.nextToken());
      }
    }
    // 첫번째 집의 cost 저장.
    dp[0] = cost[0];

    for (int idx = 1; idx < N; idx++) {
      dp[idx][0] = Math.min(dp[idx - 1][1], dp[idx - 1][2]) + cost[idx][0];
      dp[idx][1] = Math.min(dp[idx - 1][0], dp[idx - 1][2]) + cost[idx][1];
      dp[idx][2] = Math.min(dp[idx - 1][0], dp[idx - 1][1]) + cost[idx][2];
    }

    System.out.println(Math.min(dp[N - 1][0], Math.min(dp[N - 1][1], dp[N - 1][2])));
  }

}