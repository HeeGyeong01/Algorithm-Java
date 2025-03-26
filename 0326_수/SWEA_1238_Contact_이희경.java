import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 1. 단방향 그래프 정보 인접행렬로 입력받음.
 * 2. 노드 번호 대신, 계층과 노드번호가 필드값으로 있는 NodeInfo로 BFS 실시.
 * 3. 가장 높은계층의 가장 높은 노드 번호를 출력.
 */
public class SWEA_1238_Contact_이희경 {
  static BufferedReader br;
  static StringBuilder sb;
  static StringTokenizer st;
  static int N, startNode;
  static boolean[][] edgeInfo;
  static boolean[] visited;

  static class NodeInfo implements Comparable<NodeInfo> {
    int depth, node;

    public NodeInfo(int depth, int node) {
      this.depth = depth;
      this.node = node;
    }

    // depth, 노드번호 [내림차순]
    @Override
    public int compareTo(NodeInfo o) {
      return this.depth != o.depth ? o.depth - this.depth : o.node - this.node;
    }
  }

  public static void main(String[] args) throws IOException {
    br = new BufferedReader(new InputStreamReader(System.in));
    sb = new StringBuilder();

    for (int tc = 1; tc <= 10; tc++) {
      st = new StringTokenizer(br.readLine());
      N = Integer.parseInt(st.nextToken()); // 노드 수
      startNode = Integer.parseInt(st.nextToken()); // 시작 노드
      edgeInfo = new boolean[101][101]; // 입력으로 받는 간선 정보를 저장할 2차원 배열
      visited = new boolean[101]; // 방문 확인 배열
      sb.append('#').append(tc).append(' ');

      // 그래프 상태 입력받음
      st = new StringTokenizer(br.readLine());
      for (int count = 0; count < N / 2; count++) {
        int from = Integer.parseInt(st.nextToken());
        int to = Integer.parseInt(st.nextToken());
        // from 노드에서 to 노드로 가는 방향 간선이 있는 경우 true로 저장함.
        edgeInfo[from][to] = true;
      }

      // 정답 출력
      sb.append(call(startNode)).append('\n');

    }

    System.out.println(sb);

  }

  // 연락 돌린 다음, 가장 나중에 연락을 받게 되는 사람 중 번호가 가장 큰 사람 출력.
  public static int call(int startNode) {
    ArrayDeque<NodeInfo> que = new ArrayDeque<>();
    que.add(new NodeInfo(1, startNode));
    visited[startNode] = true;
    List<NodeInfo> visitHistory = new ArrayList<>();

    while (!que.isEmpty()) {
      NodeInfo curNode = que.poll();
      // 1~100까지 순회
      for (int nextNode = 1; nextNode <= 100; nextNode++) {
        // 존재하는 간선과 아직 방문하지 않은 노드인 경우
        if (edgeInfo[curNode.node][nextNode] && !visited[nextNode]) {
          que.add(new NodeInfo(curNode.depth + 1, nextNode));
          visited[nextNode] = true;
          visitHistory.add(new NodeInfo(curNode.depth + 1, nextNode));
        }
      }

    }

    Collections.sort(visitHistory);
    return visitHistory.get(0).node;

  }

}