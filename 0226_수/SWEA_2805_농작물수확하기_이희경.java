import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SWEA_2805_농작물수확하기_이희경 {
  static int[][] farm;
  static int N;
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;

  /**
   * 1. 위 삼각형 계산
   *    1-1. core(제일 중앙 열 인덱스값)를 중심으로 core - row 이상 core+row 이하의 열의 값을 더함.
   * 2. 아래 삼각형 계산
   *    2-1. idx=1부터 N-(열의 시작 인덱스) 이하의 열까지의 값을 더함.
   */
  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 농장 크기
      farm = new int[N][N]; // 농작물 가치 담은 농장 맵

      // 농작물 가치 담은 농장 맵 입력받음
      for (int idx = 0; idx < N; idx++) {
        st = new StringTokenizer(br.readLine());
        farm[idx] = st.nextToken().chars().map(charItem -> charItem - '0').toArray();
      }

      int profit = 0;
      int core = N / 2;

      // 위에 삼각형
      for (int row = 0; row <= core; row++) {
        for (int col = core - row; col <= core + row; col++) {
          profit += farm[row][col];
          farm[row][col] = -1;
        }
      }
      // 아래 삼각형
      for (int start = 1; start <= core; start++) {
        for (int col = start; col < N - start; col++) {
          profit += farm[start + core][col];
          farm[start + core][col] = -1;
        }

      }
      sb.append('#').append(tc).append(' ').append(profit).append('\n');

    }
    System.out.println(sb);

  }

}
