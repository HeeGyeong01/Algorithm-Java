import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 1. DP 배열을 최대값으로 초기화 (최소 연산 횟수 갱신용)
 * 2. N부터 2까지 역순으로 탐색하며 최소 연산 횟수 계산
 *    - 3으로 나누기가 가능하면, idx/3으로 가는 연산 횟수 갱신
 *    - 2로 나누기가 가능하면, idx/2으로 가는 연산 횟수 갱신
 *    - 1을 빼는 연산으로 idx-1로 가는 연산 횟수 갱신
 * 3. 1에 도달하는 최소 연산 횟수 출력
 */
public class BOJ_1463_1로만들기_이희경 {
  static BufferedReader br;
  static int[] dp;
  static int N;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine()); // 주어진 정수
    dp = new int[N + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[N] = 0;

    for (int idx = N; idx >= 2; idx--) {
      int curCount = dp[idx];
      if (idx % 3 == 0 && curCount + 1 < dp[idx / 3]) {
        dp[idx / 3] = curCount + 1;
      }
      if (idx % 2 == 0 && curCount + 1 < dp[idx / 2]) {
        dp[idx / 2] = curCount + 1;
      }
      if (curCount + 1 < dp[idx - 1]) {
        dp[idx - 1] = curCount + 1;
      }
    }

    System.out.println(dp[1]);
  }

}