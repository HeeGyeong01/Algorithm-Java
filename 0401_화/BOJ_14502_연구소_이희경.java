import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 1. 격자 정보를 입력받아 2차원 배열에 저장.
 *    - 바이러스는 virusList에 추가.
 * 2. 빈칸에서 벽 3개를 세우는 모든 조합을 탐색.
 *    2-1. 벽 3개를 모두 세웠으면 바이러스 전파를 시뮬레이션한다.
 *    2-2. BFS로 바이러스를 4방으로 퍼뜨린다(벽과 경계 밖인지 고려)
 * 3. 안전 영역 크기 계산해 최대값 업데이트
 */
public class BOJ_14502_연구소_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static int N, M, maxSafe;
  static int[][] grid;
  static boolean[][] visited;
  static List<virus> virusList;
  static int[] dr = { 0, 1, 0, -1 };
  static int[] dc = { 1, 0, -1, 0 };

  static class virus {
    int row, col;

    public virus(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken()); // 행
    M = Integer.parseInt(st.nextToken()); // 열
    grid = new int[N][M]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
    maxSafe = 0; // 최대 안전지대 칸 수 초기화
    virusList = new ArrayList<>(); // virus 정보 배열

    // grid 상태 입력받음
    for (int row = 0; row < N; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < M; col++) {
        int value = Integer.parseInt(st.nextToken());
        grid[row][col] = value;
        if (value == 2) { //바이러스가 있는 칸인 경우
          virusList.add(new virus(row, col));
        }
      }
    }

    comb(0, 0);

    System.out.println(maxSafe);

  }

  // 3개의 벽 세우기 위해 빈칸 3개 뽑는 경우의 수 조합
  public static void comb(int index, int count) {

    // 벽 3개 다 정한 경우
    if (count == 3) {
      spread();
      return;
    }

    // 끝까지 다 고려한 경우
    if (index == N * M) {
      return;
    }
    // 재귀 호출
    for (int nextIdx = index; nextIdx < N * M; nextIdx++) { // 0도 고려해야 함.
      // 해당 칸이 벽이나 바이러스가 있는 칸인 경우
      if (grid[nextIdx / M][nextIdx % M] == 1 || grid[nextIdx / M][nextIdx % M] == 2) {
        continue;
      }
      grid[nextIdx / M][nextIdx % M] = 1;
      comb(nextIdx + 1, count + 1);
      grid[nextIdx / M][nextIdx % M] = 0;
    }
  }

  // 새로 새운 벽 3개의 위치 가지고 전파 시행
  public static void spread() {
    visited = new boolean[N][M]; // 방문 확인 배열 초기화

    Queue<virus> que = new ArrayDeque<>();
    // 초기 바이러스들 다 큐에 집어넣음
    for (virus v : virusList) {
      que.add(v);
    }

    // 바이러스 전파 실행 위해 bfs 시행.
    while (!que.isEmpty()) {
      virus v = que.poll();

      for (int dir = 0; dir < 4; dir++) { // 4방에 있는 빈칸들 큐에 넣음
        int nr = v.row + dr[dir];
        int nc = v.col + dc[dir];

        boolean inBounds = nr >= 0 && nr < N && nc >= 0 && nc < M; // 해당 인덱스가 grid[][] 범위 안인지 확인
        if (inBounds && grid[nr][nc] == 0 && !visited[nr][nc]) { // grid 안이고, 빈칸이고, 이미 방문한 칸이 아닌 경우
          visited[nr][nc] = true;
          que.add(new virus(nr, nc));
        }
      }
    }
    calSafeRoom();
  }

  // 안전영역 칸 수 계산
  public static void calSafeRoom() {
    int safeSpot = 0;
    for (int row = 0; row < N; row++) {
      for (int col = 0; col < M; col++) {
        if (grid[row][col] == 0 && !visited[row][col]) {
          safeSpot += 1;
        }
      }
    }

    maxSafe = Math.max(maxSafe, safeSpot);

  }

}