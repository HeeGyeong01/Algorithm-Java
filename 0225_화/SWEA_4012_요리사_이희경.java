import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 2/N 크기의 조합을 구해서 두 조합 각각의 시너지를 계산 -> 시너지 차이의 최솟값을 구하는 문제
 * 
 * 1. 식재료의 수와 시너지 정보를 입력받음 
 * 2. 2/N 길이의 조합을 만듦 
 *  2-1. 기저조건: 한 팀(n/2)를 다 뽑았을 때. 
 *    - foodA와 foodB의 시너지 합을 계산한다. 
 *    - 시너지 테이블의 값이 둘 다 true거나 false인 경우 같은 음식 안에 있다는 것이므로 foodA와 foodB에 각각 추가한다. 
 *    - 그 후 두 시너지의 최솟값 차이를 업데이트 한다. 
 *  2-2. 모든 식재료를 다 고려한 경우 재귀호출 끝. 
 *  2-3. 현재 고려요소를 방문처리한 후 순열길이+1, 고려요소+1해서 재귀호출 
 *    - 현재 고려요소를 방문처리를 취소한 후 고려요소+1해서 재귀호출
 */
public class SWEA_4012_요리사_이희경 {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;
  static int[][] synergy;
  static int N, minDiff;
  static boolean[] isSelected = new boolean[16];

  public static void main(String[] args) throws IOException {

    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 식재료의 수
      synergy = new int[N][N]; // 시너지

      // 시너지 정보 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          synergy[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      minDiff = Integer.MAX_VALUE; // 능력치의 차이 최솟값
      makeComb(0, 0); // 2/N 길이의 조합을 만듦

      sb.append('#').append(tc).append(' ').append(minDiff).append('\n');
    }
    System.out.println(sb);
  }

  public static void makeComb(int count, int itemIdx) {

    // 기저조건: 한 팀(n/2)를 다 뽑았을 때.
    if (count == N / 2) {
      int foodAStats = 0, foodBStats = 0;

      // foodA, foodB의 시너지 합 계산
      for (int guestA = 0; guestA < N; guestA++) {
        for (int guestB = 0; guestB < N; guestB++) {

          if (isSelected[guestA] && isSelected[guestB]) {
            foodAStats += synergy[guestA][guestB];
          }
          if (!isSelected[guestA] && !isSelected[guestB]) {
            foodBStats += synergy[guestA][guestB];
          }

        }
      }

      // 두 시너지의 최솟값 차이 업데이트.
      minDiff = Math.min(minDiff, Math.abs(foodAStats - foodBStats));
      return;
    }

    // 끝까지 다 고려한 경우 return;
    if (itemIdx == N - 1) {
      return;
    }

    // 현재 고려요소를 방문처리한 후 순열길이+1, 고려요소+1해서 재귀호출
    isSelected[itemIdx] = true;
    makeComb(count + 1, itemIdx + 1);
    // 현재 고려요소를 방문처리를 취소한 후 고려요소+1해서 재귀호출
    isSelected[itemIdx] = false;
    makeComb(count, itemIdx + 1);

  }

}
