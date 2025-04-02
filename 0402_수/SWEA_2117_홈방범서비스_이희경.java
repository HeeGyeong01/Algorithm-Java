import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 1. grid 입력받음
 *    - 입력받으며 집 갯수 셈
 * 2. (setService) 서비스를 설치할 격자 위치와 크기(k)를 지정함
 * 3. 정해진 각 위치와 크기를 가지고 계산하여 서비스 가능한 집 수를 리턴함
 * 4. 서비스 가능한 집 수 최대값을 업데이트.
 */

public class SWEA_2117_홈방범서비스_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int N, M, homeSum, maxHome;
  static int[][] grid;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine().trim());
      N = Integer.parseInt(st.nextToken()); // grid의 행과 열
      M = Integer.parseInt(st.nextToken()); // 하나의 집이 지불할 수 있는 비용 

      grid = new int[N][N]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
      homeSum = 0; // 총 집 수
      maxHome = 0; // 서비스 제공받는 최대 집 수

      // grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          int room = Integer.parseInt(st.nextToken());
          grid[row][col] = room;
          if (room == 1) {
            homeSum += 1;
          }
        }
      }

      setService(); // 서비스를 설치할 위치와 크기를 정함

      sb.append('#').append(tc).append(' ').append(maxHome).append('\n');
    }
    System.out.println(sb);
  }

  // 서비스를 설치할 위치와 크기를 정함
  private static void setService() {

    // 서비스 설치
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < N; col++) {
        // // 서비스 크기 1 ~ (현재 위치와 4 꼭짓점 중 가장 긴 맨해튼 거리)
        int[] curManLength = new int[] { Math.abs(row) + Math.abs(col), Math.abs(row - N + 1) + Math.abs(col),
            Math.abs(row) + Math.abs(col - N + 1), Math.abs(row - N + 1) + Math.abs(col - N + 1) };
        Arrays.sort(curManLength);
        for (int k = 1; k <= curManLength[3] + 1; k++) {
          //정해진 위치와 크기를 가지고 서비스가능한 집 수를 리턴함
          int validHome = countHome(row, col, k);
          maxHome = Math.max(maxHome, validHome);
          // 최대로 집 다 커버할 수 있으면 계산 끝냄.
          if (validHome == homeSum) {
            return;
          }
        }
      }
    }

  }

  //정해진 위치와 크기를 가지고 서비스가능한 집 수를 리턴함
  private static int countHome(int row, int col, int k) {
    int operationCost = k * k + (k - 1) * (k - 1);
    int homeCount = 0;
    int startRow = row - k + 1 < 0 ? 0 : row - k + 1;
    int startCol = col - k + 1 < 0 ? 0 : col - k + 1;
    int endRow = row + k - 1 >= N ? N - 1 : row + k - 1;
    int endCol = col + k - 1 >= N ? N - 1 : col + k - 1;

    for (int r = startRow; r <= endRow; r++) {
      for (int c = startCol; c <= endCol; c++) {

        boolean inBounds = r >= 0 && r < N && c >= 0 && c < N;
        int manLength = Math.abs(row - r) + Math.abs(col - c);// 맨해튼 거리 계산

        if (inBounds && manLength < k && grid[r][c] == 1) { // 범위 안에 있고, 맨해튼 거리 조건 만족하고, 집이 있는 경우
          homeCount += 1;
        }
      }
    }

    return homeCount * M >= operationCost ? homeCount : -1;
  }

}