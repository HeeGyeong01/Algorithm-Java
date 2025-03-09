import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * N개의 값 중에서 M개의 숫자들로 만들 수 있는 조합을 구해서
 * 제한 칼로리를 넘지 않는 조합에서 나오는 점수의 최대값을 구하는 문제.
 * 
 * 1. score[], kcal[]에 재료 정보 입력받음.
 * 2. 재료 1~N개 선택하는 것을 bitmask[]배열의 뒤에서부터 1을 채워넣는걸로 표현.
 *    2-1. nextPermutation() 실행 -> 재료n개 선택한 것에서 다음 순열 찾음(비트마스크 배열의 1의 자리 변경을 줘서)
 *    2-2. 다음 순열이 존재하는 경우 calcScore() 실행해서 구한 totalKcal가 kcalLimit 이하인 경우 maxScore 업데이트 한다.
 * 3. maxScore를 출력한다.
 */

public class SWEA_5215_햄버거다이어트_NextPermutation_이희경 {
  static int N, kcalLimit, maxScore;
  static int[] score, kcal, bitmask;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 재료의 수
      kcalLimit = Integer.parseInt(st.nextToken()); // 칼로리 리밋
      score = new int[N];
      kcal = new int[N];
      maxScore = 0;

      // 1. score, kcal에 재료 정보 입력받음.
      for (int idx = 0; idx < N; idx++) {
        st = new StringTokenizer(br.readLine());
        score[idx] = Integer.parseInt(st.nextToken());
        kcal[idx] = Integer.parseInt(st.nextToken());
      }

      // 2. 재료 1개 ~ N개 선택까지 순회하면서 순열 구함
      for (int count = 0; count < N; count++) {
        bitmask = new int[N];
        for (int idx = N - 1; idx >= N - 1 - count; idx--) {
          bitmask[idx] = 1;
        }

        // nextPermutation으로 모든 부분집합 탐색
        do {
          calcScore();
        } while (nextPermutation());
      }

      // 3. 정답 출력.
      sb.append('#').append(tc).append(' ').append(maxScore).append('\n');

    }

    System.out.println(sb);
  }

  // 2️. 가능한 조합 찾기.
  public static void calcScore() {
    int totalKcal = 0, totalScore = 0;

    // 선택된 재료의 칼로리와 점수 더함
    for (int idx = 0; idx < N; idx++) {
      if (bitmask[idx] == 1) {
        totalKcal += kcal[idx];
        totalScore += score[idx];
      }
    }

    // 유효한 점수 합인 경우 maxScore 업데이트.
    if (totalKcal <= kcalLimit) {
      maxScore = Math.max(maxScore, totalScore);
    }

  }

  // nextPermutation 구현
  public static boolean nextPermutation() {
    int i = N - 1;
    // step1 : 뒤쪽부터 탐색하여 꼭대기(i) 찾음 -> 교환위치(i-1) 찾기 위해
    while (i > 0 && bitmask[i - 1] >= bitmask[i]) {
      i--;
    }
    // 지금이 가장 큰 순열형태 -> 사전상 다음 순열이 없다.
    if (i == 0) {
      return false;
    }

    // step2 : i-1과 교환할 한 단계 큰 수를 뒤쪽부터 찾음
    int j = N - 1;
    while (bitmask[i - 1] >= bitmask[j]) {
      j--; // 다음 큰 수 찾기
    }

    // step3 : i-1 자리와 j값 교환
    swap(i - 1, j);

    // step4 : i-1 자리의 한 단계 큰 수로 변화를 줬으니 i 꼭대기 위치 부터 맨 뒤까지 오름차순 정렬함.
    int k = N - 1;
    while (i < k) {
      swap(i++, k--);
    }

    return true;
  }

  public static void swap(int leftIdx, int rightIdx) {
    int temp = bitmask[leftIdx];
    bitmask[leftIdx] = bitmask[rightIdx];
    bitmask[rightIdx] = temp;
  }

}
