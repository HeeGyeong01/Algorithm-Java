import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N개의 값 중에서 M개의 숫자를 뽑아 만들 수 있는 순열 중 오름차순인 순열만 구하는 문제.
 * 
 * 0. [init] 순열 담을 배열과 방문여부 정보를 담은 배열을 각각 M+1, N+1 크기로 초기화함.
 * 1. backtrak() 재귀 호출
 * 1-1. 종료조건: 길이가 M인 순열이 완성되면 Stringbuilder에 추가하고 리턴함.
 * 1-2. 1~N까지 순회하면서 
 *      - 방문하지 않은 숫자이고 순열에 직전에 담긴 숫자보다 큰 경우에
 *      - 방문처리하고, 순열에 추가하고
 *      - 전달받은 매개변수에 +1 해서 재귀호출
 *      - 방문처리 취소한다.
 * 2. 지금까지 StringBuilder에 담긴 순열들을 한 줄씩 출력한다.
 */
public class BOJ_15650_N과M2_이희경 {

  static int N;
  static int M;
  static int[] pArr; // 순열 담은 배열
  static boolean[] visited;
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    // 0️⃣ init
    pArr = new int[M + 1];
    visited = new boolean[N + 1];

    // 1️⃣ 재귀호출 Start
    backtrack(0);

    // 3️⃣ 순열들 출력
    System.out.println(sb);

  }

  // 1️⃣ 재귀호출 Start
  private static void backtrack(int length) {
    // 길이가 M인 순열이 완성되면 Stringbuilder에 추가하고 리턴함.
    if (length == M) {
      for (int pIdx = 1; pIdx < pArr.length; pIdx++) {
        sb.append(pArr[pIdx] + " ");
      }
      sb.append('\n');
      return;
    }

    for (int num = 1; num <= N; num++) {
      // 방문하지 않은 숫자면
      if (visited[num] == false && pArr[length] < num) {
        visited[num] = true;
        pArr[length + 1] = num; // 배열에 숫자 추가
        backtrack(length + 1);
        visited[num] = false;

      }
    }

  }

}
