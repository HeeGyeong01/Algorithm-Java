import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 제한 칼로리를 넘지 않는 부분 집합에서 나오는 점수의 최대값을 구하는 문제.
 * 
 * 1. score[], kcal[]에 재료 정보 입력받음.
 * 2. makeSubset() 재귀 호출
 *  2-1. (현재 고려하고 있는 요소, 현재까지의 점수/칼로리 합)를 파라미터로 전달함
 *  2-2. 구한 totalKcal가 kcalLimit 이하인 경우 maxScore 업데이트 한다.
 *  2-3. 파라미터로 전달된 인덱스부터 끝까지 순회하며
 *    - 고려요소에 +1,현재 고려중인 요소의 점수/칼로리 더해준 다음 재귀 호출.
 *    - 고려요소에 +1 해주고 재귀 호출.
 * 3. maxScore를 출력한다.
 */

public class SWEA_5215_햄버거다이어트_부분집합_이희경 {
  static int N, kcalLimit, maxScore;
  static int[] score, kcal;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 재료의 수
      score = new int[N];
      kcal = new int[N];
      maxScore = 0;
      kcalLimit = Integer.parseInt(st.nextToken()); // 칼로리 리밋

      // 1. score[], kcal[]에 재료 정보 입력받음.
      for (int idx = 0; idx < N; idx++) {
        st = new StringTokenizer(br.readLine());
        score[idx] = Integer.parseInt(st.nextToken());
        kcal[idx] = Integer.parseInt(st.nextToken());
      }

      // 2. 부분 집합 찾기.
      makeSubset(0, 0, 0);

      // 3. 정답 출력.
      sb.append('#').append(tc).append(' ').append(maxScore).append('\n');

    }

    System.out.println(sb);
  }

  // 2️. 가능한 집합 찾기.
  public static void makeSubset(int count, int totalScore, int totalKcal) {
    // 유효한 점수 합인 경우 maxScore 업데이트.
    if (totalKcal <= kcalLimit) {
      maxScore = Math.max(maxScore, totalScore);
    }
    // 모든 요소를 다 고려했을 경우 재귀 호출 종료.
    if (count == N) {
      return;
    }
    // 현재 고려요소+1, 칼로리와 점수의 총합에 현재 칼로리/점수 더함.을 파라미터로 넘겨서 재귀호출
    makeSubset(count + 1, totalScore + score[count], totalKcal + kcal[count]);
    // 고려요소에 +1 해주고 재귀 호출.
    makeSubset(count + 1, totalScore, totalKcal);

  }

}
