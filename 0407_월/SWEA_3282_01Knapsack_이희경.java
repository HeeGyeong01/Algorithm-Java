import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. volume[], cost[]에 물건 정보 입력받음.
 * 3. dp 업데이트
 *  3-1. 점화식 : dp[idx][k] = Math.max(dp[idx - 1][k], dp[idx - 1][k - kcal[idx]] + score[idx]);
 * 4. dp[N][K]를 출력한다.
 */
public class SWEA_3282_01Knapsack_이희경 {
  static int N, K;
  static int[] volume, cost;
  static int[][] dp;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 물건의 개수
      K = Integer.parseInt(st.nextToken()); // 가방의 부피
      volume = new int[N + 1];
      cost = new int[N + 1];
      dp = new int[N + 1][K + 1]; // (1-base) N 물건까지 고려했을 때 해당 부피에서 가장 높은 가치

      // volume, cost에 재료 정보 입력받음.
      for (int idx = 1; idx <= N; idx++) {
        st = new StringTokenizer(br.readLine());
        volume[idx] = Integer.parseInt(st.nextToken());
        cost[idx] = Integer.parseInt(st.nextToken());
      }

      // dp 업데이트
      for (int idx = 1; idx <= N; idx++) {
        for (int k = 1; k <= K; k++) {
          if (k < volume[idx]) {
            dp[idx][k] = dp[idx - 1][k];
          } else {
            dp[idx][k] = Math.max(dp[idx - 1][k], dp[idx - 1][k - volume[idx]] + cost[idx]);
          }
        }
      }

      // 정답 출력.
      sb.append('#').append(tc).append(' ').append(dp[N][K]).append('\n');

    }

    System.out.println(sb);
  }

}