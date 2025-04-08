import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 1. 회전 정보를 입력받고 회전 방향이 시계방향이면 ratate() 반시계방향이면 reverseRotate() 호출.
 * 2. 자석이 맞물리는 위치의 극들이 같은지 다른지 저장.
 *    - 같은 경우에 방향 반대로 바꿔가며 각 자석들 회전시킴.
 */
public class SWEA_4013_특이한자석_이희경 {
  static int N, K;
  static List<Integer>[] mag;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      K = Integer.parseInt(br.readLine().trim()); // 자석을 회전시키는 횟수
      mag = new ArrayList[4 + 1];

      for (int idx = 1; idx <= 4; idx++) {
        mag[idx] = new ArrayList<>();
      }

      // 1~4번 자석 정보
      for (int count = 1; count <= 4; count++) {
        st = new StringTokenizer(br.readLine());
        for (int idx = 0; idx < 8; idx++) {
          mag[count].add(Integer.parseInt(st.nextToken()));
        }
      }

      // 회전 정보
      for (int cnt = 0; cnt < K; cnt++) {
        st = new StringTokenizer(br.readLine());
        int whichMag = Integer.parseInt(st.nextToken());
        int rotateDir = Integer.parseInt(st.nextToken());

        if (rotateDir == 1) {
          rotate(whichMag);
        } else {
          reverseRotate(whichMag);
        }
      }

      int score = mag[1].get(0) + mag[2].get(0) * 2 + mag[3].get(0) * 4 + mag[4].get(0) * 8;

      // 정답 출력.
      sb.append('#').append(tc).append(' ').append(score).append('\n');

    }

    System.out.println(sb);
  }

  // 시계방향 회전으로 시작
  private static void rotate(int magNum) {

    boolean isDiff12 = mag[1].get(2) != mag[2].get(6);
    boolean isDiff23 = mag[2].get(2) != mag[3].get(6);
    boolean isDiff34 = mag[3].get(2) != mag[4].get(6);
    clockRotate(magNum);

    switch (magNum) {
    case 1:
      if (isDiff12) { // 1 -> 2 자석 전파
        counterClockRotate(2);
      }
      if (isDiff12 && isDiff23) { // 2 -> 3 자석 전파
        clockRotate(3);
      }
      if (isDiff12 && isDiff23 && isDiff34) { // 3 -> 4 자석 전파
        counterClockRotate(4);
      }
      break;
    case 2:
      if (isDiff12) { // 2 -> 1 자석 전파
        counterClockRotate(1);
      }
      if (isDiff23) { // 2 -> 3 자석 전파
        counterClockRotate(3);
      }
      if (isDiff23 && isDiff34) { // 3 -> 4 자석 전파
        clockRotate(4);
      }
      break;
    case 3:
      if (isDiff23) { // 3 -> 2 자석 전파
        counterClockRotate(2);
      }
      if (isDiff23 && isDiff12) { // 2 -> 1 자석 전파
        clockRotate(1);
      }
      if (isDiff34) { // 3 -> 4 자석 전파
        counterClockRotate(4);
      }
      break;
    case 4:
      if (isDiff34) { // 4 -> 3 자석 전파
        counterClockRotate(3);
      }
      if (isDiff34 && isDiff23) { // 3 -> 2 자석 전파
        clockRotate(2);
      }
      if (isDiff34 && isDiff23 && isDiff12) { // 2 -> 1 자석 전파
        counterClockRotate(1);
      }
      break;
    }
  }

  // 반시계 방향 회전으로 시작
  private static void reverseRotate(int magNum) {

    boolean isDiff12 = mag[1].get(2) != mag[2].get(6);
    boolean isDiff23 = mag[2].get(2) != mag[3].get(6);
    boolean isDiff34 = mag[3].get(2) != mag[4].get(6);
    counterClockRotate(magNum);

    switch (magNum) {
    case 1:
      if (isDiff12) { // 1 -> 2 자석 전파
        clockRotate(2);
      }
      if (isDiff12 && isDiff23) { // 2 -> 3 자석 전파
        counterClockRotate(3);
      }
      if (isDiff12 && isDiff23 && isDiff34) { // 3 -> 4 자석 전파
        clockRotate(4);
      }
      break;
    case 2:
      if (isDiff12) { // 2 -> 1 자석 전파
        clockRotate(1);
      }
      if (isDiff23) { // 2 -> 3 자석 전파
        clockRotate(3);
      }
      if (isDiff23 && isDiff34) { // 3 -> 4 자석 전파
        counterClockRotate(4);
      }
      break;
    case 3:
      if (isDiff23) { // 3 -> 2 자석 전파
        clockRotate(2);
      }
      if (isDiff23 && isDiff12) { // 2 -> 1 자석 전파
        counterClockRotate(1);
      }
      if (isDiff34) { // 3 -> 4 자석 전파
        clockRotate(4);
      }
      break;
    case 4:
      if (isDiff34) { // 4 -> 3 자석 전파
        clockRotate(3);
      }
      if (isDiff34 && isDiff23) { // 3 -> 2 자석 전파
        counterClockRotate(2);
      }
      if (isDiff34 && isDiff23 && isDiff12) { // 2 -> 1 자석 전파
        clockRotate(1);
      }
      break;
    }
  }

  private static void clockRotate(int magNum) {
    int tail = mag[magNum].get(7);
    mag[magNum].remove(7);
    mag[magNum].add(0, tail);
  }

  private static void counterClockRotate(int magNum) {
    int head = mag[magNum].get(0);
    mag[magNum].remove(0);
    mag[magNum].add(head);
  }

}