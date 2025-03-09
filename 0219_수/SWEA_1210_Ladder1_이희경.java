import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * 주어진 도착점에 도착하게 하는 사다리 출발점의 열 정보를 출력하는 문제.
 * 
 * 1. 맨 위 행을 순회하면서
 *    1-1. 빈 칸이 아니라 사다리이면 현재 열을 저장해놓고 findLoad()로 어느 도착점에 도착하는지 확인함.
 *    1-2. 주어진 도착점에 도착하는 열이면 순회를 종료하고 출력한다.
 * 2. findLoad()
 *    2-1. 해당 칸의 왼쪽에 통로가 있으면 범위 밖으로 나가거나 통로가 끝날때까지 왼쪽으로 쭉 감.
 *    2-2. 해당 칸의 오른쪽에 통로가 있으면 범위 밖으로 나가거나 통로가 끝날때까지 오른쪽으로 쭉 감.
 *    2-3. 통로로 이동이 끝났거나, 양 옆에 통로가 없으므로 -> 한 칸 내려간다.
 *    2-4. 맨 아래 행이면 주어진 도착점인지 확인하고 리턴한다.
 */

public class SWEA_1210_Ladder1_이희경 {
  static int startCol;
  static int map[][];
  static boolean isAnswerFlag;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;
    StringBuilder sb = new StringBuilder();

    for (int tc = 1; tc <= 10; tc++) {
      // init
      br.readLine();
      startCol = 0;
      map = new int[100][100];
      isAnswerFlag = false;

      // map[][]에 사다리 정보 입력받음.
      for (int row = 0; row < 100; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < 100; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      // 1️⃣ 첫번째 행 순회하면서 사다리의 시작을 찾는다.
      for (int col = 0; col < 100; col++) {
        if (map[0][col] == 1) {
          startCol = col; // 현재 탐색하고 있는 사다리의 시작이 몇 열인지 저장해놓음.
          findLoad(0, col);
          if (isAnswerFlag) { // 정답 찾은 경우
            break;
          }

        }
      }

      sb.append('#').append(tc).append(' ').append(startCol).append('\n');

    }

    System.out.println(sb);
  }

  // 2️⃣ 사다리 경로 찾는 메소드
  public static void findLoad(int row, int col) {

    int r = row, c = col;

    while (true) {
      // 왼쪽에 통로가 있으면 방향전환해서 쭉 간다.
      if (inBounds(r, c - 1) && map[r][c - 1] == 1) {
        c = moveLeft(r, c - 1);
        // 오른쪽에 통로가 있으면 방향전환해서 쭉 간다.
      } else if (inBounds(r, c + 1) && map[r][c + 1] == 1) {
        c = moveRight(r, c + 1);
      }

      // 통로로 이동이 끝났거나, 양 옆에 통로가 없으므로 -> 한 칸 내려간다.
      r += 1;
      // 종료조건: 맨 아래 행인 경우
      if (r == 99) { // 맨 아래이고 도착지점이 아닌 경우
        if (map[r][c] == 2) { // 맨 아래이고 도착지점인 경우
          isAnswerFlag = true;
        }
        return;

      }

    }

  }

  // 해당 인덱스가 map[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < 100 && col >= 0 && col < 100) {
      return true;
    }
    return false;

  }

  // 왼쪽으로 통로 이동 완료한 뒤 col 인덱스값 리턴함.
  public static int moveLeft(int row, int col) {
    int curCol = col;
    while (true) {
      // 매개변수로 전달된 위치에서 왼쪽 칸이 범위 내이고, 1(사다리 안)이면 왼쪽으로 이동한다.
      if (inBounds(row, curCol - 1) && map[row][curCol - 1] == 1) {
        curCol -= 1;
      } else {
        // 사다리의 끝이면 현재 몇 열에 있는지 값 리턴.
        return curCol;
      }
    }

  }

  // 오른쪽으로 통로 이동 완료한 뒤 col 인덱스값 리턴함.
  public static int moveRight(int row, int col) {
    int curCol = col;
    while (true) {
      // 매개변수로 전달된 위치에서 오른쪽 칸이 범위 내이고, 1(사다리 안)이면 오른쪽으로 이동한다.
      if (inBounds(row, curCol + 1) && map[row][curCol + 1] == 1) {
        curCol += 1;
      } else {
        return curCol;
      }
    }
  }
}