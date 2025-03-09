import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 시작칸으로 가능한 모든 칸 순회
 *    1-1. 시작 노드 저장, 방문 정보 저장할 배열을 초기화
 *    1-2. 디저트 종류 방문한 기록 현재 디저트 넣어서 초기화, 시작노드 방문처리.
 *    1-3. dfs 호출
 * 2. dfs 시행
 *    2-1. 현재 방향과 다음 방향 순회
 *        2-1-1. 방향 꺾은 횟수가 3번 초과했을 때 break;
 *        2-1-2. map[][] 범위 안에 있는 노드인 경우
 *               - 시작 점으로 돌아온 경우 -> maxCount 업데이트
 *               - 안 먹어본 디저트이고, 방문한 적 없는 노드일 때 
 *                    -> 해당 노드와 디저트 방문처리
 *                    -> dfs 재귀 호출
 *                    -> 해당 노드와 디저트 방문처리 해제
 */
public class SWEA_2105_디저트카페_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int N, maxCount, initRow, initCol;
  static int[][] map;
  static boolean[] dessertKinds;
  static int[] dr = { 1, 1, -1, -1 }, dc = { 1, -1, -1, 1 }; //대각선 4방향(시계방향)

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 한 변의 길이
      map = new int[N][N]; // 입력으로 받는 map 정보를 저장할 2차원 배열
      maxCount = 0;

      // [input] 초기 map 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          map[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      // 시작칸으로 가능한 모든 칸 순회
      // 맨 위부터 오른쪽 아래 대각선 -> 왼쪽 아래 대각선 -> 왼쪽 위 대각선 -> 오른쪽 위 대각선 경로만 생각하므로
      // 맨 아래 2 줄과 맨 왼쪽의 한 줄, 맨 오른쪽의 한 줄은 시작 노드로 고려하지 않아도 됨.
      for (int row = 0; row < N - 2; row++) {
        for (int col = 1; col < N - 1; col++) {
          // 시작노드 설정
          initRow = row;
          initCol = col;
          // 방문정보 저장할 배열 초기화
          dessertKinds = new boolean[101];
          dessertKinds[map[row][col]] = true; // 디저트 종류 방문한 기록 현재 디저트 넣어서 초기화
          dfs(row, col, 0, 1);

        }
      }

      // 정답 출력
      sb.append('#').append(tc).append(' ').append(maxCount == 0 ? -1 : maxCount).append('\n');

    }

    System.out.println(sb);

  }

  // 사각형 경로 찾는 메소드 4방향 탐색
  // (현재 탐색중인 노드, 현재 방향, 탐색한 노드 수)
  public static void dfs(int curRow, int curCol, int dirIdx, int searchedNode) {

    // 현재 방향과 다음 방향 순회 (First 같은 방향, Second 다음 방향)
    for (int count = 0; count <= 1; count++) {
      // 방향 꺾은게 3번 초과했을 때
      if (dirIdx + count >= 4) {
        break;
      }

      int nextRow = curRow + dr[dirIdx + count];
      int nextCol = curCol + dc[dirIdx + count];

      // 시작 점으로 돌아온 경우 -> maxCount 업데이트
      if (nextRow == initRow && nextCol == initCol) {
        maxCount = Math.max(maxCount, searchedNode); // 탐색한 노드 개수
        return;
      }

      // map[][] 범위 안에 있는 노드인 경우
      if (inBounds(nextRow, nextCol)) {

        // 안 먹어본 디저트이고, 방문한 적 없는 노드일 때
        if (dessertKinds[map[nextRow][nextCol]] == false) {

          dessertKinds[map[nextRow][nextCol]] = true; //노드와 해당 디저트 방문처리 
          dfs(nextRow, nextCol, dirIdx + count, searchedNode + 1);
          dessertKinds[map[nextRow][nextCol]] = false; //노드와 해당 디저트 방문처리 해제

        }
      }
    }

  }

  // 해당 인덱스가 map[][] 범위 안인지 확인하는 메소드
  public static boolean inBounds(int row, int col) {
    if (row >= 0 && row < N && col >= 0 && col < N) {
      return true;
    }
    return false;

  }

}
