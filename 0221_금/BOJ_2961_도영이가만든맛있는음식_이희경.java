import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 모든 재료의 조합을 다 구해서 신맛과 쓴맛의 차이의 최솟값을 구하는 문제.
 * 
 * 1. 모든 재료의 신맛과 쓴맛을 입력받아서 sour[]와 bitter[]에 저장한다.
 * 2. makeSubset 재귀호출 Start
 *  2-1. (현재 고려하고 있는 요소, 고른 요소 개수, 현재까지의 신맛/쓴맛 합)를 파라미터로 전달함
 *  2-2. 아직 다 고려하지 않았고 공집합이 아닌 경우 -> 차이의합 업데이트.
 *  2-3. 더 이상 고려할 요소가 없음 -> 재귀 호출 End
 *  2-4. 고려요소와 고른 요소 개수에 +1,현재 고려중인 요소의 신맛과 쓴맛을 곱해주고 더해준 다음 재귀 호출.
 *  2-5. 고려요소에 +1 해주고 재귀 호출.
 */

public class BOJ_2961_도영이가만든맛있는음식_이희경 {
  static int N, minDiff;
  static int[] sour, bitter;
  static StringTokenizer st;
  static BufferedReader br;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    N = Integer.parseInt(br.readLine()); // 재료 개수
    minDiff = Integer.MAX_VALUE; // 신맛과 쓴맛의 최솟값 저장할 변수
    sour = new int[N];
    bitter = new int[N];
    // 1. 입력값 저장
    for (int idx = 0; idx < N; idx++) {
      st = new StringTokenizer(br.readLine());
      sour[idx] = Integer.parseInt(st.nextToken());
      bitter[idx] = Integer.parseInt(st.nextToken());
    }

    makeSubset(0, 0, 1, 0);

    System.out.println(minDiff);
  }

  // 2. (현재 고려하고 있는 요소, 고른 요소 개수, 현재까지의 신맛/쓴맛 합)
  public static void makeSubset(int count, int pickCount, int sourSum, int bitterSum) {
    // 아직 다 고려하지 않았고 공집합이 아닌 경우 -> 차이의합 업데이트.
    if (count <= N && pickCount > 0) {
      minDiff = Math.min(minDiff, Math.abs(sourSum - bitterSum));
    }
    // 더 이상 고려할 요소가 없음, 부분집합 완성함.
    if (count == N) {
      return;
    }

    // 고려요소와 고른 요소 개수에 +1,현재 고려중인 요소의 신맛과 쓴맛을 곱해주고 더해준 다음 재귀 호출.
    makeSubset(count + 1, pickCount + 1, sourSum * sour[count], bitterSum + bitter[count]);
    // 고려요소에 +1 해주고 재귀 호출.
    makeSubset(count + 1, pickCount, sourSum, bitterSum);

  }

}