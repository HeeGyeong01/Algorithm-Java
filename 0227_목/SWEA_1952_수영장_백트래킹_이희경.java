import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 기간별 이용권 가격과 12개월 이용 계획 입력받음.
 * 2. 문제에서 구하려는 최소비용(minCost)을 우선 1년 이용권 가격으로 입력받음.
 * 3. 1월부터 재귀호출 시작한다.
 *    (1일 이용권보다 1년 이용권이 더 쌀 수도 있으므로 이용일이 0인 달이라도 놓치지 않고 다 고려해야 함.)
 *    3-1. 12월까지 다 돌은 경우 minCost값을 업데이트하고 return
 *    3-2. 1일, 1달, 3달 이용권 3개 순회하면서 
 *        1일: 지금까지의 가격에 1일 이용권 가격 X사용일값을 파라미터 넘김.
 *        1달: 지금까지의 가격에 1달 이용권 가격 더해서 파라미터 넘김.
 *        3달: 지금부터 3달 후가 12월 이후이면 바로 종료될 수 있게 다음달 파라미터로 12를 넘김.
 * 4. 최종 업데이트된 minCost값을 출력
 */
public class SWEA_1952_수영장_백트래킹_이희경 {
  static StringTokenizer st;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static int[] plan, voucherCost;
  static int minCost;

  public static void main(String[] args) throws IOException {
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      voucherCost = new int[4];
      plan = new int[12];

      // 1-1. 이용권 가격 입력받음
      st = new StringTokenizer(br.readLine());
      for (int idx = 0; idx < 4; idx++) {
        voucherCost[idx] = Integer.parseInt(st.nextToken());

      }

      // 1-2. 12개월 이용 계획 입력받음
      st = new StringTokenizer(br.readLine());
      for (int idx = 0; idx < 12; idx++) {
        plan[idx] = Integer.parseInt(st.nextToken());

      }

      minCost = voucherCost[3]; // 최소비용을 우선 1년 이용권 금액으로 초기화 함.

      backtrack(0, 0); //1월부터 시작

      // 3. 정답 출력
      sb.append('#').append(tc).append(' ').append(minCost).append('\n');

    }

    System.out.println(sb);

  }

  public static void backtrack(int monthIdx, int costSum) {
    if (monthIdx == 12) {
      minCost = Math.min(minCost, costSum);
      return;
    }

    // 1일, 1달, 3달 이용권 3종류 순회
    for (int voucherIdx = 0; voucherIdx < 3; voucherIdx++) {
      // 지금까지의 최소비용 이상인 경우 넘어감.

      switch (voucherIdx) {
      case 0: // 1일 이용권 : 이용권 가격에 사용일 곱해서 파라미터 넘김.
        backtrack(monthIdx + 1, costSum + (voucherCost[0] * plan[monthIdx]));
        break;
      case 1: // 1달 이용권 : 이용권 가격 더해서 파라미터 넘김
        backtrack(monthIdx + 1, costSum + voucherCost[1]);

        break;
      case 2: // 3달 이용권 : 지금부터 3달 후가 12월 이후이면 바로 종료될 수 있게 12 넘김.
        backtrack(monthIdx + 3 > 12 ? 12 : monthIdx + 3, costSum + voucherCost[2]);
        break;
      }

    }

  }

}
