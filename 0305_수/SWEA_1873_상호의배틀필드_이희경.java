import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * 1. 초기 grid 정보를 2차원 배열로 입력받음
 *    (방향은 dr/dc의 인덱스값으로 바꿔서 curDirIdx에 저장함)
 * 2. 입력으로 주어짐 방향대로 전차를 이동시킴
 *    2-1. '포 발사' 명령인 경우 현재 방향대로 다음칸 들이 
 *          벽돌로 만든 벽에 충돌하는 경우 -> 벽 파괴되서 평지로 바뀌고 포탄 멈춤. 
 *          범위를 벗어낫거나 강철로 만든 벽에 닿는 경우 -> 포탄 멈춤.
 *    2-2. '방향 전환 후 한 칸 전진' 명령인 경우 
 *          해당 방향으로 한 칸 간 결과가 grid[][] 범위 안이고 평지인 경우 -> 전진
 * 3. 정답으로 grid[][] 출력함.
 */
public class SWEA_1873_상호의배틀필드_이희경 {
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;
  static int H, W, curRow, curCol;
  static char[][] grid;
  static char[] commands;
  static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 }; //위, 아래, 왼, 오
  static HashMap<Character, Integer> dirToIdx = new HashMap<>();
  static int curDirIdx; // 현재 전차가 바라보고 있는 방향

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());
    dirToIdx.put('U', 0); // 방향을 나타내는 문자를 dr/dc의 인덱스로 매핑.
    dirToIdx.put('D', 1);
    dirToIdx.put('L', 2);
    dirToIdx.put('R', 3);

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      H = Integer.parseInt(st.nextToken()); // 행
      W = Integer.parseInt(st.nextToken()); // 열

      sb.append('#').append(tc).append(' ');

      grid = new char[H][W]; // 입력으로 받는 grid 정보를 저장할 2차원 배열

      // 1. 초기 grid 상태 입력받음
      for (int row = 0; row < H; row++) {
        String str = br.readLine();
        for (int col = 0; col < W; col++) {
          grid[row][col] = str.charAt(col);
          // 초기 전차의 위치 저장
          if (grid[row][col] == '^' || grid[row][col] == 'v' || grid[row][col] == '<' || grid[row][col] == '>') {
            curRow = row;
            curCol = col;

            if (grid[row][col] == '^') {
              curDirIdx = 0;
            } else if (grid[row][col] == 'v') {
              curDirIdx = 1;
            } else if (grid[row][col] == '<') {
              curDirIdx = 2;
            } else if (grid[row][col] == '>') {
              curDirIdx = 3;
            }
            grid[row][col] = '.';
          }
        }
      }

      int N = Integer.parseInt(br.readLine()); // command 입력의 개수
      String str = br.readLine();
      for (int count = 0; count < N; count++) {
        // 2. 입력으로 주어진 방향대로 전차 이동시킴.
        play(str.charAt(count));
      }

      // 마지막 전차의 위치와 방향을 저장.
      if (curDirIdx == 0) {
        grid[curRow][curCol] = '^';
      } else if (curDirIdx == 1) {
        grid[curRow][curCol] = 'v';
      } else if (curDirIdx == 2) {
        grid[curRow][curCol] = '<';
      } else if (curDirIdx == 3) {
        grid[curRow][curCol] = '>';
      }

      // 3. 정답으로 grid[][] 출력함.
      for (int row = 0; row < H; row++) {
        for (int col = 0; col < W; col++) {
          sb.append(grid[row][col]);
        }
        sb.append('\n');
      }

    }

    System.out.println(sb);

  }

  // 전차를 이동하고 포를 쏘게 하는 메소드
  public static void play(char cmd) {
    // 방향전환 명령인 경우 -> 현재 전차의 방향 바꿈.
    if (cmd != 'S') {
      curDirIdx = dirToIdx.get(cmd);
    }

    switch (cmd) {
    // 포 발사
    case 'S':
      for (int cnt = 1;; cnt++) {
        int nextRow = curRow + cnt * dr[curDirIdx];
        int nextCol = curCol + cnt * dc[curDirIdx];
        if (inBounds(nextRow, nextCol) && grid[nextRow][nextCol] == '*') {
          // 벽돌로 만든 벽에 충돌하는 경우 -> 벽 파괴되서 평지로 바뀌고 포탄 멈춤. 
          grid[nextRow][nextCol] = '.';
          break;
        }
        // 범위를 벗어낫거나 강철로 만든 벽에 닿는 경우 -> 포탄 멈춤.
        if (!inBounds(nextRow, nextCol) || grid[nextRow][nextCol] == '#') {
          break;
        }
      }
      break;
    case 'U':
    case 'D':
    case 'L':
    case 'R':
      // 위, 아래, 왼, 오 방향전환, 한 칸 전진 
      int nextRow = curRow + dr[curDirIdx];
      int nextCol = curCol + dc[curDirIdx];
      // 해당 방향으로 한 칸 간 결과가 grid[][] 범위 안이고 평지인 경우
      if (inBounds(nextRow, nextCol) && grid[nextRow][nextCol] == '.') {
        curRow = nextRow;
        curCol = nextCol;
      }
      break;

    }
  }

  // 해당 인덱스가 grid[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < H && col >= 0 && col < W) {
      return true;
    }
    return false;

  }

}
