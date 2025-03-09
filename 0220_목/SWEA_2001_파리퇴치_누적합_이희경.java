import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

/**
 * N*N 의 그리드에서 M*M 칸 안의 점수의 합의 최대를 구하는 문제
 * 
 * 1. row = 1~N 까지 순회
 *  1-1. col = 1~N 까지 순회
 *  1-2. 2차원의 누적합 공식에 따라 각 칸에 (해당칸의 입력값 + map[row - 1][col] + map[row][col - 1] -
 *       map[row - 1][col - 1])을 저장한다.
 *  1-3. row >= M && col >= M 이라면 해당 칸을 맨 오른쪽, 맨 아래쪽으로 하는 M * M크기의 그리드 안의 점수의 합을
 *       계산해서 maxScore값을 갱신한다.
 */

public class SWEA_2001_파리퇴치_누적합_이희경 {
  static int map[][];
  static int N, M, maxScore;
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine());

    for (int tc = 1; tc <= T; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken());
      M = Integer.parseInt(st.nextToken());
      maxScore = 0;
      map = new int[N + 1][N + 1];

      // map[][]에 각 칸의 파리개수 입력받음.
      // map 순회하면서 누적합 저장함.
      for (int row = 1; row <= N; row++) {
        st = new StringTokenizer(br.readLine());
        for (int col = 1; col <= N; col++) {
          map[row][col] = Integer.parseInt(st.nextToken()) + map[row - 1][col] + map[row][col - 1]
              - map[row - 1][col - 1];

          // M*M 크기의 그리드 안의 점수 총합을 계산하여 maxScore를 업데이트
          if (row >= M && col >= M) {
            maxScore = Math.max(maxScore,
                map[row][col] - map[row - M][col] - map[row][col - M] + map[row - M][col - M]);
          }
        }
      }

      sb.append('#').append(tc).append(' ').append(maxScore).append('\n');

    }

    System.out.println(sb);
  }

}