import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * + - x / 4가지의 연산자 기호의 순열을 구해서 수식을 완성한 다음
 * 계산한 값의 최댓값과 최솟값의 차이를 구하는 문제.
 * (우선순위 무시하고 앞에서부터 순차적으로 계산한다.)
 * 
 * 1. 연산자와 피연산자 카드를 입력받음
 * 2. 연산자 기호의 순열 구하기
 *  2-1. 순열 다 만든 경우 maxResult, minResult값 업데이트하고 return
 *  2-2. 연산자 4개 순회하면서 사용처리 
 *       ->length +1을 하고, 연산자로 계산한 결과를 재귀호출할 때 파라미터로 넘김.
 * 3. 재귀호출 반복한 끝에 최종 업데이트된 maxResult - minResult값 출력.
 * 
 */
public class SWEA_4008_숫자만들기_이희경 {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;
  static int N, maxResult, minResult;
  static int[] operator, numCard;

  public static void main(String[] args) throws IOException {
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 숫자 카드의 수

      operator = new int[4];
      numCard = new int[N];
      maxResult = Integer.MIN_VALUE;
      minResult = Integer.MAX_VALUE;

      // 연산자 카드 입력받음 
      st = new StringTokenizer(br.readLine());
      for (int idx = 0; idx < 4; idx++) {
        operator[idx] = Integer.parseInt(st.nextToken());

      }

      // 피연산자 카드 입력받음
      st = new StringTokenizer(br.readLine());
      for (int idx = 0; idx < N; idx++) {
        numCard[idx] = Integer.parseInt(st.nextToken());
      }

      // 2. 순열 찾기.
      makePermutation(0, numCard[0]);

      // 3. 정답 출력.
      sb.append('#').append(tc).append(' ').append(maxResult - minResult).append('\n');

    }

    System.out.println(sb);
  }

  // 2. 가능한 순열 모두 찾기.
  public static void makePermutation(int length, int answer) {
    // 유효한 점수 합인 경우 maxScore 업데이트.
    if (length == N - 1) {
      maxResult = Math.max(maxResult, answer);
      minResult = Math.min(minResult, answer);
      return;
    }
    // 연산자 4개 순회함.
    for (int idx = 0; idx < 4; idx++) {
      if (operator[idx] > 0) { // 사용 가능한 연산자 남아있으면 실행
        operator[idx]--; // 연산자 사용 처리
        makePermutation(length + 1, calcAnswer(answer, numCard[length + 1], idx));
        operator[idx]++; // 사용처리 취소
      }
    }

  }

  // 현재 연산자 계산 처리
  public static int calcAnswer(int answer, int rightNum, int operatorIdx) {
    switch (operatorIdx) {
      case 0:
        return answer + rightNum;
      case 1:
        return answer - rightNum;
      case 2:
        return answer * rightNum;
      case 3:
        return answer / rightNum;
    }

    return answer;
  }

}