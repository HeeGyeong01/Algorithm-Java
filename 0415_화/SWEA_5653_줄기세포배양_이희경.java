import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class SWEA_5653_줄기세포배양_이희경 {
  static int R, C, K;
  static int[] dr = { 1, 0, -1, 0 }; // 아래 <- 위 ->
  static int[] dc = { 0, -1, 0, 1 };
  static Map<Loc, Cell> live;
  static Map<Loc, Integer> dead;
  static StringBuilder sb = new StringBuilder();
  static StringTokenizer st;

  static class Loc {
    int row, col;

    public Loc(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;

      Loc loc = (Loc) o;
      return row == loc.row && col == loc.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }
  }

  static class Cell {
    int startTime, power, state;

    public Cell(int startTime, int power, int state) {
      this.startTime = startTime;
      this.power = power;
      this.state = state; // 0: 비활성, 1: 활성, 2: 죽음
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int T = Integer.parseInt(br.readLine().trim());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      R = Integer.parseInt(st.nextToken()); // 행 크기
      C = Integer.parseInt(st.nextToken()); // 열 크기 
      K = Integer.parseInt(st.nextToken()); // 배양 시간 

      live = new HashMap<>();
      dead = new HashMap<>();

      // 초기 cell 상태 입력받음
      for (int row = 0; row < R; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < C; col++) {
          int power = Integer.parseInt(st.nextToken());
          if (power != 0) {
            live.put(new Loc(row, col), new Cell(0, power, 0));
          }

        }
      }
      // K시간 후 살아있는 줄기세포 총 개수 
      int liveSum = simulate();

      // 정답: 최대한 많은 벽돌을 제거했을 때 남은 벽돌의 개수.
      sb.append('#').append(tc).append(' ').append(liveSum).append('\n');

    }

    System.out.println(sb);
  }

  // K시간에 걸쳐 줄기세포 번식
  private static int simulate() {
    // K시간 후
    for (int k = 1; k <= K; k++) {
      Map<Loc, Cell> addLive = new HashMap<>();
      List<Loc> removeLive = new ArrayList<>();

      for (Loc pos : live.keySet()) {

        int row = pos.row;
        int col = pos.col;
        int start = live.get(pos).startTime;
        int power = live.get(pos).power;
        int state = live.get(pos).state;

        // 죽은건 넘김
        if (state == 2) {
          continue;

        }

        // 이번 Step에 활성화 되는 경우
        if (state == 0 && (start + power) == k) {
          live.get(pos).state = 1; // -> 활성화 상태로 변경함.
        } else if (state == 1) { // 활성화 된 지 한 시간 지난 Cell 인 경우

          if (k == (start + power) + 1) {
            // 4방향 번식 진행
            for (int idx = 0; idx < 4; idx++) {
              int nr = row + dr[idx];
              int nc = col + dc[idx];

              Loc nextPos = new Loc(nr, nc);
              // 죽은거랑 활성화된 거 중에 해당 자리 차지하고 있는거 없을 때
              if (!live.containsKey(nextPos) && !dead.containsKey(nextPos)) {
                if (!addLive.containsKey(nextPos)) {
                  addLive.put(nextPos, new Cell(k, power, 0));
                }
                // 이번 턴에 추가된 다른거랑 위치가 겹치면 생명력 큰걸로 덮어쓰기
                else if (addLive.containsKey(nextPos) && addLive.get(nextPos).power < power) {

                  addLive.put(nextPos, new Cell(k, power, 0));
                }
              }
            }
          }

          if (k == (start + power * 2)) {
            // 활성화 -> 죽음
            live.get(pos).state = 2;
            removeLive.add(pos);
            dead.put(pos, power);
          }

        }
      }

      // live에 일괄 더함
      live.putAll(addLive);

      // live에 일괄 삭제
      for (Loc p : removeLive) {
        live.remove(p);
      }

    }
    // K시간 후 살아있는 줄기세포 총 개수 
    return live.size();

  }

}