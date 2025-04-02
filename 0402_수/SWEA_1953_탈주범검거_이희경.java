import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BFS 알고리즘 사용.
 * time[][]에 각 터널마다 걸린 시간 저장
 * possibleDir에 각 터널마다 4방 중 연결할 수 있는 방향 여부 boolean타입으로 저장.
 * L 시간 이하인 터널은 possibleTunSum에 +1해줌.
 * possibleTunSum 출력.
 */
public class SWEA_1953_탈주범검거_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int N, M, R, C, L, possibleTunSum;
  static int[][] grid, time;
  static int[] dr = { -1, 0, 1, 0 }; // 위, 오른쪽, 아래, 왼쪽
  static int[] dc = { 0, 1, 0, -1 };
  static boolean[][] possibleDir = { {}, { true, true, true, true }, { true, false, true, false },
      { false, true, false, true }, { true, true, false, false }, { false, true, true, false },
      { false, false, true, true }, { true, false, false, true } }; //행: 터널타입 1~7, 열: 위/오/아/왼 4방 연결 여부

  static class Tun {
    int row, col, type, time;

    public Tun(int row, int col, int type, int time) {
      this.row = row;
      this.col = col;
      this.type = type;
      this.time = time;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine().trim());
      N = Integer.parseInt(st.nextToken()); // grid의 행
      M = Integer.parseInt(st.nextToken()); // grid의 열
      R = Integer.parseInt(st.nextToken()); // 맨홀 뚜껑 행 
      C = Integer.parseInt(st.nextToken()); // 맨홀 뚜껑 열
      L = Integer.parseInt(st.nextToken()); // 탈출 후 소요된 시간

      grid = new int[N][M]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
      time = new int[N][M]; // 방문 확인 배열
      possibleTunSum = 1;

      // grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < M; col++) {
          int room = Integer.parseInt(st.nextToken());
          grid[row][col] = room;
        }
      }

      run(R, C); // 1시간후 맨홀뚜껑에서 탈주 시작
      sb.append('#').append(tc).append(' ').append(possibleTunSum).append('\n');
    }
    System.out.println(sb);
  }

  // 1시간후 맨홀뚜껑에서 탈주 시작 (bfs)
  private static void run(int startRow, int startCol) {

    Queue<Tun> que = new ArrayDeque<>();
    que.offer(new Tun(startRow, startCol, grid[startRow][startCol], 1));
    time[startRow][startCol] = 1;

    while (!que.isEmpty()) {
      Tun t = que.poll();

      for (int dir = 0; dir < 4; dir++) {
        int nr = t.row + dr[dir];
        int nc = t.col + dc[dir];
        boolean inBounds = nr >= 0 && nr < N && nc >= 0 && nc < M;

        // 다음 칸이 범위 안에 있고, 터널이고, 방문한 적 없고, 서로 연결할 수 있는 터널방향인 경우
        if (inBounds && grid[nr][nc] != 0 && time[nr][nc] == 0 && possibleDir[t.type][dir]
            && possibleDir[grid[nr][nc]][(dir + 2) % 4]) {
          time[nr][nc] = t.time + 1;
          que.offer(new Tun(nr, nc, grid[nr][nc], t.time + 1));
          possibleTunSum += t.time + 1 <= L ? 1 : 0; // 리밋 시간 이하면 +1해줌.
        }
      }

    }

  }

}