import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 1. 관계정보 입력받아서 순방향 인접 배열과 역방향 인접 배열 생성함.
 * 2. 모든 노드에 대해, 순방향과 역방향 그래프를 각각 BFS 탐색을 실행해서 
 *    - 찾아지는 노드들의 합이 N-1이면 knowStudentCount에 +1한다.
 * 3. 최종적으로 얻어지는 knowStudentCount를 출력한다.
 */
public class SWEA_5643_키순서_이희경 {
  static BufferedReader br;
  static StringTokenizer st;
  static StringBuilder sb;
  static int N, M;
  static boolean[][] rel, reverseRel;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();
    int T = Integer.parseInt(br.readLine().trim()); // 테스트 케이스 수 입력

    for (int tc = 1; tc <= T; tc++) {
      N = Integer.parseInt(br.readLine()); // 학생 수 
      M = Integer.parseInt(br.readLine()); // 알려주는 관계의 수

      rel = new boolean[N + 1][N + 1]; // 인접 배열
      reverseRel = new boolean[N + 1][N + 1]; // 간선의 방향을 반대로 한 인접 배열

      // grid 상태 입력받음
      for (int row = 0; row < M; row++) {
        st = new StringTokenizer(br.readLine());
        int from = Integer.parseInt(st.nextToken());
        int to = Integer.parseInt(st.nextToken());
        rel[from][to] = true;
        reverseRel[to][from] = true;
      }

      int knowCount = getKnowCount(); // 자신의 키의 순위를 알 수 있는 학생의 수를 구한다.

      sb.append('#').append(tc).append(' ').append(knowCount).append('\n');
    }
    System.out.println(sb);
  }

  // 자신의 키의 순위를 알 수 있는 학생의 수를 구한다.
  private static int getKnowCount() {
    int knowStudentCount = 0;
    // 각 학생을 순회
    for (int idx = 1; idx <= N; idx++) {

      // 1. rel 탐색
      Queue<Integer> que = new ArrayDeque<>();
      boolean[] visited = new boolean[N + 1];
      visited[idx] = true;
      int relCount = 1;
      que.offer(idx);

      while (!que.isEmpty()) {
        int cur = que.poll();
        for (int next = 1; next <= N; next++) {
          if (rel[cur][next] && !visited[next]) { // 해당 노드로 가는 간선이 있고, 방문한 적 없는 노드인 경우
            relCount += 1;
            visited[next] = true;
            que.offer(next);
          }
        }
      }

      // 2. reverse 탐색 
      que = new ArrayDeque<>();
      visited = new boolean[N + 1];
      visited[idx] = true;
      que.offer(idx);

      while (!que.isEmpty()) {
        int cur = que.poll();
        for (int next = 1; next <= N; next++) {
          if (reverseRel[cur][next] && !visited[next]) { // 해당 노드로 가는 간선이 있고, 방문한 적 없는 노드인 경우
            relCount += 1;
            visited[next] = true;
            que.offer(next);
          }
        }
      }

      // 나 + 나보다 작은 학생 + 큰 학생의 합이 N인 경우 
      knowStudentCount += relCount == N ? 1 : 0;
    }

    return knowStudentCount;

  }

}