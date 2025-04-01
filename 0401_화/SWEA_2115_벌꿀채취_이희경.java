import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. grid 입력값을 2차원 배열에 저장.
 * 2. 두 슬라이드 조합 생성 (setPair)
 * 3. 각 조합의 수익 계산 (calcProfit)
 *    - 총 꿀 양과 수익(꿀^2 합) 계산.
 * 4. 꿀 양이 C 초과 시 getMaxHoney로 최대 수익 구함.
 *    - makeComb로 부분집합 만들어 최대 수익 갱신.
 * 5. 두 일꾼의 수익 합으로 maxProfit 업데이트.
 * 6. 모든 조합 탐색 후 최대 수익 출력.
 */
public class SWEA_2115_벌꿀채취_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int N, M, C, maxProfit, partMaxProfit;
  static int[][] grid;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine().trim());
      N = Integer.parseInt(st.nextToken()); // grid의 행과 열
      M = Integer.parseInt(st.nextToken()); // 한 일꾼이 가로로 선택할 수 있는 칸의 개수
      C = Integer.parseInt(st.nextToken()); // 한 일꾼이 채취할 수 있는 최대 꿀의 양

      grid = new int[N][N]; // 입력으로 받는 그리드 정보를 저장할 2차원 배열
      maxProfit = 0; // 최대 수익

      // grid 상태 입력받음
      for (int row = 0; row < N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 0; col < N; col++) {
          grid[row][col] = Integer.parseInt(st.nextToken());
        }
      }

      setPair(); // 조합 

      sb.append('#').append(tc).append(' ').append(maxProfit).append('\n');
    }
    System.out.println(sb);
  }

  // 2개 슬라이드 정함
  private static void setPair() {

    // 2차원 배열을 1차원으로 생각
    // 첫번째 일꾼 경우 
    for (int start1 = 0; start1 <= N * N - M; start1++) {

      // 두번째 일꾼 경우
      for (int start2 = start1 + M; start2 <= N * N - M; start2++) {
        // 하나의 윈도우가 start와 end가 같은 행에 있어야 함.
        if (start1 / N == (start1 + M - 1) / N && start2 / N == (start2 + M - 1) / N) {
          calcProfit(start1, start2);
        }
      }
    }
  }

  // 해당 슬라이드2개 조합의 경우에 발생하는 수익 계산
  private static void calcProfit(int idx1, int idx2) {
    int totalHoney1 = 0, totalHoney2 = 0;
    int totalProfit1 = 0, totalProfit2 = 0;
    // 첫번째 일꾼
    for (int i = idx1; i <= idx1 + M - 1; i++) {
      totalHoney1 += grid[i / N][i % N];
      totalProfit1 += Math.pow(grid[i / N][i % N], 2);
    }

    // 두번째 일꾼
    for (int j = idx2; j <= idx2 + M - 1; j++) {
      totalHoney2 += grid[j / N][j % N];
      totalProfit2 += Math.pow(grid[j / N][j % N], 2);
    }

    // 채취할 수 있는 꿀의 최대 양 초과하는 경우 조합 만들어 maxProfit 고려
    totalProfit1 = totalHoney1 > C ? getMaxHoney(idx1) : totalProfit1;
    totalProfit2 = totalHoney2 > C ? getMaxHoney(idx2) : totalProfit2;

    // MaxProfit 업데이트
    maxProfit = Math.max(maxProfit, totalProfit1 + totalProfit2);

  }

  private static int getMaxHoney(int idx) {
    partMaxProfit = 0;
    makeComb(idx, idx, 0, 0, 0);
    return partMaxProfit;
  }

  //부분집합
  private static void makeComb(int startIdx, int selectedIdx, int prevProfit, int totalHoney, int totalProfit) {

    // 최대 양 넘어간 경우
    if (totalHoney > C) {
      partMaxProfit = Math.max(partMaxProfit, totalProfit - prevProfit);
      return;
    }

    // 모든 요소 다 고려한 경우
    if (selectedIdx == (startIdx + M)) {
      partMaxProfit = Math.max(partMaxProfit, totalProfit);
      return;
    }

    // 이번 요소 선택한 경우
    int curHoney = grid[selectedIdx / N][selectedIdx % N];
    int curProfit = curHoney * curHoney;
    makeComb(startIdx, selectedIdx + 1, (curHoney * curHoney), totalHoney + curHoney, totalProfit + curProfit);
    // 이번 요소 선택하지 않은 경우
    makeComb(startIdx, selectedIdx + 1, prevProfit, totalHoney, totalProfit);

  }

}