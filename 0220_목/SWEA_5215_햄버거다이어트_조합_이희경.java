import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * N개의 값 중에서 M개의 숫자들로 만들 수 있는 조합을 구해서
 * 제한 칼로리를 넘지 않는 조합에서 나오는 점수의 최대값을 구하는 문제.
 * 
 * 1. score[], kcal[]에 재료 정보 입력받음.
 * 2. combination() 재귀 호출
 *  2-1. 구한 totalKcal가 kcalLimit 이하인 경우 maxScore 업데이트 한다.
 *  2-2. 파라미터로 전달된 인덱스부터 끝까지 순회하며
  *    - 칼로리와 점수의 총합에 현재 칼로리/점수 더함.
  *    - 현재 인덱스+1을 파라미터로 넘겨서 재귀호출
  *    - 칼로리와 점수의 총합에 현재 칼로리/점수 뺌.
 * 3. maxScore를 출력한다.
 */

public class SWEA_5215_햄버거다이어트_조합_이희경 {
  static int N, kcalLimit, maxScore, totalKcal, totalScore;
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
      totalKcal = 0;
      totalScore = 0;
      kcalLimit = Integer.parseInt(st.nextToken()); // 칼로리 리밋

      // 1. score, kcal에 재료 정보 입력받음.
      for (int idx = 0; idx < N; idx++) {
        st = new StringTokenizer(br.readLine());
        score[idx] = Integer.parseInt(st.nextToken());
        kcal[idx] = Integer.parseInt(st.nextToken());
      }

      // 2. 조합 찾기.
      combination(0);

      // 3. 정답 출력.
      sb.append('#').append(tc).append(' ').append(maxScore).append('\n');

    }

    System.out.println(sb);
  }

  // 2️. 가능한 조합 찾기.
  public static void combination(int startIdx) {
    // 유효한 점수 합인 경우 maxScore 업데이트.
    if (totalKcal <= kcalLimit) {
      maxScore = Math.max(maxScore, totalScore);
    }
    // 파라미터로 전달된 인덱스부터 끝까지 순회하며 
    for (int idx = startIdx; idx < N; idx++) {
      // 칼로리와 점수의 총합에 현재 칼로리/점수 더함.
      totalKcal += kcal[idx];
      totalScore += score[idx];
      // 현재 인덱스+1을 파라미터로 넘겨서 재귀호출
      combination(idx + 1);
      // 칼로리와 점수의 총합에 현재 칼로리/점수 뺌.
      totalKcal -= kcal[idx];
      totalScore -= score[idx];
    }

  }

}