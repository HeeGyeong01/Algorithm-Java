import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * n*n 크기의 2차원 배열에 1~n*n의 수를 채워넣는 문제.
 * 
 * 0. [init] 아래, 왼쪽, 위, 오른쪽 순서대로의 이동방향을 dr, dc에 저장.
 * 1. 맨 위 행을 1~n까지 채워넣는다.
 * 2. n+1 ~ n*n까지 순회한다.
 *  2-1. 기존 방향대로 간 다음 칸이 1)범위 밖이거나 2)숫자가 이미 채워진 칸이면 -> 다음 방향으로 바꾼다.
 *  2-2. 다음 칸에 숫자를 저장한다.
 */

public class SWEA_1954_달팽이숫자_이희경 {
  static int snailWidth;
  static int[] dr = { 1, 0, -1, 0 }; // 아래 <- 위 ->
  static int[] dc = { 0, -1, 0, 1 };
  static int[][] map;
  static int directionIdx;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      sb.append('#').append(tc).append('\n');
      snailWidth = Integer.parseInt(br.readLine());
      map = new int[snailWidth][snailWidth];
      directionIdx = 0;
      int curRow = 0, curCol = snailWidth - 1;
      // 첫번째 줄
      for (int idx = 0; idx < snailWidth; idx++) {
        map[0][idx] = idx + 1;
      }

      // 아래로 꺾는 것부터 시작함.
      for (int n = snailWidth + 1; n <= snailWidth * snailWidth; n++) {
        // 기존 방향대로 간 다음 칸이 범위 안이 아니면 다음 방향으로 바꾼다.
        if (!inBounds(curRow + dr[directionIdx], curCol + dc[directionIdx])) {
          directionIdx = (directionIdx + 1) % 4;
        }
        // 다음 칸에 숫자를 저장한다.
        curRow += dr[directionIdx];
        curCol += dc[directionIdx];
        map[curRow][curCol] = n;
      }

      // map[][] 배열 출력
      for (int i = 0; i < snailWidth; i++) {
        for (int j = 0; j < snailWidth; j++) {
          sb.append(map[i][j]).append(' ');
        }
        sb.append('\n');
      }
    }

    System.out.println(sb);
  }

  // 해당 인덱스가 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < snailWidth && col >= 0 && col < snailWidth && map[row][col] == 0) {
      return true;
    }
    return false;

  }
}
