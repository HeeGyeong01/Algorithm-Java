import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dp[idx]: idx달까지의 최소비용
 * 1. 1,2,3월을 3달 이용권 가격으로 초기화
 * 2. 1 ~ 12까지 순회
 *    2-1. 1일, 한 달, 3달 이용권 중 최소 비용을 선택
 *    2-2. 최소비용과 현재 dp[idx]값을 비교해서 더 작은값으로 dp 업데이트
 *    2-3. 현재 +1, 현재 +2 달에 3달 이용권 적용한 값으로 dp 업데이트
 * 3. 최종 업데이트된 최소 비용(dp[12]와 year 중 최소값)을 출력.
 */
public class SWEA_1952_수영장_DP_이희경 {
  static StringTokenizer st;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static int[] plan, dp;
  static int dayCost, monthCost, threeMonthCost, year;

  public static void main(String[] args) throws IOException {
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      plan = new int[13];
      dp = new int[13];

      // 1-1. 이용권 가격 입력받음
      st = new StringTokenizer(br.readLine());
      dayCost = Integer.parseInt(st.nextToken());
      monthCost = Integer.parseInt(st.nextToken());
      threeMonthCost = Integer.parseInt(st.nextToken());
      year = Integer.parseInt(st.nextToken());

      // 1-2. 12개월 이용 계획 입력받음
      st = new StringTokenizer(br.readLine());
      for (int idx = 1; idx <= 12; idx++) {
        plan[idx] = Integer.parseInt(st.nextToken());

      }
      // 3달 이용권 가격으로 초기화
      dp[1] = threeMonthCost;
      dp[2] = threeMonthCost;
      dp[3] = threeMonthCost;

      for (int idx = 1; idx <= 12; idx++) {

        int minCost = Math.min(plan[idx] * dayCost, Math.min(monthCost, threeMonthCost));
        dp[idx] = Math.min(dp[idx - 1] + minCost, dp[idx]);

        if (idx <= 11) {
          dp[idx + 1] = Math.min(dp[idx + 1], dp[idx - 1] + threeMonthCost);
        }
        if (idx <= 10) {
          dp[idx + 2] = dp[idx - 1] + threeMonthCost;
        }

      }

      sb.append('#').append(tc).append(' ').append(Math.min(year, dp[12])).append('\n');

    }

    System.out.println(sb);

  }

}
