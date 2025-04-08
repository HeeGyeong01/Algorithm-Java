import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. board[][] 입력받으면서 0인칸은 List에 저장해둔다.
 * 2. 백트래킹
 *    - 기저조건: 칸 다 채운경우
 *    - 1~9까지 순회하며 넣을 수 있는 숫자인 경우 넣고 재귀호출 한다.
 *    - isPossible()로 행, 열, 정사각형 확인하며 넣을 수 있는 숫자인지 확인함.
 * 3. 채워진 board칸 출력.
 */
public class BOJ_2239_스도쿠_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int[][] board;
  static boolean isComplete;
  static List<Pos> blanks;

  static class Pos {
    int row, col;

    public Pos(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    board = new int[9][9]; // 입력으로 받는 보드 정보를 저장할 2차원 배열
    blanks = new ArrayList<>();

    // board 상태 입력받음
    for (int row = 0; row < 9; row++) {
      String str = br.readLine();
      for (int col = 0; col < 9; col++) {
        board[row][col] = str.charAt(col) - '0';
        if (board[row][col] == 0) {
          blanks.add(new Pos(row, col));
        }
      }
    }

    // 백트랙 시작
    isComplete = false;
    backtrack(0);

    // 정답 출력
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        sb.append(board[row][col]);
      }
      sb.append('\n');
    }

    System.out.println(sb);
  }

  private static void backtrack(int idx) {
    if (isComplete) {
      return;
    }
    if (idx == blanks.size()) {
      isComplete = true;
      return;
    }
    for (int num = 1; num <= 9; num++) {
      if (isPossible(num, blanks.get(idx))) {
        // 해당 칸에 해당 숫자 넣어도 문제 없으면 -> 넣고 다음으로 넘어감.
        board[blanks.get(idx).row][blanks.get(idx).col] = num;
        backtrack(idx + 1);

        if (!isComplete) { // 아직 완성되지 않았으면 복구함
          board[blanks.get(idx).row][blanks.get(idx).col] = 0;
        }
      }
    }

  }

  private static boolean isPossible(int num, Pos pos) {
    // 해당 행에 중복 숫자 있는지
    for (int col = 0; col < 9; col++) {
      if (num == board[pos.row][col]) {
        return false;
      }
    }

    // 해당 열에 중복숫자 있는지
    for (int row = 0; row < 9; row++) {
      if (num == board[row][pos.col]) {
        return false;
      }
    }

    // 속한 3x3 정사각형 안에 중복숫자 있는지
    for (int row = (pos.row / 3) * 3; row < (pos.row / 3) * 3 + 3; row++) {
      for (int col = (pos.col / 3) * 3; col < (pos.col / 3) * 3 + 3; col++) {
        if (num == board[row][col]) {
          return false;
        }
      }
    }

    return true;
  }

}