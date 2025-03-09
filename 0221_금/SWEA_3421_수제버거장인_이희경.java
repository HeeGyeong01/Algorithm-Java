import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * N개의 요소로 만들 수 있는 부분집합 중
 * 주어지는 불가능한 조합을 제외한 부분집합 개수를 구하는 문제.
 * 
 * 1. 서로 공존 불가능한 재료 2개를 입력받아서 비트연산을 위해
 *    각 자리수에 해당하는 수를 1로 바꿔서 impossiblePair[]에 저장함.
 * 2. makeSubset 재귀호출 시작
 *  2-1. impossiblePair[]를 순회하면서 비트연산 시행 -> 불가능한 조합이 포함되어 있으면
 *       더 이상 재귀호출은 불필요 하므로 return함.
 *  2-2. 모든 요소를 다 고려했을 경우
 *       불가능한 조합이 포함되어 있지 않은 경우이므로 가능한 버거 종류에 +1을 해주고 return함
 *  2-3. 현재 고려요소 + 1하고 현재 요소 선택한 다음 재귀 호출,
 *       고려요소에 +1 해주고 재귀 호출 한다.
 * 3. 재귀호출 다 끝낸 후 가능한 버거 종류 가짓수 출력.
 */
public class SWEA_3421_수제버거장인_이희경 {
  static int N, M, burgerKinds;
  static int[] impossiblePair;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 재료의 수
      M = Integer.parseInt(st.nextToken()); // 불가능한 조합 수
      burgerKinds = 0;
      impossiblePair = new int[M];

      // 1. impossibleComb[]에 불가능한 조합 정보 입력받음.
      for (int idx = 0; idx < M; idx++) {
        st = new StringTokenizer(br.readLine());
        int a = Integer.parseInt(st.nextToken()) - 1;
        int b = Integer.parseInt(st.nextToken()) - 1;
        int num = ((1 << a) | (1 << b));
        impossiblePair[idx] = num; 
      }

      // 2. 부분 집합 찾기.
      makeSubset(0, 0);

      // 3. 정답 출력.
      sb.append('#').append(tc).append(' ').append(burgerKinds).append('\n');
    }
    System.out.println(sb);

  }

  // 2️. 가능한 부분 집합 찾기.
  public static void makeSubset(int count, int selectedNum) {

    // 불가능한 조합이 포함되어 있으면 재귀호출 종료
    for (int idx = 0; idx < M; idx++) {
      if ((impossiblePair[idx] & selectedNum) == impossiblePair[idx]) {
        return;
      }
    }

    // 모든 요소를 다 고려했을 경우 재귀 호출 종료.
    if (count == N) {
      burgerKinds += 1;
      return;
    }

    // 현재 고려요소 + 1, 현재 요소 선택한 다음 재귀 호출
    makeSubset(count + 1, selectedNum | (1 << count));
    // 고려요소에 +1 해주고 재귀 호출.
    makeSubset(count + 1, selectedNum);

  }

}
