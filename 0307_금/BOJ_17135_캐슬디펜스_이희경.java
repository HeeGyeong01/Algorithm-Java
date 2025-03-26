import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 초기 originBoard 정보를 2차원 배열로 입력받음
 * 2. 궁수 3명이 어느 열에 배치될지에 대한 조합 구함
 *    2-1. 궁수 3명 자리 다 정한 경우 play()로 자리 정보 넘겨서 몇명 kill했는지 받음.
 *         -> maxKill 값 업데이트
 *    2-2. 마지막 열까지 다 고려한 경우 return;
 *    2-3. 현재 열을 선택한 후 재귀 호출
 *    2-4. 현재 열을 선택 안하고 재귀 호출
 * 3. play() : 죽인 적의 수 리턴. (정해진 3명의 궁수의 자리에 대해 끝까지 play)
 *    3-1. originBoard 복사해서 board에 저장.
 *    3-2. 게임이 끝날때까지 라운드별 진행.
 *          - 각 궁수마다 findTarget()으로 죽인 타겟 정보 받아와서 List<Enemy> targetList에 add함(중복 고려됨)
 *          - targetList에 저장된 적들 board[][]에 1의 값을 0으로 바꾸고 killCount +1함.
 * 4. findTarget() : 한 궁수가 최종적으로 쏠 적을 return함.
 *    4-1. 모든 칸을 순회하며 가장 가깝고 & 왼쪽인 적 찾기 (줄어든 행 고려)
 *    4-2. 적이 있는 칸이면   
 *         - 공격 가능거리 이하이고 처음 찾은 적이거나, 더 가까운 적이거나, 거리가 같은데 더 왼쪽에 있는 적인 경우
 *         - 가장 까운 거리와 가까운 적 업데이트.
 *    4-3. kill한 적 return;
 */
public class BOJ_17135_캐슬디펜스_이희경 {
  static StringTokenizer st;
  static BufferedReader br;
  static int R, C, D, maxKill;
  static int[][] originBoard;
  static boolean[][] visited;
  static int[] dr = { 0, -1, 0 }, dc = { -1, 0, 1 }; // 왼, 위, 오 3방향

  static class Enemy { // 적이 있는 칸의 좌표값 저장하는 클래스
    int row;
    int col;

    Enemy(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    R = Integer.parseInt(st.nextToken()); // 행
    C = Integer.parseInt(st.nextToken()); // 열 
    D = Integer.parseInt(st.nextToken()); // 궁수의 공격거리 제한
    originBoard = new int[R][C];

    // 1. 초기 originBoard 상태 입력받음
    for (int row = 0; row < R; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < C; col++) {
        originBoard[row][col] = Integer.parseInt(st.nextToken());
      }
    }

    maxKill = 0;

    // 2. 궁수 3명 배치 -> 조합 이용
    makeComb(0, 0, 0);

    // 3. 정답 출력
    System.out.println(maxKill);

  }

  // 2. 궁수 3명의 column 좌표 정함
  public static void makeComb(int curCol, int archerCount, int archerLoc) {
    // 궁수 3명자리 다 정한 경우
    if (archerCount == 3) {
      maxKill = Math.max(maxKill, play(archerLoc));
      return;
    }

    // 마지막 열까지 다 고려한 경우
    if (curCol >= C) {
      return;
    }

    // 현재 열을 선택 O
    makeComb(curCol + 1, archerCount + 1, archerLoc | (1 << curCol));
    // 현재 열을 선택 X
    makeComb(curCol + 1, archerCount, archerLoc);
  }

  // 죽인 적의 수 리턴. (정해진 3명의 궁수의 자리에 대해 끝까지 play)
  public static int play(int archerColInfo) {
    // originBoard 복사
    int[][] board = new int[R][C];
    for (int i = 0; i < R; i++) {
      System.arraycopy(originBoard[i], 0, board[i], 0, C);
    }

    // 궁수 열 위치 받음
    int[] archerCols = new int[3];
    int archerIdx = 0;
    for (int col = 0; col < C; col++) {
      if ((archerColInfo & (1 << col)) > 0) {
        archerCols[archerIdx++] = col;
      }
    }

    // 이번 궁수3명의 자리 조합에서 총 죽이는 적의 수
    int killCount = 0;

    // 라운드별 진행 
    for (int round = 0; round < R; round++) {
      // 이번 라운드에 제거할 적들의 위치
      List<Enemy> targetList = new ArrayList<>();

      // 각 궁수마다 타겟 찾기
      for (int archerCol : archerCols) {
        Enemy target = findTarget(board, R - round, archerCol);
        if (target != null) {
          // 궁수가 쏜 적을 중복을 허용해서 저장.
          targetList.add(target);
        }
      }

      // 제거할 적들 처리 (중복 고려O)
      for (Enemy target : targetList) {
        if (board[target.row][target.col] == 1) { // 아직 제거되지 않은 적만 카운트
          board[target.row][target.col] = 0;
          killCount++;
        }
      }

      //  R-round-1 까지만 계산해서 맵의 크기를 점점 줄임. -> 궁수가 앞으로 가는 것과 같음
    }

    return killCount;
  }

  // 한 궁수가 최종적으로 쏠 적을 return함.
  private static Enemy findTarget(int[][] board, int archerRow, int archerCol) {
    int minDistance = Integer.MAX_VALUE;
    Enemy closestEnemy = null;

    // 적 찾기 (모든 칸을 순회하며 가장 가깝고 & 왼쪽인 적 찾기)
    for (int r = 0; r < archerRow; r++) { // 궁수 위치 바로 위 행까지 검사
      for (int c = 0; c < C; c++) {
        if (board[r][c] == 1) { // 적이 있는 칸이면
          int dist = Math.abs(archerRow - r) + Math.abs(archerCol - c);

          if (dist <= D) { // 공격 가능거리 이하인 경우
            // 처음 찾은 적이거나, 더 가까운 적이거나, 거리가 같은데 더 왼쪽에 있는 적인 경우
            if (closestEnemy == null || dist < minDistance || (dist == minDistance && c < closestEnemy.col)) {
              minDistance = dist;
              closestEnemy = new Enemy(r, c);
            }
          }
        }
      }
    }

    return closestEnemy; // 적이 없으면 null 반환
  }
}