import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 1. 초기 board 정보를 2차원 배열로 입력받음
 * 2. dfs 백트래킹
 *    2-1. 초기 칸을 visited로 방문처리 & HashSet으로 알파벳 방문 처리
 *    2-2. 4방을 탐색하며 해당 방향으로 탐색한 칸이 범위 안에 있고 방문하지 않은 칸이고, 전에 방문한 알파벳이 아닌 경우
 *         -> 해당 칸과 알파벳값을 방문처리함.
 *    2-3. backtrack() 재귀호출
 *    2-4. 해당 칸과 알파벳값 방문처리 해제
 *    2-5. 더 이상 위 조건에 따라 방문할 칸이 없는 경우 maxCount 와 step 값을 비교하여 업데이트.
 * 3. 정답으로 maxChunk값 출력함.
 */
public class BOJ_1987_알파벳_이희경 {
  static StringTokenizer st;
  static BufferedReader br;
  static int R, C, maxCount;
  static char[][] board;
  static boolean[][] visited;
  static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 }; //위, 아래, 왼, 오 4방향
  static Set<Character> alphabetSet = new HashSet<>();

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));

    st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken()); // 행
    C = Integer.parseInt(st.nextToken()); // 열 
    board = new char[R][C]; // 
    visited = new boolean[R][C];
    maxCount = 1;

    // 1. 초기 board 상태 입력받음
    for (int row = 0; row < R; row++) {
      String str = br.readLine();
      for (int col = 0; col < C; col++) {
        board[row][col] = str.charAt(col);
      }
    }

    // 2. 몇 스텝 가는지 계산
    visited[0][0] = true;
    alphabetSet.add(board[0][0]);
    backtrack(0, 0, 1);

    // 3. 정답 출력
    System.out.println(maxCount);

  }

  public static void backtrack(int curRow, int curCol, int step) {
    boolean flag = false;

    for (int idx = 0; idx < 4; idx++) {
      int nextRow = curRow + dr[idx];
      int nextCol = curCol + dc[idx];
      // 해당 방향으로 탐색한 칸이 범위 안에 있고 방문하지 않은 칸이고, 전에 방문한 알파벳이 아닌 경우
      if (inBounds(nextRow, nextCol) && visited[nextRow][nextCol] == false
          && !alphabetSet.contains(board[nextRow][nextCol])) {
        visited[nextRow][nextCol] = true; // 방문처리.
        alphabetSet.add(board[nextRow][nextCol]);
        backtrack(nextRow, nextCol, step + 1);
        visited[nextRow][nextCol] = false;
        alphabetSet.remove(board[nextRow][nextCol]);
        flag = true;
      }
    }

    if (flag == false) {
      maxCount = Math.max(maxCount, step); // maxCount값 업데이트

    }

  }

  // 해당 인덱스가 board[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < R && col >= 0 && col < C) {
      return true;
    }
    return false;

  }
}