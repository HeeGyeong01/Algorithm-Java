import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * [문제 개요]
 * 서로 다른 정수가 저장된 2차원 배열에서 
 * 상하좌우로 인접해있고 현재 칸의 +1값이 저장되어 있는 칸으로만 이동이 가능하다 했을 때
 * 최대 몇 칸 이동할 수 있는지 구하는 문제.
 * 
 * [풀이 과정]
 * 1. 입력으로 받는 방 번호를 저장할 2차원 배열에 저장함
 * 2. N*N 방을 모두 순회하면서 백트래킹 진행한다.
 *    2-1. 현재 방문중인 row, col, 몇번 이동했는지를 파라미터로 받는다.
 *    2-2. 상하좌우 사방으로 탐색하면서 
 *         (범위 안이고 이전 방 번호에서 +1 방인 경우) -> 재귀호출 한다.
 *    2-3. 더 이상 탐색할 방이 없는 경우
 *        2-3-1. 앞선 탐색들에서보다 더 많은 방을 이동한 경우 -> 최대이동수와 최초방번호를 업데이트.
 *        2-3-2. 앞선 탐색에서 도출한 최대이동수와 같은 경우 -> 더 작은 수가 적힌 방번호를 저장한다.
 * 3. 최초 방 번호와 최대 이동수를 정답으로 출력한다.
 * 
 */
public class SWEA_1861_정사각형방_이희경 {
  static StringTokenizer st;
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringBuilder sb = new StringBuilder();
  static int[][] rooms;
  static int N, firstRoom, maxStep, validFirstRoom;
  static int[] dr = { -1, 1, 0, 0 };
  static int[] dc = { 0, 0, -1, 1 };

  public static void main(String[] args) throws IOException {
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 행과 열 수
      rooms = new int[N][N]; // 입력으로 받는 방 번호를 저장할 2차원 배열
      maxStep = -1; // 최대 간 step
      firstRoom = validFirstRoom = 0; // 현재 최초 시작 방 번호, 가장 많은 step을 간 경우의 최초시작 방번호

      // 1. 방 번호 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          rooms[row][col] = Integer.parseInt(st.nextToken());

        }
      }

      // 2. N*N 방을 모두 순회하면서 백트래킹 진행
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {
          firstRoom = rooms[row][col];
          backtrack(row, col, 1);

        }
      }

      // 3. 정답 출력
      sb.append('#').append(tc).append(' ').append(validFirstRoom).append(' ').append(maxStep).append('\n');

    }

    System.out.println(sb);

  }

  public static void backtrack(int curRow, int curCol, int stepCount) {
    boolean flag = false;

    // 사방으로 백트래킹 재귀호출
    for (int idx = 0; idx < 4; idx++) {
      int nextRow = curRow + dr[idx], nextCol = curCol + dc[idx];
      // 범위 안이고 이전 방 번호에서 +1 방인 경우
      if (inBounds(nextRow, nextCol) && (rooms[nextRow][nextCol] == rooms[curRow][curCol] + 1)) {
        // 이동할 수 있는 방이므로 백트래킹 재귀 호출
        backtrack(nextRow, nextCol, stepCount + 1);
        flag = true;
      }
    }

    // 더 이상 탐색할 방 없는 경우
    if (flag == false) {
      // 전에보다 더 많은 방을 이동한 경우
      if (stepCount > maxStep) {
        maxStep = stepCount;
        validFirstRoom = firstRoom;

      } else if (stepCount == maxStep) {
        // 더 작은 수 적힌 방 번호를 저장한다
        validFirstRoom = Math.min(validFirstRoom, firstRoom);

      }
    }
  }

  // 해당 인덱스가 rooms[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < N && col >= 0 && col < N) {
      return true;
    }
    return false;
  }

}
