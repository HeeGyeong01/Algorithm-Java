import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. grid 2차원 배열 입력받으며 cctv 객체를 cctvList에 저장함.
 * 2. 각 cctv가 어느 방향을 바라볼지에 대한 모든 조합 생성.
 * 3. 각 조합에 대한 해당 cctv가 어느 방향을 바라볼지에 대한 정보를 가지고
 *    visited 정보 업데이트.
 * 4. 사각지대 칸 수 계산 -> minimum값 업데이트.
 */
public class BOJ_15683_감시_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static int n, m, minBlind, cctvCount;
  static int[][] grid;
  static boolean[][] visited;
  static cctv[] cctvList;
  static int[] cctvDirCase = { 0, 4, 2, 4, 4, 1 }; // 타입별 cctv의 방향의 경우의 수
  static int[][] type2 = {};
  static int[] dr = { 0, 1, 0, -1 }; // -> ↓ <- ↑
  static int[] dc = { 1, 0, -1, 0 };
  static int[] directions;

  static class cctv {
    int type, row, col;

    public cctv(int type, int row, int col) {
      this.type = type;
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    n = Integer.parseInt(st.nextToken()); // 행
    m = Integer.parseInt(st.nextToken()); // 열
    grid = new int[n][m]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
    minBlind = n * m + 1; // 최소 사각지대 칸 수
    cctvList = new cctv[8]; // cctv 정보 배열
    cctvCount = 0; // 1~5 타입의 cctv 개수
    visited = new boolean[n][m]; // 방문 확인 배열 초기화

    // grid 상태 입력받음
    for (int row = 0; row < n; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < m; col++) {
        int room = Integer.parseInt(st.nextToken());
        grid[row][col] = room;
        if (room != 0 && room != 6) {
          cctvList[cctvCount++] = new cctv(room, row, col);
        }
      }
    }

    directions = new int[cctvCount];
    comb(0);

    System.out.println(minBlind);

  }

  // cctv 방향 경우의 수 조합
  public static void comb(int index) {
    if (index == cctvCount) {
      visited = new boolean[n][m]; // 방문 확인 배열 초기화
      observeAll();
      return;
    }

    for (int i = 0; i < cctvDirCase[cctvList[index].type]; i++) {
      directions[index] = i;
      comb(index + 1);
    }
  }

  // 모든 cctv의 위치, 타입(방향)을 가지고 감시 실행.
  public static void observeAll() {
    for (int cctvIdx = 0; cctvIdx < cctvCount; cctvIdx++) {
      cctv cctv = cctvList[cctvIdx];

      switch (cctv.type) {
      case 1:
        observe(directions[cctvIdx], cctv.row, cctv.col);
        break;

      case 2:
        observe(directions[cctvIdx], cctv.row, cctv.col);
        observe((directions[cctvIdx] + 2) % 4, cctv.row, cctv.col);
        break;

      case 3:
        observe(directions[cctvIdx], cctv.row, cctv.col);
        observe((directions[cctvIdx] + 1) % 4, cctv.row, cctv.col);
        break;

      case 4:
        observe(directions[cctvIdx], cctv.row, cctv.col);
        observe((directions[cctvIdx] + 1) % 4, cctv.row, cctv.col);
        observe((directions[cctvIdx] + 2) % 4, cctv.row, cctv.col);
        break;
      case 5:
        observe(0, cctv.row, cctv.col);
        observe(1, cctv.row, cctv.col);
        observe(2, cctv.row, cctv.col);
        observe(3, cctv.row, cctv.col);
        break;

      default:
        break;
      }
    }
    calBlindNum();
  }

  // 해당 cctv의 위치, 4방향을 가지고 감시 실행. / dirIdx = 0,1,2,3 중 하나
  public static void observe(int dirIdx, int row, int col) {
    int nr = row;
    int nc = col;

    while (true) {
      nr = nr + dr[dirIdx];
      nc = nc + dc[dirIdx];

      // 해당 인덱스가 grid[][] 범위 안인지 확인
      boolean inBounds = nr >= 0 && nr < n && nc >= 0 && nc < m;
      if (inBounds && grid[nr][nc] != 6) { // grid 안이고, 벽을 만나지 않은 경우
        visited[nr][nc] = true;
      } else {
        break;
      }
    }

  }

  // 사각지대 칸 수 계산
  public static void calBlindNum() {
    int blindSpot = 0;
    for (int row = 0; row < n; row++) {
      for (int col = 0; col < m; col++) {
        int type = grid[row][col];
        if (type == 0 && !visited[row][col]) {
          blindSpot += 1;
        }
      }
    }

    minBlind = Math.min(minBlind, blindSpot);

  }

}