import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
  static StringTokenizer st;
  static BufferedReader br;
  static StringBuilder sb;
  static int N;
  static int[][] grid;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    N = Integer.parseInt(br.readLine()); // 행과 열 수
    grid = new int[N][N]; // 입력으로 받는 타일 번호를 저장할 2차원 배열

    // 1. 초기 grid 상태 입력받음
    for (int row = 0; row < N; row++) {
      st = new StringTokenizer(br.readLine());
      for (int col = 0; col < N; col++) {
        grid[row][col] = Integer.parseInt(st.nextToken());

      }
    }

    // 쿼드 트리 실행
    quadTree(0, N - 1, N / 2);

    // 정답 출력
    System.out.println(sb);

  }

  public static void quadTree(int from, int to, int length) {
    sb.append('(');
    for (int row = from; row <= to; row++) {
      for (int col = from; col <= to; col++) {

      }
    }
    sb.append(')');
  }
}