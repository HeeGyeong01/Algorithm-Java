import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 1. 초기 그래프 상태 입력 받아서 edgeInfo에 저장함.
 *    - from 노드를 key로 하고 to 노드를 ArrayList에 저장한 Map<Integer, ArrayList<Integer>> 자료형
 * 2. 모든 간선을 순회하면서 진입 차수가 0인 노드를 큐에 넣음.
 * 3. (BFS 시행) 큐에 요소 있는 조건에 while문 반복.
 *    3-1. 큐에서 요소 꺼낸 후에 StringBuilder에 추가.
 *    3-2. 방금 큐에서 꺼낸 노드에서 다른 노드로 가는 간선을 다 제거하고 
 *         해당 노드의 진입차수가 0이 된 경우 -> 큐에 추가한다. 
 */
public class BOJ_2252_줄세우기_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int N, M;
  static int[] entryDegree; // 진입차수 저장한 배열
  static Map<Integer, ArrayList<Integer>> edgeInfo; // 간선 정보 저장한 Map

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken()); // 노드 수
    M = Integer.parseInt(st.nextToken()); // 간선 수
    entryDegree = new int[N + 1]; // 각 노드의 진입차수 저장
    edgeInfo = new HashMap<>(); // 입력으로 받는 간선 정보를 저장할 Map

    // 1. 초기 그래프 상태 입력받음
    for (int count = 0; count < M; count++) {
      st = new StringTokenizer(br.readLine());
      int from = Integer.parseInt(st.nextToken());
      int to = Integer.parseInt(st.nextToken());
      // from 노드를 key로 하고 to 노드를 ArrayList에 저장
      edgeInfo.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
      entryDegree[to] += 1; // to 노드에 진입차수 +1

    }

    ArrayDeque<Integer> que = new ArrayDeque<>();

    // 2. 1~N을 순회하면서 진입 차수가 0인 정점을 큐에 모두 넣음.
    for (int nodeNum = 1; nodeNum < N + 1; nodeNum++) {
      if (entryDegree[nodeNum] == 0) {
        que.add(nodeNum);
      }
    }

    // 3. BFS 시작
    while (!que.isEmpty()) {
      int curNode = que.poll(); // 큐에서 요소 꺼냄.
      sb.append(curNode).append(' ');

      // 진입차수가 0인 노드를 꺼내서 자신과 인접한 정점의 간선을 제거한다.
      if (edgeInfo.containsKey(curNode)) {
        for (int to : edgeInfo.get(curNode)) {
          entryDegree[to] -= 1; // 해당 노드의 진입차수 -1
          // 해당 노드의 진입차수가 0인지 판별
          if (entryDegree[to] == 0) {
            // 방금 간선 제거한 노드의 진입차수가 0이 된 경우 -> 큐에 추가한다.
            que.add(to);
          }
        }

      }

    }

    // 정답 출력
    System.out.println(sb);

  }

}
