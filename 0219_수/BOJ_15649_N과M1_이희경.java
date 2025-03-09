import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N개의 값 중에서 M개의 숫자를 순서를 고려해서 뽑는 경우를 구하는 순열 문제이다.
 */
public class BOJ_15649_N과M1_이희경 {

  static int N;
  static int M;
  static int[] pArr;
  static boolean[] visited;
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken()); // 1~N까지의 자연수
    M = Integer.parseInt(st.nextToken()); // 수열의 길이
    pArr = new int[M]; // 순열을 만들 배열 M길이로 초기화
    visited = new boolean[N + 1];

    // 재귀함수 Start
    backtrack(0);

    System.out.println(sb);
  }

  private static void backtrack(int length) {
    // 종료조건: 길이가 M인 순열 다 만들어진 경우
    if (length == M) {
      // 만들어진 순열을 출력함
      for (int idx = 0; idx < pArr.length; idx++) {
        sb.append(pArr[idx] + " ");
      }
      sb.append('\n');
      return;
    }

    /**
     * 1~N 자연수를 순회하면서 visited[num]이 false인 경우 해당 수를 방문처리 하고 순열에 추가한다.
     * 
     * 그 후에 length+1을 파라미터로 하여 재귀 호출을 실행하고
     * 
     * 방문처리를 취소한다.
     */
    for (int num = 1; num <= N; num++) {
      if (visited[num] == false) {
        visited[num] = true;
        pArr[length] = num;
        backtrack(length + 1);
        visited[num] = false;
      }
    }
  }
}
