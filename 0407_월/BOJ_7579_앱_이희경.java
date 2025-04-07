import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.io.InputStreamReader;

public class BOJ_7579_앱_이희경 {
  static int N, M;
  static int[] memory, cost;
  static int[] dp;
  static StringTokenizer st;

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken()); // 현재 N개의 앱이 활성화 되어있음
    M = Integer.parseInt(st.nextToken()); // 필요한 메모리 M바이트 
    memory = new int[N + 1];
    cost = new int[N + 1];

    // memory[], cost[]에 정보 입력받음.
    st = new StringTokenizer(br.readLine());
    for (int idx = 1; idx <= N; idx++) {
      memory[idx] = Integer.parseInt(st.nextToken());
    }
    st = new StringTokenizer(br.readLine());
    for (int idx = 1; idx <= N; idx++) {
      cost[idx] = Integer.parseInt(st.nextToken());
    }

    dp = new int[100 * N + 1]; // (1-base, N 앱까지 고려했을 때) 해당 cost 이하에서 가장 높은 메모리값 저장.

    // dp 업데이트
    for (int idx = 1; idx <= N; idx++) {
      for (int m = 100 * N; m >= 0; m--) {
        if (m >= cost[idx]) {
          dp[m] = Math.max(dp[m], dp[m - cost[idx]] + memory[idx]);
        }
      }
    }

    // 메모리 M바이트 이상인 인덱스(가치) 중에 가장 낮은 인덱스 값 구함
    int minCost = Integer.MAX_VALUE;
    for (int idx = dp.length - 1; idx >= 0; idx--) {
      if (dp[idx] >= M) {
        minCost = idx;
      }
    }

    // 필요한 메모리 M 바이트를 확보하기 위한 앱 비활성화의 최소의 비용
    System.out.println(minCost);

  }

}