import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * 1. 초기 board 상태 입력받음 (총 치즈 개수도 계산함)
 * 2. 시간에 따라 치즈 녹임 - bfs 시행
 *    2-1. visited[][] 초기화, 이번 스텝에서 녹은 치즈 0으로 값 할당.
 *    2-2. 확실히 빈 칸인 0,0 칸을 큐에 넣고 방문처리
 *    2-3. 큐에 남아있는게 있는 조건으로 while문 
 *        2-3-1. 큐에서 poll() -> 4방 탐색
 *               해당 방향으로 탐색한 칸이 범위 안에 있고 방문하지 않은 칸인 경우
 *               큐에 넣고 방문 처리 한 후
 *               1) 치즈칸인 경우 -> 이번 step에서 녹은 치즈 +1, 빈 칸으로 바꿈.
 *               2) 빈 칸인 경우 -> 큐에 넣음.
 *        2-3-2. 이번 스텝에서 녹은 치즈 값 return;
 * 3. 도출해낸 총 걸리는 시간과 모두 녹기 한 시간 전에 남아있는 치즈조각이 놓여 있는 칸의 개수를 출력
 */

public class BOJ_2636_치즈_이희경 {
  static StringTokenizer st;
  static BufferedReader br;
  static int R, C;
  static int[][] board;
  static boolean[][] visited;
  static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 }; // 위, 아래, 왼, 오 4방향

  static class Loc { // 해당 칸의 좌표값 저장하는 클래스
    int row;
    int col;

    Loc(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken()); // 행
    C = Integer.parseInt(st.nextToken()); // 열

    board = new int[R][C];
    int remainCheese = 0; // 총 치즈 조각

    // 1. 초기 board 상태 입력받음
    for (int row = 0; row < R; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < C; col++) {
        board[row][col] = Integer.parseInt(st.nextToken());
        // 치즈 칸 일시 총 치즈조각에 +1함
        if (board[row][col] == 1) {
          remainCheese += 1;
        }
      }
    }

    int hours = 0; // 걸린 시간
    int lastCheese = 0; // 남은 치즈 수

    // 시간에 따라 치즈 녹임
    while (remainCheese > 0) {
      lastCheese = remainCheese; // 현재 남아있는 치즈 수
      int removedCheese = bfs(); // 한 시간 후 녹는 치즈 수
      remainCheese -= removedCheese; // 남은 치즈 수 업데이트
      hours += 1; // 시간 증가
    }

    // 3. 정답 출력
    System.out.println(hours);
    System.out.println(lastCheese);

  }

  // 녹아 없어질 치즈 찾는 메소드, 4방향 탐색
  public static int bfs() {
    int removedChs = 0;
    visited = new boolean[R][C];

    // 초기값 방문처리, 큐에 삽입
    ArrayDeque<Loc> que = new ArrayDeque<>();
    que.add(new Loc(0, 0));
    visited[0][0] = true;

    while (que.size() > 0) {
      Loc loc = que.poll();

      for (int idx = 0; idx < 4; idx++) {
        int nextRow = loc.row + dr[idx]; // 4방 탐색에서 다음 좌표
        int nextCol = loc.col + dc[idx];

        // 해당 방향으로 탐색한 칸이 범위 안에 있고 방문하지 않은 칸인 경우
        if (inBounds(nextRow, nextCol) && visited[nextRow][nextCol] == false) {
          visited[nextRow][nextCol] = true; // 큐에 넣고 방문처리.

          if (board[nextRow][nextCol] == 1) { // 치즈칸인 경우 -> 이번 step에 녹은 치즈 +1, 빈 칸으로 바꿈.
            removedChs += 1;
            board[nextRow][nextCol] = 0;
          } else { // 빈 칸인 경우 -> 큐에 넣음.
            que.add(new Loc(nextRow, nextCol));

          }
        }
      }
    }

    return removedChs;
  }

  // 해당 인덱스가 board[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < R && col >= 0 && col < C) {
      return true;
    }
    return false;
  }
}