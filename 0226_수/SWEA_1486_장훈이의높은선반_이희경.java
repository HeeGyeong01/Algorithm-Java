import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 높이가 B이상인 탑 중에서 높이가 가장 낮은 탑의 높이를 구하는 문제.
 * 점원의 키들로 부분집합을 만들어서 풀음.
 * 
 * 1. 부분집합 만들기 위한 재귀호출 Start
 *    1-1. 아직 다 고려하지 않았고 현재까지의 높이가 선반 높이 이상인 경우-> minDiff 업데이트.
 *    1-2. 기저조건: 더 이상 고려할 요소가 없음, 부분집합 완성함. -> return
 *    1-3. 고려요소와 고른 요소 개수에 +1,현재 고려중인 점원의 키를 더해준 다음 재귀 호출.
 *    1-4. 고려요소에 +1 해주고 재귀 호출.
 * 2. minDiff 출력.
 * 
 */

public class SWEA_1486_장훈이의높은선반_이희경 {
  static int[] clerks;
  static int N, B, minDiff;
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 점원 수 
      B = Integer.parseInt(st.nextToken()); // 선반 높이
      minDiff = Integer.MAX_VALUE; // 탑과 선반의 높이의 차이 중 최소값
      clerks = new int[N]; // 점원의 키

      // 점원 키 입력받음
      st = new StringTokenizer(br.readLine());
      for (int idx = 0; idx < N; idx++) {
        clerks[idx] = Integer.parseInt(st.nextToken());
      }

      makeSubset(0, 0);

      sb.append('#').append(tc).append(' ').append(minDiff).append('\n');

    }

    System.out.println(sb);

  }

  // 2. (현재 고려하고 있는 요소, 고른 요소 개수, 현재까지의 높이 합)
  public static void makeSubset(int count, int heightSum) {
    // 아직 다 고려하지 않았고 현재까지의 높이가 선반 높이 이상인 경우-> minDiff 업데이트.
    if (count <= N && heightSum >= B) {
      minDiff = Math.min(minDiff, heightSum - B);
    }
    // 더 이상 고려할 요소가 없음, 부분집합 완성함.
    if (count == N) {
      return;
    }

    // 고려요소와 고른 요소 개수에 +1,현재 고려중인 점원의 키를 더해준 다음 재귀 호출.
    makeSubset(count + 1, heightSum + clerks[count]);
    // 고려요소에 +1 해주고 재귀 호출.
    makeSubset(count + 1, heightSum);

  }
}
