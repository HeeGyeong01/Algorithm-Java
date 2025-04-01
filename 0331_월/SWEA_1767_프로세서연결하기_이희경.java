import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 초기 격자 상태 입력 받아 grid[][]에 저장함.
 *    - 가장자리에 위치하지 않은 core는 coreList에 추가.
 * 2. 모든 코어에 대해 4방향 전선 연결 조합 생성.
 *    2-1. 모든 코어에 대해 방향 결정되면 
 *         - 그 조합에 대해 전선 연결 시도.
 *         - 경로에 코어나 전선 없으면 해당 전선 길이 반환.
 *    2-2. 연결 성공 시 Max코어 수와 Min전선 길이 갱신.
 *         - 실패 시 백트래킹으로 visited 원상복구.
 * 3. Max 코어 연결 경우에 대한 Min 전선 길이 출력.
 */
public class SWEA_1767_프로세서연결하기_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int coreCount, N, maxConnectCore, minWireLength;
  static int[][] grid;
  static boolean[][] visited;
  static List<Core> coreList;
  static int[] dr = { 0, 1, 0, -1 }; // 4방
  static int[] dc = { 1, 0, -1, 0 };

  // 코어 정보 객체
  static class Core {
    int dir, row, col;

    public Core(int dir, int row, int col) {
      this.dir = dir;
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine().trim()); // 멕시노스의 행과 열
      grid = new int[N][N]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
      coreList = new ArrayList<>(); // core 정보 배열
      maxConnectCore = 0; // 최대 연결 코어 수
      minWireLength = Integer.MAX_VALUE; // 최소 전선 길이

      // grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          int room = Integer.parseInt(st.nextToken());
          grid[row][col] = room;
          // 가장자리에 위치하지 않은 코어인 경우
          if (room == 1 && (row > 0 && row < N - 1 && col > 0 && col < N - 1)) {
            coreList.add(new Core(-1, row, col));
          }
        }
      }

      coreCount = coreList.size(); // 가장자리에 위치하지 않은 코어들 개수
      visited = new boolean[N][N]; // 방문 확인 배열 초기화
      comb(0, 0, 0); // 조합 

      sb.append('#').append(tc).append(' ').append(minWireLength).append('\n');
    }
    System.out.println(sb);
  }

  // core 전선 방향 경우의 수 조합
  public static void comb(int index, int connectCore, int totalWireLength) {
    // 남은 코어 다 연결해도 maxConnectCore 미만이면 리턴.
    if (connectCore + (coreCount - index) < maxConnectCore)
      return;

    // 모든 core들의 방향이 결정된 경우
    if (index == coreCount) {
      if (connectCore > maxConnectCore) { // 더 많은 코어 연결 시 업데이트
        maxConnectCore = connectCore;
        minWireLength = totalWireLength;
      } else if (connectCore == maxConnectCore) { // 연결된 코어 수 같으면 최소 전선 길이 선택
        minWireLength = Math.min(minWireLength, totalWireLength);
      }
      return;
    }

    Core c = coreList.get(index);
    // 4방향 시도 + 연결 안 하는 경우
    for (int dirIdx = 0; dirIdx <= 4; dirIdx++) {
      if (dirIdx == 4) { // 연결 안 하는 경우
        comb(index + 1, connectCore, totalWireLength);
      } else {
        c.dir = dirIdx;
        int wireLength = connect(c.row, c.col, dirIdx);
        if (wireLength >= 0) { // 연결 성공 시
          comb(index + 1, connectCore + 1, totalWireLength + wireLength);
          disconnect(c.row, c.col, dirIdx); // 연결한 전선 원상복구
        } else { // 연결 실패 시 다음 방향 시도
          comb(index + 1, connectCore, totalWireLength);
        }
      }
    }
  }

  // 해당 코어의 위치와 방향으로 전선 연결 시도, 성공 시 길이 반환, 실패 시 -1
  public static int connect(int row, int col, int dir) {
    int nr = row, nc = col, wireLength = 0;

    while (true) {
      nr += dr[dir];
      nc += dc[dir];
      // 경계 도달 시 연결 성공
      if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
        // 전선 연결 경로를 바로 visited에 표시 
        nr = row;
        nc = col;
        while (true) {
          nr += dr[dir];
          nc += dc[dir];
          if (nr < 0 || nr >= N || nc < 0 || nc >= N)
            break;
          visited[nr][nc] = true;
        }
        return wireLength;
      }
      // 코어나 기존 전선 만나면 실패
      if (grid[nr][nc] == 1 || visited[nr][nc])
        return -1;
      wireLength++;
    }
  }

  // 전선 연결 해제
  public static void disconnect(int row, int col, int dir) {
    int nr = row, nc = col;
    while (true) {
      nr += dr[dir];
      nc += dc[dir];
      if (nr < 0 || nr >= N || nc < 0 || nc >= N)
        break;
      visited[nr][nc] = false; // 방문 해제
    }
  }
}