import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 초기 grid 상태 입력받음
 * 2. 방향에 따라 타일 이동시킴
 *    2-1. 전달받은 command에 따라 switch문으로 나눠 다른 로직 적용함.
 * 3. 출력용으로 만들어 놓은 2차원 배열 printGrid 출력함.
 */
public class SWEA_6109_추억의2048게임_이희경 {
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;
  static int N;
  static int[][] grid, printGrid;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 행과 열 수
      String command = st.nextToken(); // 방향 명령어
      sb.append('#').append(tc).append('\n');

      grid = new int[N][N]; // 입력으로 받는 타일 번호를 저장할 2차원 배열
      printGrid = new int[N][N]; // 출력용 2차원 배열을 초기화 디폴트로 0들어감.

      // 1. 초기 grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          grid[row][col] = Integer.parseInt(st.nextToken());

        }
      }

      // 2. 입력으로 주어진 방향대로 타일 이동시킴.
      swipe(command);

      // 3. 정답으로 printGrid 출력함.
      for (int row = 0; row < N; row++) {
        for (int col = 0; col < N; col++) {
          sb.append(printGrid[row][col]).append(' ');
        }
        sb.append('\n');
      }

    }

    System.out.println(sb);

  }

  // 4 방향 중 한 방향으로 스와이프 하고 합칠것들 합치는 메소드
  public static void swipe(String cmd) {

    switch (cmd) {
    case "left":
      for (int row = 0; row < N; row++) {
        int validCount = 0; // 유효한 숫자 타일 -> 출력함
        int prevNum = 0; // 유효한 숫자 타일 중 바로 이전의 숫자타일

        for (int col = 0; col < N; col++) {
          if (grid[row][col] == 0) {
            continue; // 타일의 값이 0인 경우 건너뛴다.
          }
          if (prevNum != 0) { // 직전의 타일이 0이 아닌 경우
            if (prevNum == grid[row][col]) { //직전의 타일이 현재 타일과 값이 같은 경우
              printGrid[row][validCount++] = prevNum * 2; // printGrid에 현재값*2를 넣고,직전타일을 0으로 초기화한다.
              prevNum = 0;
            } else if (prevNum != grid[row][col]) { // 직전 타일이 현재 타일과 값이 다른 경우
              printGrid[row][validCount++] = prevNum; // printGrid에 현재값을 넣고,직전타일의 값을 현재타일로 바꾼다..
              prevNum = grid[row][col];
            }
          } else if (prevNum == 0 && grid[row][col] != 0) {
            prevNum = grid[row][col]; // 직전타일이 0이고 현재 타일이 0이 아닌 경우 -> 직전타일의 값을 현재타일로 바꾼다.
          }
          printGrid[row][validCount] = prevNum; //row별 마지막에 남는 수를 printGrid에 넣음.
        }
      }
      break;

    // 이하의 right / up / down 도 위와 같은 로직으로 하되 row와 col 반복문의 순서를 바꾼다.
    case "right":
      for (int row = 0; row < N; row++) {
        int validCount = N - 1;
        int prevNum = 0;

        for (int col = N - 1; col >= 0; col--) {
          if (grid[row][col] == 0) {
            continue;
          }
          if (prevNum != 0) {
            if (prevNum == grid[row][col]) {
              printGrid[row][validCount--] = prevNum * 2;
              prevNum = 0;
            } else if (prevNum != grid[row][col]) {
              printGrid[row][validCount--] = prevNum;
              prevNum = grid[row][col];
            }
          } else if (prevNum == 0 && grid[row][col] != 0) {
            prevNum = grid[row][col];
          }
          printGrid[row][validCount] = prevNum; // 마지막에 남는 수 printGrid에 넣음.
        }
      }
      break;

    case "up":
      for (int col = 0; col < N; col++) {
        int validCount = 0;
        int prevNum = 0;

        for (int row = 0; row < N; row++) {
          if (grid[row][col] == 0) {
            continue;
          }
          if (prevNum != 0) {
            if (prevNum == grid[row][col]) {
              printGrid[validCount++][col] = prevNum * 2;
              prevNum = 0;
            } else if (prevNum != grid[row][col]) {
              printGrid[validCount++][col] = prevNum;
              prevNum = grid[row][col];
            }
          } else if (prevNum == 0 && grid[row][col] != 0) {
            prevNum = grid[row][col];
          }
          printGrid[validCount][col] = prevNum; // 마지막에 남는 수 printGrid에 넣음.
        }
      }
      break;

    case "down":
      for (int col = 0; col < N; col++) {
        int validCount = N - 1;
        int prevNum = 0;

        for (int row = N - 1; row >= 0; row--) {
          if (grid[row][col] == 0) {
            continue;
          }
          if (prevNum != 0) {
            if (prevNum == grid[row][col]) {
              printGrid[validCount--][col] = prevNum * 2;
              prevNum = 0;
            } else if (prevNum != grid[row][col]) {
              printGrid[validCount--][col] = prevNum;
              prevNum = grid[row][col];
            }
          } else if (prevNum == 0 && grid[row][col] != 0) {
            prevNum = grid[row][col];
          }
          printGrid[validCount][col] = prevNum; // 마지막에 남는 수 printGrid에 넣음.
        }
      }
      break;
    }
  }
}
