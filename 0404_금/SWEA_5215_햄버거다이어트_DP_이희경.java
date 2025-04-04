import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. score[], kcal[]에 재료 정보 입력받음.
 * 2. dp[1][] 초기화
 * 3. dp 업데이트
 *  3-1. 점화식 : dp[idx][k] = Math.max(dp[idx - 1][k], dp[idx - 1][k - kcal[idx]] + score[idx]);
 * 4. dp[N][kcalLimit]를 출력한다.
 */
public class SWEA_5215_햄버거다이어트_DP_이희경 {
  static int N, kcalLimit;
  static int[] score, kcal;
  static int[][] dp;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 재료의 수
      score = new int[N + 1];
      kcal = new int[N + 1];
      kcalLimit = Integer.parseInt(st.nextToken()); // 칼로리 리밋
      dp = new int[N + 1][kcalLimit + 1]; // (1-base) N재료까지 고려했을 때 해당 칼로리에서 가장 높은 점수

      // score, kcal에 재료 정보 입력받음.
      for (int idx = 1; idx <= N; idx++) {
        st = new StringTokenizer(br.readLine());
        score[idx] = Integer.parseInt(st.nextToken());
        kcal[idx] = Integer.parseInt(st.nextToken());
      }

      // 첫번째 재료에 대해서 dp 배열 초기화
      if (kcal[1] <= kcalLimit) {
        for (int k = 1; k < kcal[1]; k++) {
          dp[1][k] = 0;
        }
        for (int k = kcal[1]; k <= kcalLimit; k++) {
          dp[1][k] = score[1];
        }
      }

      // dp 업데이트
      for (int idx = 2; idx <= N; idx++) {
        for (int k = 1; k <= kcalLimit; k++) {
          if (k - kcal[idx] >= 0) {
            dp[idx][k] = Math.max(dp[idx - 1][k], dp[idx - 1][k - kcal[idx]] + score[idx]);
          } else { // 현재 재료 추가했을 때 kcalLimit 넘는 경우 -> 이전의 값 그대로 저장함.
            dp[idx][k] = dp[idx - 1][k];
          }
        }
      }

      // 정답 출력.
      sb.append('#').append(tc).append(' ').append(dp[N][kcalLimit]).append('\n');

    }

    System.out.println(sb);
  }

}