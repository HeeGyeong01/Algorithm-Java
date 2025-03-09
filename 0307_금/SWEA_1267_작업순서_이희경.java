import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * 1. 초기 그래프 상태 입력 받아서 edgeInfo[][]에 저장함.
 *    - from 노드에서 to 노드로 가는 방향 간선이 있는 경우 true로 저장함.
 * 2. 모든 간선을 순회하면서 진입 차수가 0인 노드를 큐에 넣음.
 * 3. (BFS 시행) 큐에 요소 있는 조건에 while문 반복.
 *    3-1. 큐에서 요소 꺼낸 후에 StringBuilder에 추가.
 *    3-2. 방금 큐에서 꺼낸 노드에서 다른 노드로 가는 간선을 다 제거하고 
 *         해당 노드의 진입차수가 0이 된 경우 -> 큐에 추가한다. 
 */
public class SWEA_1267_작업순서_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int V, E;
  static boolean[][] edgeInfo;

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    for (int tc = 1; tc <= 10; tc++) {
      st = new StringTokenizer(br.readLine());
      V = Integer.parseInt(st.nextToken()); // 노드 수
      E = Integer.parseInt(st.nextToken()); // 간선 수
      edgeInfo = new boolean[V + 1][V + 1]; // 입력으로 받는 간선 정보를 저장할 2차원 배열
      sb.append('#').append(tc).append(' ');

      // 1. 초기 그래프 상태 입력받음
      st = new StringTokenizer(br.readLine());
      for (int count = 0; count < E; count++) {
        int from = Integer.parseInt(st.nextToken());
        int to = Integer.parseInt(st.nextToken());
        // from 노드에서 to 노드로 가는 방향 간선이 있는 경우 true로 저장함.
        edgeInfo[from][to] = true;
      }

      ArrayDeque<Integer> que = new ArrayDeque<>();

      // 2. 1~V를 순회하면서 진입 차수가 0인 정점을 큐에 모두 넣음.
      for (int nodeNum = 1; nodeNum < V + 1; nodeNum++) {
        if (isInDegree0(nodeNum)) {
          que.add(nodeNum);
        }
      }

      // 3. BFS 시작
      while (!que.isEmpty()) {
        int curNode = que.poll(); // 큐에서 요소 꺼냄.
        sb.append(curNode).append(' ');

        // 진입차수가 0인 노드를 꺼내서 자신과 인접한 정점의 간선을 제거한다.
        for (int to = 1; to < V + 1; to++) {
          // 진입 노드가 있는 경우 -> 간선 제거
          if (edgeInfo[curNode][to]) {
            edgeInfo[curNode][to] = false;
            // 방금 간선 제거한 노드의 진입차수가 0이 된 경우 -> 큐에 추가한다.
            if (isInDegree0(to)) {
              que.add(to);
            }
          }
        }
      }

      // 정답 출력
      sb.append('\n');

    }

    System.out.println(sb);

  }

  // 해당 노드의 진입차수가 0인지 판별
  public static boolean isInDegree0(int node) {

    for (int from = 1; from < V + 1; from++) {
      // 진입 노드가 있다.
      if (edgeInfo[from][node]) {
        return false; // -> false 리턴
      }

    }
    return true;

  }

}
