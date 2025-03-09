import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 경기결과 int[4][6][3] 형식으로 입력받음. 4개의 예제, 6명의 선수, 3개의 가능한 결과
 * 2. playGame 재귀호출 시작
 *    2-1. 기저조건: 15번의 경기 다 했을 때 4개의 예제와 비교하면서 경기결과가 같은지 확인
 *         -> 같으면 해당 예제 가능(=1)으로 저장.
 *    2-2. 승/무/패 3종류 순회
 *        2-2-1. 각각 승/무/패의 경우에 4개의 예제 중 최댓값을 넘지 않는지, 유망한 조건인지 확인
 *               유망한 조건이면 2명의 플레이어를 다음 조합으로 넘어갈 수 있게 파라미터 조정해서 넘김.
 *        2-2-2. 재귀호출 한 다음 승/무/패 결과에 따라서 더했던 점수를 원복함.
 * 3. 4개의 예제 가능한 결과인지 저장한 배열의 값을 출력.
 */
public class BOJ_6987_월드컵_이희경 {
  static StringTokenizer st;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static int[][][] inputPlayResult;
  static int[][] curPlayResult, maxValueResult;
  static int[] isAvailableResult = { 0, 0, 0, 0 }; // 불가능으로 초기화

  public static void main(String[] args) throws IOException {

    inputPlayResult = new int[4][6][3];
    curPlayResult = new int[6][3];
    maxValueResult = new int[6][3];

    // 1. 경기결과 입력받음
    for (int caseIdx = 0; caseIdx < 4; caseIdx++) {
      st = new StringTokenizer(br.readLine());
      for (int row = 0; row < 6; row++) {
        for (int col = 0; col < 3; col++) {
          inputPlayResult[caseIdx][row][col] = Integer.parseInt(st.nextToken());
          maxValueResult[row][col] = Math.max(maxValueResult[row][col], inputPlayResult[caseIdx][row][col]);
        }

      }
    }

    // 2. 재귀호출 시작
    playGame(0, 0, 1);

    for (int num : isAvailableResult) {
      System.out.print(num + " ");
    }

  }

  public static void playGame(int count, int leftPlayer, int rightPlayer) {

    // 15번의 경기 다 했을 때
    if (count == 15) {
      // 4가지 입력 경기결과와 비교.
      for (int caseIdx = 0; caseIdx < 4; caseIdx++) {
        boolean flag = true;

        for (int row = 0; row < 6; row++) {
          for (int col = 0; col < 3; col++) {
            if (inputPlayResult[caseIdx][row][col] != curPlayResult[row][col]) {
              flag = false;
              break;
            }
          }
          if (flag == false) {
            break;
          }
        }

        if (flag == true && isAvailableResult[caseIdx] == 0) {
          isAvailableResult[caseIdx] = 1;
        }

      }
      return;
    }

    // 승 무 패 3종류 순회
    for (int matchResult = 0; matchResult < 3; matchResult++) {
      int leftIdx = -1, rightIdx = -1;

      if (matchResult == 0) { // 왼쪽이 이김
        leftIdx = 0;
        rightIdx = 2;
      } else if (matchResult == 1) { // 무승부
        leftIdx = 1;
        rightIdx = 1;
      } else { // 오른쪽이 이김
        leftIdx = 2;
        rightIdx = 0;
      }

      // 유망한 결과인지 확인
      if (curPlayResult[leftPlayer][leftIdx] + 1 <= maxValueResult[leftPlayer][leftIdx] &&
          curPlayResult[rightPlayer][rightIdx] + 1 <= maxValueResult[rightPlayer][rightIdx]) {

        curPlayResult[leftPlayer][leftIdx] += 1;
        curPlayResult[rightPlayer][rightIdx] += 1;

        // 다음 플레이어 지정(조합)
        int nextLeft = rightPlayer == 5 ? leftPlayer + 1 : leftPlayer;
        int nextRight = rightPlayer == 5 ? leftPlayer + 2 : rightPlayer + 1;

        playGame(count + 1, nextLeft, nextRight);

        // 결과 원복
        curPlayResult[leftPlayer][leftIdx] -= 1;
        curPlayResult[rightPlayer][rightIdx] -= 1;
      }
    }

  }

}
