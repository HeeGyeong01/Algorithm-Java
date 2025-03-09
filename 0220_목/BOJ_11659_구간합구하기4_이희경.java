import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 주어진 수열에서 i번째부터 j번째 수까지의 합을 구하는 문제
 * 
 * 0. [init] 입력으로 주어진 수의 누적합을 저장한 nArr[] 초기화
 * 1. 주어지는 수들을 입력받고 직전에 nArr[]에 저장된 값을 더해서 저장한다.
 * 2. 주어지는 i와 j를 입력받고 (j까지의 누적합 - i이전까지의 누적합)을 계산해서 SrringBuilder에 추가.
 * 3. 정답 출력한다.
 */
public class BOJ_11659_구간합구하기4_이희경 {

  static int N, M;
  static int start, end;
  static int[] nArr; // 주어진 수의 누적 합을 담은 배열
  static StringBuilder sb = new StringBuilder();

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());
    // 0️⃣ init
    nArr = new int[N + 1];

    // 1️⃣ 입력 받아서 누적합으로 저장.
    st = new StringTokenizer(br.readLine());
    for (int idx = 1; idx <= N; idx++) {
      nArr[idx] = nArr[idx - 1] + Integer.parseInt(st.nextToken());
    }

    // 2️⃣ i번째부터 j번째 수까지의 합 계산해서 SringBuilder에 추가.
    for (int inputLine = 0; inputLine < M; inputLine++) {
      st = new StringTokenizer(br.readLine());
      start = Integer.parseInt(st.nextToken());
      end = Integer.parseInt(st.nextToken());
      sb.append(nArr[end] - nArr[start - 1]).append('\n');
    }

    // 3️⃣ StringBuilder에 추가된 수들 출력
    System.out.println(sb);

  }

}
