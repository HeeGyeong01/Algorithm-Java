import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

public class SWEA_5656_벽돌깨기_이희경 {
  static int N, C, R, minRemain, kanSum;
  static int[][] map;
  static int[] dr = { 1, 0, -1, 0 }; // 아래 <- 위 ->
  static int[] dc = { 0, -1, 0, 1 };
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  static class Pos {
    int row, col, range;

    public Pos(int row, int col, int range) {
      this.row = row;
      this.col = col;
      this.range = range;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 구슬 쏠 수 있는 횟수
      C = Integer.parseInt(st.nextToken()); // 너비 칸 수 
      R = Integer.parseInt(st.nextToken()); // 높이 칸 수 
      map = new int[R][C];
      minRemain = Integer.MAX_VALUE;
      kanSum = 0; // 초기 벽돌 수 

      // map 상태 입력받음
      for (int row = 0; row < R; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < C; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
          if (map[row][col] != 0) {
            kanSum += 1;
          }
        }
      }

      chooseTarget(0, map);

      // 정답: 최대한 많은 벽돌을 제거했을 때 남은 벽돌의 개수.
      sb.append('#').append(tc).append(' ').append(minRemain).append('\n');

    }

    System.out.println(sb);
  }

  // 어느 칸에 구슬 쏠지 고르기
  private static void chooseTarget(int count, int[][] curMap) {
    int remain = countRemain(curMap);
    if (remain == 0) { // 모든 벽돌 제거 시 즉시 종료
      minRemain = 0;
      return;
    }
    if (count == N) { // N번 쏜 경우
      minRemain = Math.min(minRemain, remain);
      return;
    }

    for (int idx = 0; idx < C; idx++) {
      int[][] nextMap = copyMap(curMap);
      shoot(nextMap, idx);
      chooseTarget(count + 1, nextMap);
    }
  }

  private static void shoot(int[][] curMap, int targetIdx) {
    int curCol = targetIdx;
    int curRow = findTopRow(curCol, curMap);
    if (curRow == R)
      return; // 벽돌 없으면 아무것도 안 함

    Queue<Pos> que = new ArrayDeque<>();
    que.offer(new Pos(curRow, curCol, curMap[curRow][curCol]));
    curMap[curRow][curCol] = 0;

    while (!que.isEmpty()) {
      Pos pos = que.poll();
      int curR = pos.row;
      int curC = pos.col;
      int curRange = pos.range;

      if (curRange == 1)
        continue;

      for (int dirIdx = 0; dirIdx < 4; dirIdx++) {
        for (int dist = 1; dist < curRange; dist++) {
          int nr = curR + dist * dr[dirIdx];
          int nc = curC + dist * dc[dirIdx];
          if (nr >= 0 && nr < R && nc >= 0 && nc < C && curMap[nr][nc] != 0) {
            int range = curMap[nr][nc];
            curMap[nr][nc] = 0;
            if (range > 1)
              que.offer(new Pos(nr, nc, range));
          }
        }
      }
    }

    // 벽돌 아래로 정렬
    for (int col = 0; col < C; col++) {
      int[] temp = new int[R];
      int tempIdx = R - 1;
      for (int row = R - 1; row >= 0; row--) {
        if (curMap[row][col] != 0) {
          temp[tempIdx--] = curMap[row][col];
        }
      }
      for (int row = 0; row < R; row++) {
        curMap[row][col] = temp[row];
      }
    }
  }

  private static int findTopRow(int col, int[][] snapShotMap) {
    int topIdx = R; //해당 열에 아무 벽돌이 없으면 (맨아래 행+1)
    for (int row = 0; row < R; row++) {
      if (snapShotMap[row][col] != 0) {
        topIdx = row;
        break;
      }
    }
    return topIdx;
  }

  private static int[][] copyMap(int[][] copy) {
    int[][] paste = new int[R][C];
    for (int i = 0; i < R; i++) {
      System.arraycopy(copy[i], 0, paste[i], 0, C);
    }
    return paste;
  }

  private static int countRemain(int[][] curMap) {
    int count = 0;
    for (int r = 0; r < R; r++) {
      for (int c = 0; c < C; c++) {
        if (curMap[r][c] != 0)
          count++;
      }
    }
    return count;
  }

}